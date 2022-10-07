package ru.explorewithme.statistic.service;

import ru.explorewithme.statistic.dto.StatisticDto;
import ru.explorewithme.statistic.dto.StatisticDtoShort;

import java.time.LocalDateTime;
import java.util.List;

public interface StatisticService {

    StatisticDto postStatistic(StatisticDto statistic);

    List<StatisticDtoShort> getStatsByUri(LocalDateTime start,
                                          LocalDateTime end,
                                          List<String> uris,
                                          Boolean unique);
}
