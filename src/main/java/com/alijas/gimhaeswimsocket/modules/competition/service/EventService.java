package com.alijas.gimhaeswimsocket.modules.competition.service;

import com.alijas.gimhaeswim.module.competition.enums.status.EventStatus;
import com.alijas.gimhaeswimsocket.modules.competition.entity.Event;
import com.alijas.gimhaeswimsocket.modules.competition.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getEventList() {
        return eventRepository.findAllByStatus(EventStatus.ACTIVE);
    }

    public List<Event> findAll() {
        return eventRepository.findAll();
    }
}
