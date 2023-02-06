package ru.nikitin.service.user.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nikitin.dto.user.UserDto;
import ru.nikitin.entity.user.UserEntity;
import ru.nikitin.mapper.user.UserMapper;
import ru.nikitin.repository.user.UserRepository;
import ru.nikitin.service.user.UserService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    @Override
    public UserDto getUserById(Long id) {
        UserEntity userEntity = getUserEntity(id);
        if (userEntity.isDeleted()) {
            throw new EntityNotFoundException("User is deleted");
        }
        return userMapper.toDto(userEntity);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        UserEntity userEntity = userMapper.toEntity(userDto);
        userEntity = userRepository.save(userEntity);
        return userMapper.toDto(userEntity);
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        UserEntity userEntity = getUserEntity(id);
        UserEntity updatedUserEntity = userMapper.updateEntity(userDto, userEntity);
        updatedUserEntity = userRepository.save(updatedUserEntity);
        return userMapper.toDto(updatedUserEntity);
    }

    @Override
    public UserDto addFollowersForUser(Long id, List<Long> followersId) {
        UserEntity userEntity = getUserEntity(id);
        List<UserEntity> newFollowers = getUserEntitiesByIds(followersId);
        for (UserEntity newFollower: newFollowers) {
            newFollower.setPublisher(userEntity);
        }
        userEntity.getFollowers().addAll(newFollowers);
        userEntity = userRepository.save(userEntity);
        return userMapper.toDto(userEntity);
    }

    @Override
    public void deleteFollowerForUser(Long followerId, Long userId) {
        UserEntity userEntity = getUserEntity(userId);
        UserEntity followerToDelete = userEntity.getFollowers().stream().filter(e -> e.getUserId().equals(followerId))
                .findFirst().orElseThrow(() -> new EntityNotFoundException(String.format("User with %d not found", followerId)));
        followerToDelete.setPublisher(null);
        userRepository.save(userEntity);
    }

    @Override
    public void deleteUser(Long userId) {
        UserEntity userEntity = getUserEntity(userId);
        userEntity.setDeleted(true);
        userRepository.save(userEntity);
    }

    private UserEntity getUserEntity(Long id) {
        return userRepository.findById(id).orElseThrow(() ->  new EntityNotFoundException(
                String.format("User with %d not found", id)));
    }

    List<UserEntity> getUserEntitiesByIds(List<Long> ids) {
        return userRepository.findAllById(ids);
    }
}
