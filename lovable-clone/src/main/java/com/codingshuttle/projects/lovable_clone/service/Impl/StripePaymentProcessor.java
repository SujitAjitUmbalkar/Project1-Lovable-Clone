package com.codingshuttle.projects.lovable_clone.service.Impl;

import com.codingshuttle.projects.lovable_clone.dto.subscription.CheckoutRequest;
import com.codingshuttle.projects.lovable_clone.dto.subscription.CheckoutResponse;
import com.codingshuttle.projects.lovable_clone.dto.subscription.PortalResponse;
import com.codingshuttle.projects.lovable_clone.entity.Plan;
import com.codingshuttle.projects.lovable_clone.error.ResourceNotFoundException;
import com.codingshuttle.projects.lovable_clone.repository.PlanRepository;
import com.codingshuttle.projects.lovable_clone.security.AuthUtils;
import com.codingshuttle.projects.lovable_clone.service.PaymentProcessor;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class StripePaymentProcessor implements PaymentProcessor
{
    private final AuthUtils authUtil;
    private final PlanRepository planRepository;

    @NonFinal
    @Value("${client.url}")
    private String frontEntUrl;

    @Override
    public CheckoutResponse createCheckoutSessionUrl(CheckoutRequest request)
    {
        Long userId = authUtil.getCurrentUserId();

        Plan plan = planRepository.findById(request.planId()).orElseThrow(() ->
                new ResourceNotFoundException("Plan", request.planId().toString()));

        SessionCreateParams params = SessionCreateParams.builder()
                .addLineItem(
                        SessionCreateParams.LineItem.builder().setPrice(plan.getStripePriceId()).setQuantity(1L).build()
                )
                .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                .setSubscriptionData(
                        SessionCreateParams.SubscriptionData.builder()
                                .setBillingMode(SessionCreateParams.SubscriptionData.BillingMode.builder()
                                        .setType(SessionCreateParams.SubscriptionData.BillingMode.Type.FLEXIBLE)
                                        .build())
                                .build()
                )
                .setSuccessUrl(frontEntUrl + "/success.html?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl(frontEntUrl + "/cancel.html")
                .putMetadata("user_id", userId.toString())
                .putMetadata("plan_id", plan.getId().toString())
                .build();

        try
        {
            Session session = Session.create(params);

            log.info("Stripe Session URL generated: {}", session.getUrl());

            return new CheckoutResponse(session.getUrl());
        }
        catch (StripeException e) {
            log.error("Stripe error during checkout creation: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public PortalResponse openCustomerPortal()
    {
        return null;
    }
}
