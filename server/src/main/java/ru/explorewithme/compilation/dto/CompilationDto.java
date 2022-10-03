package ru.explorewithme.compilation.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CompilationDto {

    private List<Long> events;
    private Long id;
    private Boolean pinned;
    private String title;
}
