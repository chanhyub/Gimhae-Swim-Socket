package com.alijas.gimhaeswimsocket.modules.section.controller;

import com.alijas.gimhaeswimsocket.modules.common.exception.CustomRestException;
import com.alijas.gimhaeswimsocket.modules.competition.entity.CompetitionEvent;
import com.alijas.gimhaeswimsocket.modules.competition.service.CompetitionEventService;
import com.alijas.gimhaeswimsocket.modules.section.response.SectionResponse;
import com.alijas.gimhaeswimsocket.modules.section.service.SectionService;
import com.alijas.gimhaeswimsocket.security.SecurityUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sections")
public class SectionController {

    private final SectionService sectionService;

    private final CompetitionEventService competitionEventService;

    public SectionController(SectionService sectionService, CompetitionEventService competitionEventService) {
        this.sectionService = sectionService;
        this.competitionEventService = competitionEventService;
    }

    @GetMapping
    public ResponseEntity<List<SectionResponse>> getSections(
            @RequestParam(value = "competitionEventId", required = true) Long competitionEventId
    ) {
        if (competitionEventId == null) {
            throw new CustomRestException("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
        }

        CompetitionEvent competitionEvent = competitionEventService.getCompetitionEvent(competitionEventId).orElseThrow(() -> new CustomRestException("해당 대회 종목을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST));



        return ResponseEntity.ok(sectionService.getSectionByCompetitionEvent(competitionEvent));
    }
}
