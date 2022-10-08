package ru.explorewithme.controller.statistic;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.explorewithme.dto.statistic.StatisticDto;
import ru.explorewithme.dto.statistic.StatisticDtoShort;
import ru.explorewithme.service.statistic.StatisticService;

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
                                                 @RequestParam(name = "unique", required = false,
                                                         defaultValue = "false") Boolean unique) {
        return statisticService.getStatsByUri(start, end, uris, unique);
    }
}
