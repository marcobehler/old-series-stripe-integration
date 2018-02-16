package com.marcobehler.stripe;

import com.stripe.exception.APIConnectionException;
import com.stripe.exception.AuthenticationException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockserver.client.proxy.ProxyClient;
import org.mockserver.junit.ProxyRule;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
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
        "stripe.readTimeout=1000",})
public class StripeNetworkingTest {

    @Autowired
    private StripeService stripeService;

    @Rule
    public ProxyRule proxyRule = new ProxyRule(9999, this);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private ProxyClient proxyClient;

    @Before
    public void setUp() {
        ignoreAllSSLCertificates();
        proxyClient.when(HttpRequest.request())
                .respond(HttpResponse.response()
                        .withDelay(TimeUnit.SECONDS, 5)
                        .withBody("Hey, this is Stripe!"));
    }


    @Test
    public void handles_read_timeout_exception() {
        expectedException.expectCause(isA(APIConnectionException.class));
        stripeService.charge("sometoken", 11123);
    }


    private void ignoreAllSSLCertificates() {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }};

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
        }
    }
}
