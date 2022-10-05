package ru.explorewithme.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.explorewithme.request.dto.RequestDto;
import ru.explorewithme.user.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}/requests")
    public List<RequestDto> getAllUserRequests(@PathVariable Long userId) {
        return userService.getAllUserRequests(userId);
    }

    @PostMapping("/{userId}/requests")
    public RequestDto postUserRequest(@PathVariable Long userId,
                                      @RequestParam(name = "eventId") Long eventId) {
        return userService.postUserRequest(userId, eventId);
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public RequestDto cancelRequestByUser(@PathVariable Long userId, @PathVariable Long requestId) {
        return userService.cancelRequestByUser(userId, requestId);
    }
}
