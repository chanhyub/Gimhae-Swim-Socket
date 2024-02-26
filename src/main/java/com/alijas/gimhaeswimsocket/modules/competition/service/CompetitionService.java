package com.alijas.gimhaeswimsocket.modules.competition.service;

import com.alijas.gimhaeswim.module.competition.enums.status.CompetitionStatus;
import com.alijas.gimhaeswimsocket.modules.competition.entity.Competition;
import com.alijas.gimhaeswimsocket.modules.competition.repository.CompetitionRepository;
import com.alijas.gimhaeswimsocket.modules.competition.response.CompetitionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompetitionService {

    private final CompetitionRepository competitionRepository;

    public CompetitionService(CompetitionRepository competitionRepository) {
        this.competitionRepository = competitionRepository;
    }


    public Page<CompetitionResponse> getCompetitionList(Pageable pageable) {
        return competitionRepository.findAllByStatus(pageable, CompetitionStatus.ACTIVE)
                .map(Competition::toResponse);
    }

    public Optional<Competition> getCompetition(Long competitionId) {
        return competitionRepository.findById(competitionId);
    }
}
