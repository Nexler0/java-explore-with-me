package ru.explorewithme.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.explorewithme.request.model.Request;

import java.time.LocalDateTime;
import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long>, RequestRepositoryCustom {

    @Query("select r from Request r where r.requester.id = ?1")
    List<Request> findAllByRequesterId(Long userId);

    @Query("select r from Request r where r.requester.id = ?1")
    Request findByRequesterId(Long id);

    @Query("select (count(r) > 0) from Request r where r.requester.id = ?1")
    Boolean existsByRequesterId(Long id);

    @Query("select r from Request r where r.event.id = ?1")
    List<Request> findByEventId(Long id);

    @Query("select count(r) from Request r where r.event.id = ?1")
    Long countByEventId(Long id);

    @Query("select r from Request r where r.created > ?1")
    List<Request> findAllByCreatedAfter(LocalDateTime created);
}
