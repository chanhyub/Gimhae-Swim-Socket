package com.alijas.gimhaeswimsocket.modules.team.repository;

import com.alijas.gimhaeswimsocket.modules.team.entity.Team;
import com.alijas.gimhaeswimsocket.modules.team.entity.TeamMember;
import com.alijas.gimhaeswimsocket.modules.team.enums.TeamMemberPosition;
import com.alijas.gimhaeswimsocket.modules.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {

    List<TeamMember> findByTeam(Team team);

    TeamMember findByTeamAndPosition(Team team, TeamMemberPosition position);

    Optional<TeamMember> findByUser(User user);
}
