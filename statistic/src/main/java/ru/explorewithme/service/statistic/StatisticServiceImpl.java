package ru.explorewithme.service.statistic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.explorewithme.dto.statistic.StatisticDto;
import ru.explorewithme.dto.statistic.StatisticDtoShort;
import ru.explorewithme.model.statistic.Statistic;
import ru.explorewithme.repository.statistic.StatisticRepository;
import ru.explorewithme.util.statistic.StatisticMapper;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
            start = LocalDateTime.now().withNano(0).minusYears(100);
        }
        if (end == null) {
            end = LocalDateTime.now().withNano(0);
        }
        List<Statistic> statistics = new ArrayList<>();
        for (String uri : uris) {
            statistics = statisticRepository.findAllByUriEqualsAndTimestampBetween(uri, start, end);
            for (Statistic s : statistics) {
                if (unique) {
                    s.setHits(statisticRepository.countDistinctByIpAndUri(s.getIp(), uri));
                } else {
                    s.setHits(statisticRepository.countByUri(uri));
                }
            }
        }
        log.info("Statistics getStatsByUri: {}", statistics);
        return statistics.stream().map(StatisticMapper::toStatisticDtoShort).collect(Collectors.toList());
    }
}
