package ru.explorewithme.dto.statistic;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Обьект Статистики для сохранения на сервер
 */

@Data
@AllArgsConstructor
public class StatisticDto {

    private Long id;
    private String app;
    private String uri;
    private String ip;
    private String timestamp;

}

