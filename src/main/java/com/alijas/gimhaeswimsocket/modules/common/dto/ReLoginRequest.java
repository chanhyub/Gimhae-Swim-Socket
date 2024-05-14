package com.alijas.gimhaeswimsocket.modules.common.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public record ReLoginRequest(
        @NotEmpty(message = "refreshToken은 필수 값입니다.")
        String refreshToken
) {
}
