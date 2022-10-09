package ru.explorewithme.controller.client.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.explorewithme.model.compilation.Compilation;
import ru.explorewithme.service.compilation.CompilationService;

import java.util.List;

/**
 * Public API для пользователей, работа с подборками
 *
 * @see CompilationController
 */

@RestController
@RequestMapping("/compilations")
@RequiredArgsConstructor
public class CompilationController {

    private final CompilationService compilationService;

    /**
     * Получение закрепленного списка подборок
     *
     * @param pinned статус закрепления страницы
     * @param from   номер страницы для пагинации
     * @param size   количество записей на странице
     */
    @GetMapping
    public List<Compilation> getCompilations(@RequestParam(name = "pinned", defaultValue = "true", required = false)
                                                     Boolean pinned,
                                             @RequestParam(name = "from", defaultValue = "0", required = false)
                                                     Integer from,
                                             @RequestParam(name = "size", defaultValue = "10", required = false)
                                                     Integer size) {
        return compilationService.getCompilations(pinned, from, size);
    }

    /**
     * Получение подборки по идентификатору
     *
     * @param compId идентификатор подборки
     */
    @GetMapping("/{compId}")
    public Compilation getCompilationById(@PathVariable Long compId) {
        return compilationService.getCompilationById(compId);
    }
}
