package ru.explorewithme.ewm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatisticDto {

    private Long id;
    private String app;
    private String uri;
    private String ip;
    private String timestamp;

}

