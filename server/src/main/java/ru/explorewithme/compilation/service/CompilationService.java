package ru.explorewithme.compilation.service;

import ru.explorewithme.category.dto.CategoryDto;
import ru.explorewithme.compilation.dto.CompilationDto;
import ru.explorewithme.compilation.model.Compilation;

import java.util.List;

public interface CompilationService {
    Compilation addCompilation(CompilationDto compilationDto);

    Compilation deleteEventFromCompilation(Long compId, Long eventId);

    Compilation addEventFromCompilation(Long compId, Long eventId);

    Compilation unpinnedCompilation(Long compId);

    Compilation pinnedCompilation(Long compId);

    void deleteCompilation(Long compId);

    List<Compilation> getCompilations(Boolean pinned, Integer from, Integer size);

    Compilation getCompilationById(Long compId);
}
