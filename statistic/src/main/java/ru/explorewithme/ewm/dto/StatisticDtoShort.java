package ru.explorewithme.ewm.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class StatisticDtoShort {

    private String app;
    private String uri;
    private int hits;

}
