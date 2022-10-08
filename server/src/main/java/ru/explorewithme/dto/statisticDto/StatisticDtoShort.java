package ru.explorewithme.dto.statisticDto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Обьект Статистики для получения с сервера
 */

@Data
@RequiredArgsConstructor
public class StatisticDtoShort {

    private String app;
    private String uri;
    private int hits;

}
