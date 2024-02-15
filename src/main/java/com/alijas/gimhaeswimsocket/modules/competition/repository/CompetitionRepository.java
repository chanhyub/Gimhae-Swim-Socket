package com.alijas.gimhaeswimsocket.modules.competition.repository;

import com.alijas.gimhaeswim.module.competition.enums.status.CompetitionStatus;
import com.alijas.gimhaeswimsocket.modules.competition.entity.Competition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompetitionRepository extends JpaRepository<Competition, Long> {

    Page<Competition> findAllByStatus(Pageable pageable, CompetitionStatus status);
}
