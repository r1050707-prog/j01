package kr.co.jcovenant.inquiry;

import java.time.ZonedDateTime;

public record Inquiry(
        long id,
        String name,
        String organization,
        String phone,
        String email,
        String message,
        ZonedDateTime createdAt
) {
}
