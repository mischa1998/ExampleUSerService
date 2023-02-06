package ru.nikitin.mapper.user;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nikitin.dto.user.UserDto;
import ru.nikitin.entity.user.UserEntity;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Mapping(target = "followers", ignore = true)
    public abstract UserEntity toEntity(UserDto userDto);

    @Mapping(target = "followers", ignore = true)
    public abstract UserEntity updateEntity(UserDto userDto, @MappingTarget UserEntity userEntity);

    @Mapping(target = "id", source = "userId")
    @Mapping(target = "followers", ignore = true)
    public abstract UserDto toDto(UserEntity userEntity);

    @AfterMapping
    public void addFollowers(@MappingTarget UserDto userDto, UserEntity userEntity) {
        if (userEntity.getFollowers() != null) {
            userDto.setFollowers(userEntity.getFollowers().stream().map(e -> e.getUserId()).collect(Collectors.toList()));
        }
    }
}
