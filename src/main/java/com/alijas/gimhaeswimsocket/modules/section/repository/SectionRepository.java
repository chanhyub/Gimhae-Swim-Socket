package com.alijas.gimhaeswimsocket.modules.section.repository;

import com.alijas.gimhaeswimsocket.modules.competition.entity.CompetitionEvent;
import com.alijas.gimhaeswimsocket.modules.section.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SectionRepository extends JpaRepository<Section, Long> {
    List<Section> findByCompetitionEventId(Long competitionEventId);

    List<Section> findByCompetitionEvent(CompetitionEvent competitionEvent);
}
