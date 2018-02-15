package com.marcobehler.stripe;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Thanks for watching this episode! If you want to, drop me a line to info@marcobehler.com.
 */
@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
public class StripeConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public CommonAnnotationBeanPostProcessor commonAnnotationBeanPostProcessor() {
        return new CommonAnnotationBeanPostProcessor();
    }

    @Bean
    public JavaMailSender getJavaMailSender( @Value("${smtp.host}") String smtpHost,  @Value("${smtp.port}") Integer smtpPort) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(smtpHost);
        mailSender.setPort(smtpPort);
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "false");
        return mailSender;
    }
}
