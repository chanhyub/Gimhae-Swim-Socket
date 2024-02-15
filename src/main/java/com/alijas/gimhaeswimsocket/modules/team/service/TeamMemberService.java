package com.alijas.gimhaeswimsocket.modules.team.service;


import com.alijas.gimhaeswimsocket.modules.team.entity.Team;
import com.alijas.gimhaeswimsocket.modules.team.entity.TeamMember;
import com.alijas.gimhaeswimsocket.modules.team.enums.TeamMemberPosition;
import com.alijas.gimhaeswimsocket.modules.team.repository.TeamMemberRepository;
import com.alijas.gimhaeswimsocket.modules.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TeamMemberService {

    private final TeamMemberRepository teamMemberRepository;

    public TeamMemberService(TeamMemberRepository teamMemberRepository) {
        this.teamMemberRepository = teamMemberRepository;
    }

    @Transactional
    public void saveTeamMember(Team team, User user, String position) {
        teamMemberRepository.save(
                new TeamMember(
                        null,
                        team,
                        user,
                        TeamMemberPosition.valueOf(position)
                )
        );
    }

    public List<TeamMember> getTeamMemberList(Team team) {
        return teamMemberRepository.findByTeam(team);
    }

    public Optional<TeamMember> getUserTeam(User user) {
        return teamMemberRepository.findByUser(user);
    }

    @Transactional
    public void deleteTeamMember(TeamMember teamMember) {
        teamMemberRepository.delete(teamMember);
    }

    public Optional<TeamMember> getTeamMember(Long teamMemberId) {
        return teamMemberRepository.findById(teamMemberId);
    }
}
