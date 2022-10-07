package ru.explorewithme.ewm.model.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.explorewithme.ewm.dto.StatisticDto;
import ru.explorewithme.ewm.dto.StatisticDtoShort;
import ru.explorewithme.ewm.service.StatisticService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StatisticController {

    private final StatisticService statisticService;

    @PostMapping("/hit")
    public StatisticDto postStatistic(@RequestBody StatisticDto statistic) {
        return statisticService.postStatistic(statistic);
    }

    @GetMapping("/stats")
    public List<StatisticDtoShort> getStatsByUri(@RequestParam(name = "start", required = false)
                                            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                                                 @RequestParam(name = "end", required = false)
                                            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                                                 @RequestParam(name = "uris") List<String> uris,
                                                 @RequestParam(name = "unique") Boolean unique) {
        return statisticService.getStatsByUri(start, end, uris, unique);
    }
}
