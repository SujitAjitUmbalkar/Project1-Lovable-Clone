package com.codingshuttle.projects.lovable_clone.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

// “Plan defines the limits and pricing configuration,
// and it is linked to users through the Subscription table to enforce usage restrictions.”

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Plan
{

    Long id; // primary key → unique identifier of plan

    String name; // plan name → e.g. Free, Pro, Enterprise

    String stripePriceId; // external pricing id from Stripe → used for billing integration

    Integer maxProjects; // max number of projects a user can create under this plan

    Integer maxTokensPerDay; // daily token usage limit for AI operations

    Integer maxPreviews; // max number of live previews/deployments allowed

    Boolean unlimitedAi; // if true → ignore token limit (maxTokensPerDay not applied)

    Boolean active; // whether this plan is currently available for users
}