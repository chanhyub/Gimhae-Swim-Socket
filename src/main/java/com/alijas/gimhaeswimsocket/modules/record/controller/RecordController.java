package com.alijas.gimhaeswimsocket.modules.record.controller;


import com.alijas.gimhaeswimsocket.modules.common.exception.CustomRestException;
import com.alijas.gimhaeswimsocket.modules.competition.entity.CompetitionEvent;
import com.alijas.gimhaeswimsocket.modules.competition.service.CompetitionEventService;
import com.alijas.gimhaeswimsocket.modules.lane.entity.Lane;
import com.alijas.gimhaeswimsocket.modules.lane.service.LaneService;
import com.alijas.gimhaeswimsocket.modules.record.request.RecordSaveRequest;
import com.alijas.gimhaeswimsocket.modules.record.service.RecordService;
import com.alijas.gimhaeswimsocket.modules.section.entity.Section;
import com.alijas.gimhaeswimsocket.modules.section.service.SectionService;
import com.alijas.gimhaeswimsocket.modules.user.entity.User;
import com.alijas.gimhaeswimsocket.modules.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/records")
public class RecordController {

    private final RecordService recordService;

    private final SectionService sectionService;

    private final LaneService laneService;

    private final CompetitionEventService competitionEventService;

    private final UserService userService;

    public RecordController(RecordService recordService, SectionService sectionService, LaneService laneService, CompetitionEventService competitionEventService, UserService userService) {
        this.recordService = recordService;
        this.sectionService = sectionService;
        this.laneService = laneService;
        this.competitionEventService = competitionEventService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> saveRecord(
        @Valid @RequestBody List<RecordSaveRequest> recordSaveRequestList,
        Errors errors
    ) {
        if (errors.hasErrors()) {
            throw new CustomRestException(errors.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }

        recordSaveRequestList.forEach((recordSaveRequest) -> {
            laneService.getLane(recordSaveRequest.getLaneId()).orElseThrow(() -> new CustomRestException("레인이 존재하지 않습니다.", HttpStatus.BAD_REQUEST));
            userService.getUser(recordSaveRequest.getUserId()).orElseThrow(() -> new CustomRestException("경기인이 존재하지 않습니다.", HttpStatus.BAD_REQUEST));
        });

        recordService.saveRecord(recordSaveRequestList);

        return ResponseEntity.ok("저장되었습니다.");
    }
}
