package ru.nikitin.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nikitin.entity.user.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
