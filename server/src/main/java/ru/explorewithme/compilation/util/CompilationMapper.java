package ru.explorewithme.compilation.util;

import ru.explorewithme.compilation.dto.CompilationDto;
import ru.explorewithme.compilation.model.Compilation;
import ru.explorewithme.event.repository.EventRepository;

import java.util.ArrayList;

public class CompilationMapper {

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
