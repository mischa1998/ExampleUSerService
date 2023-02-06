package ru.nikitin.service.user;

import ru.nikitin.dto.user.UserDto;

import java.util.List;

public interface UserService {
    UserDto getUserById(Long id);

    UserDto createUser(UserDto userDto);

    UserDto updateUser(Long id, UserDto userDto);

    UserDto addFollowersForUser(Long id, List<Long> followersId);

    void deleteFollowerForUser(Long followerId, Long userId);

    void deleteUser(Long userId);
}
