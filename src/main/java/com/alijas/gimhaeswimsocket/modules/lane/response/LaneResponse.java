package com.alijas.gimhaeswimsocket.modules.lane.response;

import com.alijas.gimhaeswimsocket.modules.referee.dto.RefereeLaneDTO;
import com.alijas.gimhaeswimsocket.modules.user.dto.UserLaneDTO;

public record LaneResponse(
        Long id,
        Integer laneNumber,
        UserLaneDTO user,
        RefereeLaneDTO referee,
        Boolean isComplete
) {
}
