package com.marcobehler.stripe;

import com.stripe.Stripe;
import com.stripe.model.Charge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Thanks for watching this episode! If you want to, drop me a line to info@marcobehler.com.
 */

@Service
public class StripeService {

    private Logger logger = LoggerFactory.getLogger(StripeService.class);

    private String apiKey = " PUT STRIPE KEY HERE";

    public Charge charge(String token, Integer amountinCents) {
        Stripe.apiKey = apiKey;

        Map<String, Object> params = new HashMap<>();
        params.put("amount", amountinCents);
        params.put("currency", "USD");
        params.put("description", "This is a test");
        params.put("source", token);

        try {
            return Charge.create(params);
        } catch (Exception e) {
            logger.error("Problem charging card", e);
            return null;
        }
    }

}
