package ru.explorewithme.controller.admin.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.explorewithme.dto.compilation.CompilationDto;
import ru.explorewithme.model.compilation.Compilation;
import ru.explorewithme.service.compilation.CompilationService;

/**
 * API для работы с компиляциями для администратора
 *
 * @see CompilationAdminController
 */

@RestController
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
public class CompilationAdminController {

    private final CompilationService compilationService;

    /**
     * Добавление подборки событий
     *
     * @param compilationDto обьект компиляции
     */
    @PostMapping
    public Compilation addCompilation(@RequestBody CompilationDto compilationDto) {
        return compilationService.addCompilation(compilationDto);
    }

    /**
     * удаление события события из подбороки
     *
     * @param compId  номер компиляции
     * @param eventId номер события
     */
    @DeleteMapping("/{compId}/events/{eventId}")
    public Compilation deleteEventFromCompilation(@PathVariable Long compId,
                                                  @PathVariable Long eventId) {
        return compilationService.deleteEventFromCompilation(compId, eventId);
    }

    /**
     * Добавление сбытия в подборку
     *
     * @param compId  номер компиляции
     * @param eventId номер события
     */
    @PatchMapping("/{compId}/events/{eventId}")
    public Compilation addEventFromCompilation(@PathVariable Long compId,
                                               @PathVariable Long eventId) {
        return compilationService.addEventFromCompilation(compId, eventId);
    }

    /**
     * Открепление компиляции от главной страницы
     *
     * @param compId номер компиляции
     */
    @DeleteMapping("/{compId}/pin")
    public Compilation unpinnedCompilation(@PathVariable Long compId) {
        return compilationService.unpinnedCompilation(compId);
    }

    /**
     * Закрепление компиляции от главной страницы
     *
     * @param compId номер компиляции
     */
    @PatchMapping("/{compId}/pin")
    public Compilation pinnedCompilation(@PathVariable Long compId) {
        return compilationService.pinnedCompilation(compId);
    }

    /**
     * Удаление компиляции
     *
     * @param compId номер компиляции
     */
    @DeleteMapping("/{compId}")
    public void deleteCompilation(@PathVariable Long compId) {
        compilationService.deleteCompilation(compId);
    }
}
