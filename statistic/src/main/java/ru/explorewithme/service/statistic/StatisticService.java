package ru.explorewithme.service.statistic;


import ru.explorewithme.dto.statistic.StatisticDto;
import ru.explorewithme.dto.statistic.StatisticDtoShort;

import java.time.LocalDateTime;
import java.util.List;

public interface StatisticService {

    StatisticDto postStatistic(StatisticDto statistic);

    List<StatisticDtoShort> getStatsByUri(LocalDateTime start,
                                          LocalDateTime end,
                                          List<String> uris,
                                          Boolean unique);
}
