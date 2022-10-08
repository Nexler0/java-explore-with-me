package ru.explorewithme.service.compilation;

import ru.explorewithme.dto.compilation.CompilationDto;
import ru.explorewithme.model.compilation.Compilation;

import java.util.List;

/**
 * Сервис для работы с Подборками
 *
 * @see Compilation
 */

public interface CompilationService {
    /**
     * Добавление подборки
     *
     * @param compilationDto Обьект Подборки
     * @see CompilationDto
     */
    Compilation addCompilation(CompilationDto compilationDto);

    /**
     * Удаление события из подборки по идентификатору
     *
     * @param compId  идентификатор подборки
     * @param eventId идентификатор события
     */
    Compilation deleteEventFromCompilation(Long compId, Long eventId);

    /**
     * Добавление события в подборку
     *
     * @param compId  идентификатор подборки
     * @param eventId идентификатор события
     */
    Compilation addEventFromCompilation(Long compId, Long eventId);

    /**
     * Закрепление поборки на главном экране
     *
     * @param compId идентификатор подборки
     */
    Compilation unpinnedCompilation(Long compId);

    /**
     * Открепление родборки от главного экрана
     *
     * @param compId идентификатор подборки
     */
    Compilation pinnedCompilation(Long compId);

    /**
     * Удаление подборки по  идентификатору
     *
     * @param compId идентификатор подборки
     */
    void deleteCompilation(Long compId);

    /**
     * Получение списка подборок по параметрам
     *
     * @param pinned статус закрепления
     * @param from   номер страницы для пагинации
     * @param size   количество записей на странице
     */
    List<Compilation> getCompilations(Boolean pinned, Integer from, Integer size);

    /**
     * Получуние подборки по идентификатору
     *
     * @param compId идентификатор подборки
     */
    Compilation getCompilationById(Long compId);
}
