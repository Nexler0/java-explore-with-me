package ru.explorewithme.util.statistic;


import ru.explorewithme.dto.statistic.StatisticDto;
import ru.explorewithme.dto.statistic.StatisticDtoShort;
import ru.explorewithme.model.statistic.Statistic;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Преобразование обьекта Статистики в Дто и обратно
 *
 * @see Statistic
 * @see StatisticDto
 * @see StatisticDtoShort
 */

public class StatisticMapper {

    private static final DateTimeFormatter DATA_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Преобразование Дто В обьект Статистики
     *
     * @param statisticDto Дто статистики
     */
    public static Statistic toStatistic(StatisticDto statisticDto) {
        return new Statistic(
                statisticDto.getId(),
                statisticDto.getApp(),
                statisticDto.getUri(),
                statisticDto.getIp(),
                LocalDateTime.parse(statisticDto.getTimestamp(), DATA_TIME_FORMATTER),
                0);
    }

    /**
     * Преобразование Обьекта статистики в Дто
     *
     * @param statistic обьект Статистики
     */
    public static StatisticDto toStatisticDto(Statistic statistic) {
        return new StatisticDto(
                statistic.getId(),
                statistic.getApp(),
                statistic.getUri(),
                statistic.getIp(),
                statistic.getTimestamp().format(DATA_TIME_FORMATTER));
    }

    /**
     * Преобразование обьекта Ститистики в короткое Дто
     *
     * @param statistic обьект Статистики
     */
    public static StatisticDtoShort toStatisticDtoShort(Statistic statistic) {
        StatisticDtoShort statisticDtoShort = new StatisticDtoShort();
        statisticDtoShort.setApp(statistic.getApp());
        statisticDtoShort.setUri(statistic.getUri());
        statisticDtoShort.setHits(statistic.getHits());
        return statisticDtoShort;
    }
}
