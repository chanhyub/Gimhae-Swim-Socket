package com.alijas.gimhaeswimsocket.modules.section.controller;

import com.alijas.gimhaeswimsocket.modules.common.exception.CustomRestException;
import com.alijas.gimhaeswimsocket.modules.competition.entity.CompetitionEvent;
import com.alijas.gimhaeswimsocket.modules.competition.service.CompetitionEventService;
import com.alijas.gimhaeswimsocket.modules.section.dto.SectionUpdateRequest;
import com.alijas.gimhaeswimsocket.modules.section.dto.SectionUpdateResponse;
import com.alijas.gimhaeswimsocket.modules.section.entity.Section;
import com.alijas.gimhaeswimsocket.modules.section.response.SectionResponse;
import com.alijas.gimhaeswimsocket.modules.section.service.SectionService;
import com.alijas.gimhaeswimsocket.security.SecurityUser;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
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
            @RequestParam(value = "competitionEventId", required = false) Long competitionEventId
    ) {
        if (competitionEventId == null) {
            throw new CustomRestException("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
        }

        CompetitionEvent competitionEvent = competitionEventService.getCompetitionEvent(competitionEventId).orElseThrow(() -> new CustomRestException("해당 대회 종목을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST));

        return ResponseEntity.ok(sectionService.getSectionByCompetitionEvent(competitionEvent));
    }

    @PutMapping
    public ResponseEntity<SectionUpdateResponse> updateSection(
            @Valid @RequestBody SectionUpdateRequest sectionUpdateRequest,
            Errors errors
    ) {
        if (errors.hasErrors()) {
            throw new CustomRestException(errors.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }

        Section section = sectionService.getSection(sectionUpdateRequest.sectionId()).orElseThrow(() -> new CustomRestException("해당 조를 찾을 수 없습니다.", HttpStatus.BAD_REQUEST));

        sectionService.updateSection(section);

        return ResponseEntity.ok(new SectionUpdateResponse("조 기록 측정이 완료되었습니다."));
    }
}
