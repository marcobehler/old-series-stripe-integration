package com.marcobehler.stripe;

import com.stripe.Stripe;
import com.stripe.exception.CardException;
import com.stripe.model.Charge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Thanks for watching this episode! If you want to, drop me a line to info@marcobehler.com.
 */

@Service
public class StripeService {

    private static final Logger logger = LoggerFactory.getLogger(StripeService.class);

    private String stripeSecretKey;

    private JavaMailSender mailSender;

    @Autowired
    public StripeService(@Value("${stripe.secretKey}") String stripeSecretKey, JavaMailSender mailSender) {
        this.stripeSecretKey = stripeSecretKey;
        this.mailSender = mailSender;
    }

    public Charge charge(String token, Integer amountInCents) {
        Stripe.apiKey = stripeSecretKey;

        Map<String, Object> params = new HashMap<>();
        params.put("amount", amountInCents);
        params.put("currency", "USD");
        params.put("description", "This is a test transaction");
        params.put("source", token);

        try {
            return Charge.create(params);
        }
        catch (CardException e) {
            handleCardException(e);
            return null;
        }
        catch (Exception e) {
            logger.error("Problem charging card", e);
            return null;
        }
    }

    private void handleCardException(CardException e) {
        String code = e.getCode();

        if (code.equals("expired_card")) {
            SimpleMailMessage m = new SimpleMailMessage();
            m.setFrom("your@mail.com");
            m.setTo("customers@mail.com");
            m.setSubject("dude, where's my money??");
            m.setText("please renew your credit card details !!!");
            mailSender.send(m);
        } else {
            // TODO
        }
    }

}
