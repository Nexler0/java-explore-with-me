package ru.explorewithme.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.explorewithme.model.event.State;
import ru.explorewithme.repository.event.EventRepository;
import ru.explorewithme.exception.ValidationException;
import ru.explorewithme.dto.request.RequestDto;
import ru.explorewithme.util.request.RequestMapper;
import ru.explorewithme.model.request.Request;
import ru.explorewithme.repository.request.RequestRepository;
import ru.explorewithme.dto.user.UserDto;
import ru.explorewithme.util.user.UserMapper;
import ru.explorewithme.model.user.User;
import ru.explorewithme.repository.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;

    @Override
    public List<UserDto> getAllUsers(List<Long> userIds, Integer from, Integer size) {
        List<User> result = userRepository.findAll();
        result = result
                .stream()
                .filter(user -> userIds.contains(user.getId()))
                .collect(Collectors.toList());
        log.info("Users getAllUsers: {}", result);
        return result.stream().map(UserMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public UserDto addUser(UserDto userDto) {
        User result = userRepository.save(UserMapper.toUser(userDto));
        log.info("Users addUser: {}", result);
        return UserMapper.toDto(result);
    }

    @Override
    public void deleteUser(Long id) {
        log.info("Users deleteUser: {}", id);
        userRepository.deleteById(id);
    }

    @Override
    public List<RequestDto> getAllUserRequests(Long userId) {
        log.info("Users getAllUserRequests: {}", userId);
        return requestRepository.findAllByRequesterId(userId).stream()
                .map(RequestMapper::toRequestDto).collect(Collectors.toList());
    }

    @Override
    public RequestDto postUserRequest(Long userId, Long eventId) {
        log.info("Users postUserRequest: userId: {}, \n eventId: {}", userId, eventId);
        Request request = new Request();
        request.setRequester(userRepository.findById(userId).get());
        request.setCreated(LocalDateTime.now().withNano(0));
        request.setEvent(eventRepository.findById(eventId).get());
        if (!request.getEvent().getInitiator().getId().equals(userId)
                && request.getEvent().getState().equals(State.PUBLISHED)
                && (request.getEvent().getParticipantLimit() == 0 || request.getEvent().getParticipantLimit() > 0)) {
            if (requestRepository.findByRequesterId(userId) != null &&
                    requestRepository.findByRequesterId(userId).equals(request)) {
                throw new ValidationException("ПОвторное создание запроса");
            }
            if (request.getEvent().getRequestModeration()) {
                request.getEvent().setParticipantLimit(request.getEvent().getParticipantLimit() - 1);
            } else {
                request.setStatus(State.CONFIRMED);
            }
            request = requestRepository.save(request);
            log.info("Users postUserRequest: {}", request);
            return RequestMapper.toRequestDto(request);
        } else {
            throw new ValidationException("Ошибка валидации запроса на участие");
        }
    }

    @Override
    public RequestDto cancelRequestByUser(Long userId, Long requestId) {
        Request request = requestRepository.findById(requestId).get();
        log.info("Users cancelRequestByUser: userId: {}, \n requestId: {}", userId, requestId);
        if (request.getRequester().getId().equals(userId)) {
            request.setStatus(State.CANCELED);
            log.info("Users cancelRequestByUser: {}", request);
            return RequestMapper.toRequestDto(request);
        } else {
            throw new ValidationException("Пользователь не является создателем запроса");
        }
    }
}
