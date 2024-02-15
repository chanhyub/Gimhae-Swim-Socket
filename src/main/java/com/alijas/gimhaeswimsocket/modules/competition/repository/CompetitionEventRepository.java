package com.alijas.gimhaeswimsocket.modules.competition.repository;


import com.alijas.gimhaeswimsocket.modules.competition.entity.CompetitionEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompetitionEventRepository extends JpaRepository<CompetitionEvent, Long> {
    Page<CompetitionEvent> findByCompetitionId(Long competitionId, Pageable pageable);
}
