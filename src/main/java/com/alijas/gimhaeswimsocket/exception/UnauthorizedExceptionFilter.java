package com.alijas.gimhaeswimsocket.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;

public class UnauthorizedExceptionFilter extends OncePerRequestFilter {

    /*
    인증 오류가 아닌, JWT 관련 오류는 이 필터에서 따로 잡아낸다.
    이를 통해 JWT 만료 에러와 인증 에러를 따로 잡아낼 수 있다.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            chain.doFilter(request, response); // JwtAuthenticationFilter로 이동
        } catch (UnauthorizedException ex) {
            // JwtAuthenticationFilter에서 예외 발생하면 바로 setErrorResponse 호출
            setErrorResponse(request, response, ex);
        }
    }

    public void setErrorResponse(HttpServletRequest request, HttpServletResponse response, Throwable ex) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().println(
                "{\n" +
                        "    \"type\": \"about:blank\",\n" +
                        "    \"title\": \""+ HttpStatus.UNAUTHORIZED.getReasonPhrase() +"\",\n" +
                        "    \"status\": "+ response.getStatus() +",\n" +
                        "    \"detail\": \"" + "JWT 토큰 만료" + "\",\n" +
                        "    \"instance\": \"" + request.getServletPath() + "\"\n"
                        + "}"
        );
    }
}