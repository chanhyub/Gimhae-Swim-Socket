package com.alijas.gimhaeswimsocket.modules.team.service;

import com.alijas.gimhaeswimsocket.modules.team.entity.Team;
import com.alijas.gimhaeswimsocket.modules.team.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Optional<Team> getTeam(Long teamId) {
        return teamRepository.findById(teamId);
    }

    public Optional<Team> getTeam(String teamName) {
        return teamRepository.findByTeamName(teamName);
    }

    public Team saveTeam(String teamName) {
        return teamRepository.save(
                new Team(
                        null,
                        teamName
                )
        );
    }

}

