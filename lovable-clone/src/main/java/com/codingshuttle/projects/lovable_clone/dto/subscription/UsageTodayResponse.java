package com.codingshuttle.projects.lovable_clone.dto.subscription;

public record UsageTodayResponse
        (
                Integer tokensLimit,
                Integer tokensUsed,
                Integer previewsRunning,
                Integer previewsLimit
        )
{
}