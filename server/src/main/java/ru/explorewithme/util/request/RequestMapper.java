package ru.explorewithme.util.request;

import ru.explorewithme.model.event.State;
import ru.explorewithme.repository.event.EventRepository;
import ru.explorewithme.dto.request.RequestDto;
import ru.explorewithme.model.request.Request;
import ru.explorewithme.repository.user.UserRepository;

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
