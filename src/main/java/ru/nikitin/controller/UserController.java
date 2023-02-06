package ru.nikitin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nikitin.dto.user.UserDto;
import ru.nikitin.service.user.UserService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(path = {"/","/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto updateUser(@RequestBody UserDto userDto, @PathVariable(name = "id", required = false) Long id) {
        if (id == null) {
            return userService.createUser(userDto);
        } else {
            return userService.updateUser(id, userDto);
        }
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto getUserById(@PathVariable(name = "id") Long id) {
        return userService.getUserById(id);
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteUser(@PathVariable(name = "id") Long id) {
        userService.deleteUser(id);
    }

    @PostMapping(path = "/follower/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto addFollowersForUser(@RequestBody List<Long> followersIds, @PathVariable(name = "id") Long id) {
        return userService.addFollowersForUser(id, followersIds);
    }

    @DeleteMapping(path = "/follower/{userId}/{followerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteFollowerForUser(@PathVariable(name = "userId") Long userId,
                                         @PathVariable(name = "followerId") Long followerId) {
        userService.deleteFollowerForUser(followerId, userId);
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity handleExceptionForUserController(EntityNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }
}
