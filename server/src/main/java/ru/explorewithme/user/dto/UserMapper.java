package ru.explorewithme.user.dto;

import org.springframework.stereotype.Component;
import ru.explorewithme.user.model.User;

@Component
public class UserMapper {

    public static UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    public static User toUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        return user;
    }

    public static UserShortDto toUserShortDto(User u){
        return new UserShortDto(u.getId(), u.getName());
    }
}
