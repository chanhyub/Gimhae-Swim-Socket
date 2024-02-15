package com.alijas.gimhaeswimsocket.modules.competition.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public record CompetitionEventResponse(
        Long id,
        String eventType,
        String gender,
        String department,
        String event,
        String meter
) {

}
