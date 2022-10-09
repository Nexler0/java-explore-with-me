package ru.explorewithme.repository.statistic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.explorewithme.model.statistic.Statistic;

import java.time.LocalDateTime;
import java.util.List;

/**
 * JPA Repository для статистики
 */

@RepositoryRestResource(path = "statistics")
public interface StatisticRepository extends JpaRepository<Statistic, Long>, StatisticRepositoryCustom {
    /**
     * Получение списка статистики по параметрам
     *
     * @param uri   URI для поиска
     * @param start период начала поиска
     * @param end   период окончания поиска
     */
    @Query("select s from Statistic s where s.uri = ?1 and s.timestamp between ?2 and ?3")
    List<Statistic> findAllByUriEqualsAndTimestampBetween(String uri, LocalDateTime start, LocalDateTime end);

    /**
     * Получение количества записей в таблизе по запросу
     *
     * @param uri URI для поиска
     */
    @Query("select count(s) from Statistic s where s.uri = ?1")
    Integer countByUri(String uri);

    /**
     * Получение количества уникальних запросов по IP и URI
     *
     * @param ip  IP адрес
     * @param uri URI для поиска
     */
    @Query("select count(distinct s) from Statistic s where s.ip = ?1 and s.uri = ?2")
    Integer countDistinctByIpAndUri(String ip, String uri);
}
