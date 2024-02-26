package com.alijas.gimhaeswimsocket.modules.competition.controller;

import com.alijas.gimhaeswimsocket.modules.common.exception.CustomRestException;
import com.alijas.gimhaeswimsocket.modules.competition.dto.CompetitionEventResponse;
import com.alijas.gimhaeswimsocket.modules.competition.service.CompetitionEventService;
import com.alijas.gimhaeswimsocket.modules.competition.service.CompetitionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/competition-events")
public class CompetitionEventController {

    private final CompetitionEventService competitionEventService;

    private final CompetitionService competitionService;

    public CompetitionEventController(CompetitionEventService competitionEventService, CompetitionService competitionService) {
        this.competitionEventService = competitionEventService;
        this.competitionService = competitionService;
    }

    @GetMapping
    public ResponseEntity<Page<CompetitionEventResponse>> getCompetitionEventList(
            @PageableDefault(sort = "id" ,direction = Sort.Direction.DESC, size = 10) Pageable pageable,
            @RequestParam(value = "competitionId", required = false) Long competitionId
    ) {
        if (competitionId == null) {
            throw new CustomRestException("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
        }

        competitionService.getCompetition(competitionId).orElseThrow(() -> new CustomRestException("해당 대회를 찾을 수 없습니다.", HttpStatus.BAD_REQUEST));
        return ResponseEntity.ok(competitionEventService.getCompetitionEventByCompetitionId(competitionId, pageable));
    }
}
