package com.alijas.gimhaeswimsocket.modules.lane.dto;

import jakarta.validation.constraints.NotNull;

public record LaneUpdateRequest (
        @NotNull(message = "레인 고유 번호는 필수입니다.")
        Long laneId
) {

}
