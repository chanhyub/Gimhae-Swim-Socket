package com.alijas.gimhaeswimsocket.security;

import com.alijas.gimhaeswimsocket.modules.user.enums.UserStatus;
import com.alijas.gimhaeswimsocket.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SecurityService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

        var user = userRepository.findByUsername(id).orElseThrow (
            () -> new UsernameNotFoundException("아이디를 찾을 수 없습니다.")
        );

        if (user.getStatus().equals(UserStatus.DELETED)) {
            throw new UsernameNotFoundException("탈퇴된 회원입니다.");
        }

        return new SecurityUser(user);
    }

}