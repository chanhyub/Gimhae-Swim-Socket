package com.alijas.gimhaeswimsocket.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        setResponse(request, response, authException);
    }

    private static void setResponse(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().println(
                "{\n" +
                        "    \"type\": \"about:blank\",\n" +
                        "    \"title\": \""+ HttpStatus.UNAUTHORIZED.getReasonPhrase() +"\",\n" +
                        "    \"status\": "+ response.getStatus() +",\n" +
                        "    \"detail\": \"" + "인증되지 않았습니다." + "\",\n" +
                        "    \"instance\": \"" + request.getServletPath() + "\"\n"
                        + "}"
        );
    }
}