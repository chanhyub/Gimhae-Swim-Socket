package com.alijas.gimhaeswimsocket.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        setResponse(request, response, accessDeniedException);
    }

    private static void setResponse(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().println(
                "{\n" +
                        "    \"type\": \"about:blank\",\n" +
                        "    \"title\": \""+ HttpStatus.FORBIDDEN.getReasonPhrase() +"\",\n" +
                        "    \"status\": "+ response.getStatus() +",\n" +
                        "    \"detail\": \"" + "권한이 없습니다." + "\",\n" +
                        "    \"instance\": \"" + request.getServletPath() + "\"\n"
                        + "}"
        );
    }
}