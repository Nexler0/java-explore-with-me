package ru.explorewithme.statistic.util;

import ru.explorewithme.statistic.dto.StatisticDto;
import ru.explorewithme.statistic.dto.StatisticDtoShort;
import ru.explorewithme.statistic.model.Statistic;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StatisticMapper {

    private static final DateTimeFormatter DATA_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static Statistic toStatistic(StatisticDto statisticDto) {
        return new Statistic(
                statisticDto.getId(),
                statisticDto.getApp(),
                statisticDto.getUri(),
                statisticDto.getIp(),
                LocalDateTime.parse(statisticDto.getTimestamp(), DATA_TIME_FORMATTER),
                0);
    }

    public static StatisticDto toStatisticDto(Statistic statistic) {
        return new StatisticDto(
                statistic.getId(),
                statistic.getApp(),
                statistic.getUri(),
                statistic.getIp(),
                statistic.getTimestamp().format(DATA_TIME_FORMATTER));
    }

    public static StatisticDtoShort toStatisticDtoShort(Statistic statistic) {
        StatisticDtoShort statisticDtoShort = new StatisticDtoShort();
        statisticDtoShort.setApp(statistic.getApp());
        statisticDtoShort.setUri(statistic.getUri());
        statisticDtoShort.setHits(statistic.getHits());
        return statisticDtoShort;
    }
}
