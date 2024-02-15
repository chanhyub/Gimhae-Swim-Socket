package com.alijas.gimhaeswimsocket.modules.competition.repository;

import com.alijas.gimhaeswim.module.competition.enums.status.EventStatus;
import com.alijas.gimhaeswimsocket.modules.competition.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

        List<Event> findAllByStatus(EventStatus status);
}
