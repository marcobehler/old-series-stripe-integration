package com.marcobehler.stripe;

import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Balance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Thanks for watching this episode! Send any feedback to info@marcobehler.com!
 */
@Component
@Profile("production")
public class StripeApiKeyChecker implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(StripeApiKeyChecker.class);

    private String stripeSecretKey;

    public StripeApiKeyChecker(@Value("${stripe.secretKey}") String stripeSecretKey) {
        this.stripeSecretKey = stripeSecretKey;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        Stripe.apiKey = stripeSecretKey;
        try {
            Balance.retrieve();
        } catch (InvalidRequestException | APIConnectionException | CardException | APIException e) {
            throw new RuntimeException(e);
        } catch (AuthenticationException e) {
            logger.error("${stripe.secretKey} is invalid or got rolled over recently." +
                    " Double-Check your application.properties file for the right key.");
            throw new RuntimeException(e);
        }
    }
}
