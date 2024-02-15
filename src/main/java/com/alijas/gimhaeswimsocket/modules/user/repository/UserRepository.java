package com.alijas.gimhaeswimsocket.modules.user.repository;

import com.alijas.gimhaeswimsocket.modules.user.entity.User;
import com.alijas.gimhaeswimsocket.modules.user.enums.ApplyStatus;
import com.alijas.gimhaeswimsocket.modules.user.enums.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Page<User> findAllByApplyStatusAndStatus(Pageable pageable, ApplyStatus status, UserStatus userStatus);

    Optional<User> findByUsernameAndStatusNot(String username, UserStatus userStatus);
}
