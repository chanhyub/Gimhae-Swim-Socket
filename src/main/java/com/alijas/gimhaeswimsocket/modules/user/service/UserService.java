package com.alijas.gimhaeswimsocket.modules.user.service;

import com.alijas.gimhaeswimsocket.modules.user.entity.User;
import com.alijas.gimhaeswimsocket.modules.user.enums.UserStatus;
import com.alijas.gimhaeswimsocket.modules.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUser(Long userId) {
        return userRepository.findById(userId);
    }
}
