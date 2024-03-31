package com.alijas.gimhaeswimsocket.modules.record.controller;


import com.alijas.gimhaeswimsocket.modules.common.exception.CustomRestException;
import com.alijas.gimhaeswimsocket.modules.competition.entity.CompetitionEvent;
import com.alijas.gimhaeswimsocket.modules.competition.service.CompetitionEventService;
import com.alijas.gimhaeswimsocket.modules.lane.entity.Lane;
import com.alijas.gimhaeswimsocket.modules.lane.service.LaneService;
import com.alijas.gimhaeswimsocket.modules.record.dto.RecordTotalSaveRequest;
import com.alijas.gimhaeswimsocket.modules.record.request.RecordSaveRequest;
import com.alijas.gimhaeswimsocket.modules.record.service.RecordService;
import com.alijas.gimhaeswimsocket.modules.section.entity.Section;
import com.alijas.gimhaeswimsocket.modules.section.service.SectionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/records")
public class RecordController {

    private final RecordService recordService;

    private final LaneService laneService;

    private final SectionService sectionService;

    private final CompetitionEventService competitionEventService;

    public RecordController(RecordService recordService, LaneService laneService, SectionService sectionService, CompetitionEventService competitionEventService) {
        this.recordService = recordService;
        this.laneService = laneService;
        this.sectionService = sectionService;
        this.competitionEventService = competitionEventService;
    }

    @PostMapping
    public ResponseEntity<String> saveRecord(
        @Valid @RequestBody RecordSaveRequest recordSaveRequest,
        Errors errors
    ) {
        if (errors.hasErrors()) {
            throw new CustomRestException(errors.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }

        Lane lane = laneService.getLane(recordSaveRequest.getLaneId()).orElseThrow(() -> new CustomRestException("레인이 존재하지 않습니다.", HttpStatus.BAD_REQUEST));

        recordService.saveRecord(recordSaveRequest, lane);

        return ResponseEntity.ok("저장되었습니다.");
    }

    @PostMapping("/total")
    public ResponseEntity<String> saveTotalRecord(
        @Valid @RequestBody RecordTotalSaveRequest recordTotalSaveRequest,
        Errors errors
    ) {
        if (errors.hasErrors()) {
            throw new CustomRestException(errors.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }

        Section section = sectionService.getSection(recordTotalSaveRequest.sectionId()).orElseThrow(() -> new CustomRestException("조가 존재하지 않습니다.", HttpStatus.BAD_REQUEST));
        CompetitionEvent competitionEvent = competitionEventService.getCompetitionEvent(recordTotalSaveRequest.competitionEventId()).orElseThrow(() -> new CustomRestException("대회 종목이 존재하지 않습니다.", HttpStatus.BAD_REQUEST));
        String competitionEventType = section.getCompetitionEvent().getEventType().toString().split("_")[0];
        if (competitionEventType.equals("ORGANIZATION")) {
            List<Lane> laneList = laneService.findBySectionAndTeamMemberIsNotNullAndRefereeIsNotNull(section);
            recordService.saveTeamTotalRecord(laneList, competitionEvent);
        } else {
            List<Lane> laneList = laneService.findBySectionAndUserIsNotNullAndRefereeIsNotNull(section);
            recordService.saveIndividualTotalRecord(laneList, competitionEvent);
        }

        return ResponseEntity.ok("저장되었습니다.");
    }


}
