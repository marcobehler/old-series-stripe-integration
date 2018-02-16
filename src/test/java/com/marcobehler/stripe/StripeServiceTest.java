package com.marcobehler.stripe;

import com.stripe.model.Charge;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.subethamail.wiser.Wiser;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Thanks for watching this episode! Send any feedback to info@marcobehler.com!
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = StripeConfig.class)
public class StripeServiceTest {

    @Autowired
    private StripeService stripeService;

    private Wiser wiser;

    @Before
    public void setUp() throws Exception {
        wiser = new Wiser(25);
        wiser.start();
    }

    @Test
    public void charge_is_successful() {
        Charge charge = stripeService.charge("tok_visa", 1123);
        assertThat(charge).isNotNull();
        assertThat(charge.getStatus()).isEqualTo("succeeded");
    }

    @Test
    public void credit_card_is_expired() {
        Charge charge = stripeService.charge("tok_chargeDeclinedExpiredCard", 1123);
        assertThat(charge).isNull();

        assertThat(wiser.getMessages()).hasSize(1);
        assertThat(wiser.getMessages()).extracting("mimeMessage.subject")
                .contains("dude, where's my money??");

    }
}
