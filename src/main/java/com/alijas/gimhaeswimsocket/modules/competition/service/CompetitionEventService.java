package com.alijas.gimhaeswimsocket.modules.competition.service;

import com.alijas.gimhaeswimsocket.modules.common.exception.CustomRestException;
import com.alijas.gimhaeswimsocket.modules.competition.dto.CompetitionEventResponse;
import com.alijas.gimhaeswimsocket.modules.competition.entity.CompetitionEvent;
import com.alijas.gimhaeswimsocket.modules.competition.repository.CompetitionEventRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompetitionEventService {

    private final CompetitionEventRepository competitionEventRepository;

    public CompetitionEventService(CompetitionEventRepository competitionEventRepository) {
        this.competitionEventRepository = competitionEventRepository;
    }

    public Page<CompetitionEventResponse> getCompetitionEventByCompetitionId(Long competitionId, Pageable pageable) {

        Page<CompetitionEvent> competitionEventPage = competitionEventRepository.findByCompetitionId(competitionId, pageable);
        if (competitionEventPage.isEmpty()) {
            throw new CustomRestException("해당 대회에 등록된 경기가 없습니다.", HttpStatus.BAD_REQUEST);
        }

//        List<CompetitionEventResponse> list = competitionEventPage
//                .stream()
//                .map(CompetitionEvent::toCompetitionEventResponse)
//                .toList();

        return competitionEventPage.map(CompetitionEvent::toCompetitionEventResponse);
    }

    public Optional<CompetitionEvent> getCompetitionEvent(Long competitionEventId) {
        return competitionEventRepository.findById(competitionEventId);
    }
}
