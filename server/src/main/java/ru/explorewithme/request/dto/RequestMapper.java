package ru.explorewithme.request.dto;

import ru.explorewithme.event.model.State;
import ru.explorewithme.event.repository.EventRepository;
import ru.explorewithme.request.model.Request;
import ru.explorewithme.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RequestMapper {

    private static final DateTimeFormatter DATA_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static Request toRequest(RequestDto requestDto,
                                    EventRepository eventRepository,
                                    UserRepository userRepository) {
        return new Request(requestDto.getId(),
                LocalDateTime.parse(requestDto.getCreated(), DATA_TIME_FORMATTER),
                eventRepository.findById(requestDto.getEvent()).get(),
                userRepository.findById(requestDto.getRequester()).get(),
                State.valueOf(requestDto.getStatus()));
    }

    public static RequestDto toRequestDto(Request request) {
        return new RequestDto(request.getId(),
                request.getCreated().format(DATA_TIME_FORMATTER),
                request.getEvent().getId(),
                request.getRequester().getId(),
                request.getStatus().toString());
    }
}
