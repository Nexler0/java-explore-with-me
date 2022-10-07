package ru.explorewithme.ewm.service;

import ru.explorewithme.ewm.dto.StatisticDto;
import ru.explorewithme.ewm.dto.StatisticDtoShort;

import java.time.LocalDateTime;
import java.util.List;

public interface StatisticService {

    StatisticDto postStatistic(StatisticDto statistic);

    List<StatisticDtoShort> getStatsByUri(LocalDateTime start,
                                          LocalDateTime end,
                                          List<String> uris,
                                          Boolean unique);
}
