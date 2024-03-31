package com.alijas.gimhaeswimsocket.modules.point.repository;

import com.alijas.gimhaeswimsocket.modules.competition.entity.CompetitionEvent;
import com.alijas.gimhaeswimsocket.modules.point.entity.TeamPoint;
import com.alijas.gimhaeswimsocket.modules.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamPointRepository extends JpaRepository<TeamPoint, Long> {
    TeamPoint findByCompetitionEventAndTeam(CompetitionEvent competitionEvent, Team team);
}
