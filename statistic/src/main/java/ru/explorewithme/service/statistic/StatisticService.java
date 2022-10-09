package ru.explorewithme.service.statistic;


import ru.explorewithme.dto.statistic.StatisticDto;
import ru.explorewithme.dto.statistic.StatisticDtoShort;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Сервис для работы со Статистикой
 *
 * @see ru.explorewithme.model.statistic.Statistic
 */

public interface StatisticService {

    /**
     * Добавление статистики
     *
     * @param statisticDto Дто статистики
     */
    StatisticDto postStatistic(StatisticDto statisticDto);

    /**
     * Получение списка статистики по параметрам
     *
     * @param start  период начала поиска
     * @param end    период окончания поиска
     * @param uris   списо URI для поиска
     * @param unique флаг уникальности IP адресов
     */
    List<StatisticDtoShort> getStatsByUri(LocalDateTime start,
                                          LocalDateTime end,
                                          List<String> uris,
                                          Boolean unique);
}
