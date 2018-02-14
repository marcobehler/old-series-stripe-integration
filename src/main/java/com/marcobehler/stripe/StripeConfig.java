package com.marcobehler.stripe;

import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Thanks for watching this episode! If you want to, drop me a line to info@marcobehler.com.
 */
@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
public class StripeConfig {

    @Bean
    public CommonAnnotationBeanPostProcessor commonAnnotationBeanPostProcessor() {
        return new CommonAnnotationBeanPostProcessor();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
