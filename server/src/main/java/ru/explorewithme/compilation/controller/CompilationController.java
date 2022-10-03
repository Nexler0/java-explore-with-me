package ru.explorewithme.compilation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.explorewithme.compilation.model.Compilation;
import ru.explorewithme.compilation.service.CompilationService;

import java.util.List;

@RestController
@RequestMapping("/compilations")
@RequiredArgsConstructor
public class CompilationController {

    private final CompilationService compilationService;

    @GetMapping
    public List<Compilation> getCompilations(@RequestParam(name = "pinned", defaultValue = "true", required = false)
                                                     Boolean pinned,
                                             @RequestParam(name = "from", defaultValue = "0", required = false)
                                                     Integer from,
                                             @RequestParam(name = "size", defaultValue = "10", required = false)
                                                     Integer size) {
        return compilationService.getCompilations(pinned, from, size);
    }

    @GetMapping("/{compId}")
    public Compilation getCompilationById(@PathVariable Long compId) {
        return compilationService.getCompilationById(compId);
    }
}
