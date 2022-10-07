package ru.explorewithme.compilation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.explorewithme.compilation.dto.CompilationDto;
import ru.explorewithme.compilation.util.CompilationMapper;
import ru.explorewithme.compilation.model.Compilation;
import ru.explorewithme.compilation.repository.CompilationRepository;
import ru.explorewithme.event.model.Event;
import ru.explorewithme.event.repository.EventRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    @Override
    public Compilation addCompilation(CompilationDto compilationDto) {
        Compilation compilation = CompilationMapper.toCompilation(compilationDto, eventRepository);
        log.info("Compilations addCompilation: {}", compilation);
        return compilationRepository.save(compilation);
    }

    @Override
    public Compilation deleteEventFromCompilation(Long compId, Long eventId) {
        Compilation compilations = compilationRepository.findById(compId).get();
        List<Event> events = compilations.getEvents();
        events.remove(eventRepository.findById(eventId).get());
        compilations.setEvents(events);
        log.info("Compilations deleteEventFromCompilation: {}", compilations);
        return compilations;
    }

    @Override
    public Compilation addEventFromCompilation(Long compId, Long eventId) {
        Compilation compilations = compilationRepository.findById(compId).get();
        List<Event> events = compilations.getEvents();
        events.add(eventRepository.findById(eventId).get());
        compilations.setEvents(events);
        log.info("Compilations addEventFromCompilation: {}", compilations);
        return compilations;
    }

    @Override
    public Compilation unpinnedCompilation(Long compId) {
        Compilation compilations = compilationRepository.findById(compId).get();
        compilations.setPinned(false);
        log.info("Compilations unpinnedCompilation: {}", compilations);
        return compilations;
    }

    @Override
    public Compilation pinnedCompilation(Long compId) {
        Compilation compilations = compilationRepository.findById(compId).get();
        compilations.setPinned(true);
        log.info("Compilations pinnedCompilation: {}", compilations);
        return compilations;
    }

    @Override
    public void deleteCompilation(Long compId) {
        log.info("Compilations deleteCompilation: {}", compId);
        compilationRepository.deleteById(compId);
    }

    @Override
    public List<Compilation> getCompilations(Boolean pinned, Integer from, Integer size) {
        List<Compilation> result = compilationRepository.findAllByPinned(pinned, PageRequest.of(from, size)).toList();
        log.info("Compilations getCompilations: {}", result);
        return result;
    }

    @Override
    public Compilation getCompilationById(Long compId) {
        Compilation compilation = compilationRepository.findById(compId).get();
        log.info("Compilations getCompilations: {}", compilation);
        return compilation;
    }
}
