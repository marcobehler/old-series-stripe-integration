package com.marcobehler.stripe;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Thanks for watching this episode! If you want to, drop me a line to info@marcobehler.com.
 */

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = StripeConfig.class)
public class StripeServiceTest {


    @Autowired
    private StripeService stripeService;

    @Autowired
    private ApplicationContext context;

    @Test
    public void name() {
        stripeService.charge("tok_visa", 10000);
    }
}