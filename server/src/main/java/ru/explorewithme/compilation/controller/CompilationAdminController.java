package ru.explorewithme.compilation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.explorewithme.compilation.dto.CompilationDto;
import ru.explorewithme.compilation.model.Compilation;
import ru.explorewithme.compilation.service.CompilationService;

@RestController
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
public class CompilationAdminController {

    private final CompilationService compilationService;

    @PostMapping
    public Compilation addCompilation(@RequestBody CompilationDto compilationDto) {
        return compilationService.addCompilation(compilationDto);
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    public Compilation deleteEventFromCompilation(@PathVariable Long compId,
                                                  @PathVariable Long eventId) {
        return compilationService.deleteEventFromCompilation(compId, eventId);
    }

    @PatchMapping("/{compId}/events/{eventId}")
    public Compilation addEventFromCompilation(@PathVariable Long compId,
                                               @PathVariable Long eventId) {
        return compilationService.addEventFromCompilation(compId, eventId);
    }

    @DeleteMapping("/{compId}/pin")
    public Compilation unpinnedCompilation(@PathVariable Long compId) {
        return compilationService.unpinnedCompilation(compId);
    }

    @PatchMapping("/{compId}/pin")
    public Compilation pinnedCompilation(@PathVariable Long compId) {
        return compilationService.pinnedCompilation(compId);
    }

    @DeleteMapping("/{compId}")
    public void deleteCompilation(@PathVariable Long compId) {
        compilationService.deleteCompilation(compId);
    }
}
