package ru.explorewithme.repository.request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.explorewithme.model.request.Request;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Jpa Repository для запросов пользователей
 */

public interface RequestRepository extends JpaRepository<Request, Long>, RequestRepositoryCustom {
    /**
     * Получение списка запросов по идентификатору создателя запроса
     *
     * @param userId идентификатор пользователя
     */
    @Query("select r from Request r where r.requester.id = ?1")
    List<Request> findAllByRequesterId(Long userId);

    /**
     * Получение запроса по идентификатору
     *
     * @param requestId идентификатор запроса
     */
    @Query("select r from Request r where r.requester.id = ?1")
    Request findByRequesterId(Long requestId);

    /**
     * Получение списка запросов по идетификатору события
     *
     * @param eventId идентификатор события
     */
    @Query("select r from Request r where r.event.id = ?1")
    List<Request> findByEventId(Long eventId);

    /**
     * Получение количества запросов по идентификатору события
     *
     * @param eventId идентификатор события
     */
    @Query("select count(r) from Request r where r.event.id = ?1")
    Long countByEventId(Long eventId);

    /**
     * Получение списка запросов после определенного момента времени
     *
     * @param created время начала поиска
     */
    @Query("select r from Request r where r.created > ?1")
    List<Request> findAllByCreatedAfter(LocalDateTime created);
}
