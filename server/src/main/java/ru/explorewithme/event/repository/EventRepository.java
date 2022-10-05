package ru.explorewithme.event.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.explorewithme.event.model.Event;
import ru.explorewithme.event.model.State;

import java.time.LocalDateTime;
import java.util.List;

@RepositoryRestResource(path = "events")
public interface EventRepository extends JpaRepository<Event, Long>, EventRepositoryCustom {

    @Query("select e from Event e where e.id = ?1")
    List<Event> findAllById(Long id, PageRequest of);

        @Query("select e from Event e where e.state = ?1 and e.createdOn between ?2 and ?3")
        Page<Event> findAllByStateAndCreatedOnIsBetween(State state,
                                                        LocalDateTime rangeStart,
                                                        LocalDateTime rangeEnd,
                                                        Pageable pageable);

    @Query("select e from Event e where e.initiator.id = ?1")
    Event findByInitiatorId(Long userId);

    @Query("select e from Event e where e.initiator.id = ?1")
    List<Event> findAllByInitiatorId(Long userId, Pageable pageable);
}
