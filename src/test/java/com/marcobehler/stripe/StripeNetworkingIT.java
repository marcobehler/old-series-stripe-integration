package com.marcobehler.stripe;

import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.AuthenticationException;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockserver.junit.ProxyRule;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.core.Is.isA;

/**
 * Thanks for watching this episode! Send any feedback to info@marcobehler.com!
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = StripeConfig.class)
@TestPropertySource(properties = {
        "http.proxyHost=localhost",
        "http.proxyPort=9999",
        "stripe.connectTimeout=1000",
        "stripe.readTimeout=1000"
})
public class StripeNetworkingIT {

    @Autowired
    private StripeService stripeService;

    @Rule
    public ProxyRule proxyRule = new ProxyRule(9999, this);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        proxyRule.getClient().when(HttpRequest.request())
                .respond(HttpResponse.response().withDelay(TimeUnit.SECONDS,5)
                        .withBody("Hey, this is stripe!"));
        SSLDisabler.ignoreAllSSLCertificates();
    }

    @After
    public void tearDown() throws Exception {
        Stripe.setConnectionProxy(null);
        Stripe.setReadTimeout(-1);
        Stripe.setConnectTimeout(-1);
    }

    @Test
    public void handles_read_timeout() {
        expectedException.expectCause(isA(APIConnectionException.class));
        stripeService.charge("anytoken", 1111);
    }
}
