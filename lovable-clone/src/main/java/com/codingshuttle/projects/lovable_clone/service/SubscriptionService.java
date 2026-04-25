package com.codingshuttle.projects.lovable_clone.service;

import com.codingshuttle.projects.lovable_clone.dto.subscription.SubscriptionResponse;

public interface SubscriptionService
{

    SubscriptionResponse getCurrentSubscription(Long userId);
}
