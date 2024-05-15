package com.alijas.gimhaeswimsocket.modules.section.dto;

import jakarta.validation.constraints.NotNull;

public record SectionUpdateRequest(
        @NotNull(message = "조 고유 번호는 필수입니다.")
        Long sectionId
) {
}
