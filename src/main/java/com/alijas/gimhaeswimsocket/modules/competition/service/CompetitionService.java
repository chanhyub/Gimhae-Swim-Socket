package com.alijas.gimhaeswimsocket.modules.competition.service;

import com.alijas.gimhaeswim.module.competition.enums.status.CompetitionStatus;
import com.alijas.gimhaeswimsocket.modules.competition.entity.Competition;
import com.alijas.gimhaeswimsocket.modules.competition.repository.CompetitionRepository;
import com.alijas.gimhaeswimsocket.modules.competition.response.CompetitionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class CompetitionService {

    private final CompetitionRepository competitionRepository;

    public CompetitionService(CompetitionRepository competitionRepository) {
        this.competitionRepository = competitionRepository;
    }


    public Page<CompetitionResponse> getCompetitionList(Pageable pageable) {
        Page<Competition> competitionPage = competitionRepository.findAllByStatus(pageable, CompetitionStatus.ACTIVE);

        List<CompetitionResponse> list = competitionPage
                .stream()
                .map(Competition::toResponse)
                .toList();

        return new PageImpl<>(list, pageable, competitionPage.getTotalElements());
    }

    public Optional<Competition> getComeptition(Long competitionId) {
        return competitionRepository.findById(competitionId);
    }
}
