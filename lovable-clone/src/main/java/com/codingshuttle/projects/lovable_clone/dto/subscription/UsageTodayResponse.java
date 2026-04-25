package com.codingshuttle.projects.lovable_clone.dto.subscription;

public record UsageTodayResponse
        (
                int tokensUsed,
                int tokensLimit,
                int previewsRunning,
                int previewsLimit
        )
{
}
// variable values are not expected to null , so wrapper classes arent used here ,
// 0 is accepted