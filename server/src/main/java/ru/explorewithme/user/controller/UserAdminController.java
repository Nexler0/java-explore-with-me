package ru.explorewithme.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.explorewithme.user.dto.UserDto;
import ru.explorewithme.user.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class UserAdminController {

    private final UserService userService;

    @GetMapping
    public List<UserDto> getAllCategories(@RequestParam(name = "ids", required = false)
                                                  List<Long> userIds,
                                          @RequestParam(name = "size", defaultValue = "10", required = false)
                                                  Integer size,
                                          @RequestParam(name = "from", defaultValue = "0", required = false)
                                                  Integer from) {
        return userService.getAllUsers(userIds, from, size);
    }

    @PostMapping
    public UserDto addUser(@Valid @RequestBody UserDto userDto) {
        return userService.addUser(userDto);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
