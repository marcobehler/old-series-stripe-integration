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

    public Charge charge(String token, Integer amountInCents) {
        Stripe.apiKey = "sk_test_zcNGUB0X6rgQMFLRmfcHpzUv";

        Map<String, Object> params = new HashMap<>();
        params.put("amount", amountInCents);
        params.put("currency", "USD");
        params.put("description", "This is a test transaction");
        params.put("source", token);

        try {
            return Charge.create(params);
        } catch (Exception e) {
            logger.error("Problem charging card", e);
            return null;
        }
    }

}
