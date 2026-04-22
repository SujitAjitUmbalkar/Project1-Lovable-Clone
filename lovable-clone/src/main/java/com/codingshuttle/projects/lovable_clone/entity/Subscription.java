package com.codingshuttle.projects.lovable_clone.entity;

import com.codingshuttle.projects.lovable_clone.enums.SubscriptionStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import java.time.Instant;

/*
 *Subscription Entity Usage:
 * Represents the billing relationship between a User and a Plan.
 * It tracks the current state of access (active, cancelled, past_due),
 * manages synchronization with the payment provider (Stripe), and
 * enforces feature limits based on the billing cycle.
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Subscription
{
    Long id; // PK: Unique identifier for the subscription record

    User user; // FK: The user who owns this subscription and is billed for it
    Plan plan; // FK: The specific pricing tier (e.g., Pro, Enterprise) currently active

    SubscriptionStatus status ;  // State: Current standing ( ACTIVE, TRIALING, CANCELED, PAST_DUE, INCOMPLETE) determining access

    String stripeCustomerId; // External Ref: The unique ID for this user in Stripe's system
    String stripeSubscriptionId; // External Ref: The specific recurring payment object ID in Stripe

    Instant currentPeriodStart; // Billing: The start timestamp of the current billing cycle
    Instant currentPeriodEnd; // Billing: The expiration timestamp; access is revoked after this if not renewed
    Boolean cancelAtPeriodEnd = false; // Churn Logic: If true, subscription stops at currentPeriodEnd (no auto-renew)

    Instant createdAt; // Audit: When the subscription was first initialized
    Instant updatedAt; // Audit: Last time status or period details changed
}
