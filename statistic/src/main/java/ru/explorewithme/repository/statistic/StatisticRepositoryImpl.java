package ru.explorewithme.repository.statistic;

import org.springframework.context.annotation.Lazy;

public class StatisticRepositoryImpl {

    private final StatisticRepository statisticRepository;

    public StatisticRepositoryImpl(@Lazy StatisticRepository statisticRepository) {
        this.statisticRepository = statisticRepository;
    }
}