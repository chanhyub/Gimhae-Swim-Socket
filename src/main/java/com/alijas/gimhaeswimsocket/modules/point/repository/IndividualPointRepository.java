package com.alijas.gimhaeswimsocket.modules.point.repository;

import com.alijas.gimhaeswimsocket.modules.competition.entity.CompetitionEvent;
import com.alijas.gimhaeswimsocket.modules.point.entity.IndividualPoint;
import com.alijas.gimhaeswimsocket.modules.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IndividualPointRepository extends JpaRepository<IndividualPoint, Long> {

    Optional<IndividualPoint> findByCompetitionEventAndUser(CompetitionEvent competitionEvent, User user);
}
