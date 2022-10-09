package ru.explorewithme.repository.event;

import org.springframework.context.annotation.Lazy;

public class EventRepositoryImpl implements EventRepositoryCustom {

    private final EventRepository eventRepository;

    public EventRepositoryImpl(@Lazy EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }
}
