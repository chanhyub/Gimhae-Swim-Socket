package com.alijas.gimhaeswimsocket.modules.team.repository;

import com.alijas.gimhaeswimsocket.modules.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {

    Optional<Team> findByTeamName(String teamName);
}
