package com.alijas.gimhaeswimsocket.modules.lane.controller;

import com.alijas.gimhaeswimsocket.modules.common.exception.CustomRestException;
import com.alijas.gimhaeswimsocket.modules.lane.response.LaneResponse;
import com.alijas.gimhaeswimsocket.modules.lane.service.LaneService;
import com.alijas.gimhaeswimsocket.modules.section.entity.Section;
import com.alijas.gimhaeswimsocket.modules.section.service.SectionService;
import com.alijas.gimhaeswimsocket.security.SecurityUser;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("lanes")
public class LaneController {

    private final LaneService laneService;

    private final SectionService sectionService;

    public LaneController(LaneService laneService, SectionService sectionService) {
        this.laneService = laneService;
        this.sectionService = sectionService;
    }

    @GetMapping
    public ResponseEntity<List<LaneResponse>> getLanes(
            @AuthenticationPrincipal SecurityUser securityUser,
            @RequestParam(value = "sectionId", required = false) Long sectionId
    ) {
        if (sectionId == null) {
            throw new CustomRestException("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
        }

        Section section = sectionService.getSection(sectionId).orElseThrow(() -> new CustomRestException("조를 찾을 수 없습니다.", HttpStatus.BAD_REQUEST));
        return ResponseEntity.ok(laneService.getLaneBySectionId(section, securityUser.getUser()));
    }
}
