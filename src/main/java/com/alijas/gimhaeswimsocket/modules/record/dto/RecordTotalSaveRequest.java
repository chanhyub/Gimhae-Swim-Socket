package com.alijas.gimhaeswimsocket.modules.record.dto;

import jakarta.validation.constraints.NotNull;

public record RecordTotalSaveRequest(
        @NotNull(message = "조 고유번호는 필수입니다.")
        Long sectionId,

        @NotNull(message = "대회 종목 고유번호는 필수입니다.")
        Long competitionEventId
){
}
