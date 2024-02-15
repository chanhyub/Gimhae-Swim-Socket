package com.alijas.gimhaeswimsocket.modules.common.controller;

import com.alijas.gimhaeswimsocket.modules.common.dto.LoginRequest;
import com.alijas.gimhaeswimsocket.modules.common.dto.ResUserLoginDTO;
import com.alijas.gimhaeswimsocket.modules.common.exception.CustomRestException;
import com.alijas.gimhaeswimsocket.modules.common.service.CommonService;
import com.alijas.gimhaeswimsocket.modules.user.entity.User;
import com.alijas.gimhaeswimsocket.modules.user.service.UserService;
import com.alijas.gimhaeswimsocket.security.SecurityConstants;
import com.alijas.gimhaeswimsocket.security.SecurityTokenProvider;
import com.alijas.gimhaeswimsocket.security.SecurityTokenType;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class CommonController {

    private final CommonService commonService;

    private final SecurityTokenProvider tokenProvider;

    public CommonController(CommonService commonService, SecurityTokenProvider tokenProvider) {
        this.commonService = commonService;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<ResUserLoginDTO> login(
            @Valid @RequestBody LoginRequest loginRequest,
            Errors errors
    ) {
        if (errors.hasErrors()) {
            throw new CustomRestException(errors.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }

        Optional<User> optionalUser = commonService.findUser(loginRequest.username());
        if (optionalUser.isEmpty()) {
            throw new CustomRestException("ID 혹은 Password가 잘못되었습니다.", HttpStatus.BAD_REQUEST);
        }

        User user = optionalUser.get();
        if (!commonService.passwordCheck(loginRequest.password(), user.getPassword())) {
            throw new CustomRestException("ID 혹은 Password가 잘못되었습니다.", HttpStatus.BAD_REQUEST);
        }

        ResUserLoginDTO token = commonService.getToken(user);

        HttpHeaders headers = new HttpHeaders();
        headers.add(SecurityConstants.TOKEN_HEADER, SecurityConstants.TOKEN_PREFIX + token.getAccessToken());
        headers.add("Content-Type", "application/json;charset=utf-8");

        return new ResponseEntity<>(token, headers, HttpStatus.OK);
    }
}
