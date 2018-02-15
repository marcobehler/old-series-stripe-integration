package com.marcobehler.stripe;

import com.stripe.exception.AuthenticationException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.core.Is.isA;
import static org.junit.Assert.*;

/**
 * Thanks for watching this episode! Send any feedback to info@marcobehler.com!
 */
public class StripeApiKeyCheckerTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void checks_wrong_key() {
        expectedException.expectCause(isA(AuthenticationException.class));
        new StripeApiKeyChecker("this is a wrong key").onApplicationEvent(null);
    }
}