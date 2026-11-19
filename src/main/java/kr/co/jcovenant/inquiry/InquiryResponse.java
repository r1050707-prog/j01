package kr.co.jcovenant.inquiry;

import java.time.format.DateTimeFormatter;

public record InquiryResponse(
        long id,
        String name,
        String organization,
        String phone,
        String email,
        String message,
        String createdAt
) {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static InquiryResponse from(Inquiry inquiry) {
        String email = inquiry.email() == null ? "" : inquiry.email();
        return new InquiryResponse(
                inquiry.id(),
                inquiry.name(),
                inquiry.organization(),
                inquiry.phone(),
                email,
                inquiry.message(),
                inquiry.createdAt().format(FORMATTER)
        );
    }
}
