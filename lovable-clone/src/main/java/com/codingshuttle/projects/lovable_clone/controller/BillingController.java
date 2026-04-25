package com.codingshuttle.projects.lovable_clone.controller;

import com.codingshuttle.projects.lovable_clone.dto.subscription.CheckoutRequest;
import com.codingshuttle.projects.lovable_clone.dto.subscription.CheckoutResponse;
import com.codingshuttle.projects.lovable_clone.dto.subscription.PlanResponse;
import com.codingshuttle.projects.lovable_clone.dto.subscription.SubscriptionResponse;
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
    private final SubscriptionService subscriptionService;

    @GetMapping("/api/plans")
    public ResponseEntity<List<PlanResponse>> getAllPlans()
    {
        return ResponseEntity.ok(planService.getAllActivePlans());
    }

    @GetMapping("/api/me/subscription")
    public ResponseEntity<SubscriptionResponse> getMySubsciption()
    {
        Long userId = 1L;
        return ResponseEntity.ok(subscriptionService.getCurrentSubscription(userId));
    }

    @PostMapping("api/stripe/checkout")
    public ResponseEntity<CheckoutResponse> createCheckoutSessionUrl
            (
                    @RequestBody CheckoutRequest request
            )
    {
        Long userId = 1L;
        return ResponseEntity.ok(subscriptionService.createCheckoutSessionUrl(userId , request));
    }

//    This method creates a Stripe checkout session for the user based on the request data and returns a payment URL.
//The frontend uses this URL to redirect the user to Stripe to complete the subscription/payment process.

}
