package ru.explorewithme.ewm.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.explorewithme.ewm.dto.StatisticDto;
import ru.explorewithme.ewm.dto.StatisticDtoShort;
import ru.explorewithme.ewm.model.Statistic;
import ru.explorewithme.ewm.repository.StatisticRepository;
import ru.explorewithme.ewm.util.StatisticMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {

    private final StatisticRepository statisticRepository;

    @Override
    public StatisticDto postStatistic(StatisticDto statisticDto) {
        Statistic statistic = StatisticMapper.toStatistic(statisticDto);
        statistic = statisticRepository.save(statistic);
        log.info("Statistics postStatistic: {}", statistic);
        return StatisticMapper.toStatisticDto(statistic);
    }

    @Override
    public List<StatisticDtoShort> getStatsByUri(LocalDateTime start, LocalDateTime end,
                                                 List<String> uris, Boolean unique) {
        if (start == null) {
            start = LocalDateTime.now().withNano(0).minusYears(1);
        }
        if (end == null) {
            end = LocalDateTime.now().withNano(0);
        }
        List<StatisticDtoShort> statistics = new ArrayList<>();
        for (String uri : uris) {
            Statistic statistic = statisticRepository.findAllByUriContainsAndTimestampBetween(uri, start, end);
            StatisticDtoShort statisticDtoShort = StatisticMapper.toStatisticDtoShort(statistic);
            if (unique) {
                statisticDtoShort.setHits(statisticRepository.countDistinctByIpAndUri(statistic.getIp(), uri));
            } else {
                statisticDtoShort.setHits(statisticRepository.countByUri(uri));
            }
            statistics.add(statisticDtoShort);
        }
        log.info("Statistics getStatsByUri: {}", statistics);
        return statistics;
    }
}
