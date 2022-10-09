package ru.explorewithme.util.compilation;

import ru.explorewithme.dto.compilation.CompilationDto;
import ru.explorewithme.model.compilation.Compilation;
import ru.explorewithme.repository.event.EventRepository;

import java.util.ArrayList;

/**
 * Преобразование Дто в обьект Подборки
 *
 * @see Compilation
 * @see CompilationDto
 */

public class CompilationMapper {
    /**
     * Преобразование  Дто в обьект Компиляции
     *
     * @param compilationDto  Обьект Дто Компиляции
     * @param eventRepository Инжекция репозитория
     */
    public static Compilation toCompilation(CompilationDto compilationDto, EventRepository eventRepository) {
        Compilation compilation = new Compilation();
        if (compilationDto.getEvents() != null && !compilationDto.getEvents().isEmpty()) {
            compilation.setEvents(eventRepository.findAllById(compilationDto.getEvents()));
        } else {
            compilation.setEvents(new ArrayList<>());
        }
        compilation.setPinned(compilationDto.getPinned());
        compilation.setTitle(compilationDto.getTitle());
        return compilation;
    }
}
