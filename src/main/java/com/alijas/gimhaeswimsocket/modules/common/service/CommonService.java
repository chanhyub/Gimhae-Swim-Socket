package com.alijas.gimhaeswimsocket.modules.common.service;

import com.alijas.gimhaeswimsocket.modules.common.dto.ResUserLoginDTO;
import com.alijas.gimhaeswimsocket.modules.user.entity.User;
import com.alijas.gimhaeswimsocket.modules.user.enums.UserStatus;
import com.alijas.gimhaeswimsocket.modules.user.repository.UserRepository;
import com.alijas.gimhaeswimsocket.security.SecurityTokenProvider;
import com.alijas.gimhaeswimsocket.security.SecurityTokenType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommonService {
    private final UserRepository userRepository;
    private final SecurityTokenProvider tokenProvider;
    private final BCryptPasswordEncoder passwordEncoder;
    public CommonService(UserRepository userRepository, SecurityTokenProvider tokenProvider, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findUser(String username) {
        return userRepository.findByUsernameAndStatusNot(username, UserStatus.DELETED);
    }

    public ResUserLoginDTO getToken(User user) {
        ResUserLoginDTO resUserLoginDTO = new ResUserLoginDTO();
        resUserLoginDTO.setAccessToken(tokenProvider.createToken(user.getUsername(), user.getRole().name(), SecurityTokenType.ACCESS_TOKEN));
        resUserLoginDTO.setRefreshToken(tokenProvider.createToken(user.getUsername(), user.getRole().name(), SecurityTokenType.REFRESH_TOKEN));
        return resUserLoginDTO;
    }

    public Boolean passwordCheck(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
