package ru.explorewithme.dto.compilation;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Обьект Подборки
 *
 * @see ru.explorewithme.model.compilation.Compilation
 */

@Data
@Builder
public class CompilationDto {

    private List<Long> events;
    private Long id;
    private Boolean pinned;
    private String title;
}
