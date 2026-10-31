package com.codingshuttle.projects.lovable_clone.config;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentConfig
{
    @Value("${stripe.secret}")
    private String stripeSecretKey;

    @PostConstruct
    public void init()
    {
        // This sets the global static API key required by Session.create(params)
        Stripe.apiKey = stripeSecretKey;
    }
}