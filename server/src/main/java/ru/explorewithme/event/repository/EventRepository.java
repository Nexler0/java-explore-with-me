package ru.explorewithme.event.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.explorewithme.event.dto.EventDto;
import ru.explorewithme.event.model.Event;

import java.util.List;

@RepositoryRestResource(path = "events")
public interface EventRepository extends JpaRepository<Event, Long>, EventRepositoryCustom {

    List<Event> findAllById(Long id, PageRequest of);
}
