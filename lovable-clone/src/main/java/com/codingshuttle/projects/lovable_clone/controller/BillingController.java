package com.codingshuttle.projects.lovable_clone.controller;

import com.codingshuttle.projects.lovable_clone.dto.subscription.*;
import com.codingshuttle.projects.lovable_clone.service.PaymentProcessor;
import com.codingshuttle.projects.lovable_clone.service.PlanService;
import com.codingshuttle.projects.lovable_clone.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BillingController
{
    private final PlanService planService;
    private final PaymentProcessor paymentProcessor;
    private final SubscriptionService subscriptionService;

    @GetMapping("/api/plans")
    public ResponseEntity<List<PlanResponse>> getAllPlans()
    {
        return ResponseEntity.ok(planService.getAllActivePlans());
    }

    @GetMapping("/api/me/subscription")
    public ResponseEntity<SubscriptionResponse> getMySubscription()
    {
        return ResponseEntity.ok(subscriptionService.getCurrentSubscription());
    }

    @PostMapping("/api/payment/checkout")
    public ResponseEntity<CheckoutResponse> createCheckoutSessionUrl(@RequestBody CheckoutRequest request)
    {
        return ResponseEntity.ok(paymentProcessor.createCheckoutSessionUrl( request));
    }

    @PostMapping("/api/payment/portal")
    public ResponseEntity<PortalResponse> openCustomerPortal()
    {
        return ResponseEntity.ok(paymentProcessor.openCustomerPortal());
    }

//    It handles a POST request to /api/stripe/portal
//Calls service to create a Stripe customer portal session
//Returns a URL where user can manage their subscription
}
