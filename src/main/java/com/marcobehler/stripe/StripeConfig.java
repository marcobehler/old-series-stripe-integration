package com.marcobehler.stripe;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Thanks for watching this episode! If you want to, drop me a line to info@marcobehler.com.
 */
@Configuration
@ComponentScan
public class StripeConfig {
    @Bean
    public CommonAnnotationBeanPostProcessor commonAnnotationBeanPostProcessor() {
        return new CommonAnnotationBeanPostProcessor();
    }
}
