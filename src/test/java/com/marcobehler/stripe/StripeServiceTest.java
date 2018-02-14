package com.marcobehler.stripe;

import com.stripe.model.Charge;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Thanks for watching this episode! Send any feedback to info@marcobehler.com!
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = StripeConfig.class)
public class StripeServiceTest {

    @Autowired
    private StripeService stripeService;

    @Test
    public void charge_is_successful() {
        Charge charge = stripeService.charge("tok_visa", 1123);
        assertThat(charge).isNotNull();
        assertThat(charge.getStatus()).isEqualTo("succeeded");

    }
}