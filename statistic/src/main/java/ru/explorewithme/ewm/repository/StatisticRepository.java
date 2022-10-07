package ru.explorewithme.ewm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.explorewithme.ewm.model.Statistic;

import java.time.LocalDateTime;
import java.util.List;

@RepositoryRestResource(path = "statistics")
public interface StatisticRepository extends JpaRepository<Statistic, Long>, StatisticRepositoryCustom {

    @Query("select s from Statistic s where s.uri = ?1 and s.timestamp between ?2 and ?3")
    List<Statistic> findAllByUriEqualsAndTimestampBetween(String uri, LocalDateTime start, LocalDateTime end);

    @Query("select count(s) from Statistic s where s.uri = ?1")
    Integer countByUri(String uri);

    @Query("select count(distinct s) from Statistic s where s.ip = ?1 and s.uri = ?2")
    Integer countDistinctByIpAndUri(String ip, String uri);
}
