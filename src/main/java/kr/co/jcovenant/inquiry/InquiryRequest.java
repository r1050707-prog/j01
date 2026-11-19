package kr.co.jcovenant.inquiry;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record InquiryRequest(
        @NotBlank(message = "이름을 입력해 주세요.")
        @Size(max = 40, message = "이름은 40자 안으로 입력해 주세요.")
        String name,

        @NotBlank(message = "시설명 또는 기관명을 입력해 주세요.")
        @Size(max = 80, message = "시설명은 80자 안으로 입력해 주세요.")
        String organization,

        @NotBlank(message = "연락처를 입력해 주세요.")
        @Pattern(regexp = "^[0-9+\\-\\s()]{8,30}$", message = "연락처 형식을 확인해 주세요.")
        String phone,

        @Email(message = "이메일 형식을 확인해 주세요.")
        @Size(max = 120, message = "이메일은 120자 안으로 입력해 주세요.")
        String email,

        @NotBlank(message = "문의 내용을 입력해 주세요.")
        @Size(max = 2000, message = "문의 내용은 2000자 안으로 입력해 주세요.")
        String message
) {
}
