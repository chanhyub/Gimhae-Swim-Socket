package com.alijas.gimhaeswimsocket.modules.competition.controller;

import com.alijas.gimhaeswimsocket.modules.competition.response.CompetitionResponse;
import com.alijas.gimhaeswimsocket.modules.competition.service.CompetitionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/competitions")
public class CompetitionController {

    private final CompetitionService competitionService;

    public CompetitionController(CompetitionService competitionService) {
        this.competitionService = competitionService;
    }

    @GetMapping({"", "/"})
    public ResponseEntity<Page<CompetitionResponse>> getCompetitionList(
            @PageableDefault(sort = "id" ,direction = Sort.Direction.DESC, size = 10) Pageable pageable
    ) {
        return ResponseEntity.ok(competitionService.getCompetitionList(pageable));
    }

}
