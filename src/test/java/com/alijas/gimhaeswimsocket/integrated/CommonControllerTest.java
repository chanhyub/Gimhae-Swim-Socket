package com.alijas.gimhaeswimsocket.integrated;

import com.alijas.gimhaeswimsocket.common.enums.Gender;
import com.alijas.gimhaeswimsocket.modules.common.dto.LoginRequest;
import com.alijas.gimhaeswimsocket.modules.common.dto.ReLoginRequest;
import com.alijas.gimhaeswimsocket.modules.user.entity.User;
import com.alijas.gimhaeswimsocket.modules.user.enums.ApplyStatus;
import com.alijas.gimhaeswimsocket.modules.user.enums.RoleType;
import com.alijas.gimhaeswimsocket.modules.user.enums.UserStatus;
import com.alijas.gimhaeswimsocket.security.SecurityConstants;
import com.alijas.gimhaeswimsocket.security.SecurityTokenType;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Instant;
import java.time.ZonedDateTime;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("공통 컨트롤러 통합 테스트")
public class CommonControllerTest extends AbstractIntegrated {

    @Test
    @DisplayName("로그인 실패 - 유효성 검사 실패")
    void loginValidFail() throws Exception {
        LoginRequest reqUserLoginDTO = new LoginRequest("", "1234");

        ResultActions perform = this.mockMvc.perform(
                post("/login")
                        .content(objectMapper.writeValueAsString(reqUserLoginDTO))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        perform
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(
                        document("fail-user-login-valid",
                                requestFields(
                                        new FieldDescriptor[] {
                                                fieldWithPath("username").description("사용자 아이디"),
                                                fieldWithPath("password").description("사용자 비밀번호"),
                                        }
                                ),
                                responseFields(getFailResponseField)
                        )
                );
    }

    @Test
    @DisplayName("로그인 실패 - 비밀번호 불일치")
    void loginPasswordFail() throws Exception {
        LoginRequest reqUserLoginDTO = new LoginRequest("Jorge", "asererawerawer");

        ResultActions perform = this.mockMvc.perform(
                post("/login")
                        .content(objectMapper.writeValueAsString(reqUserLoginDTO))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        perform
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(
                        document("fail-user-login-password",
                                requestFields(
                                        new FieldDescriptor[] {
                                                fieldWithPath("username").description("사용자 아이디"),
                                                fieldWithPath("password").description("사용자 비밀번호"),
                                        }
                                ),
                                responseFields(getFailResponseField)
                        )
                );
    }

    @Test
    @DisplayName("로그인 성공")
    void loginSuccess() throws Exception {
        LoginRequest reqUserLoginDTO = new LoginRequest("Jorge", "1234");

        ResultActions perform = this.mockMvc.perform(
                post("/login")
                        .content(objectMapper.writeValueAsString(reqUserLoginDTO))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        perform
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("success-user-login",
                                requestFields(
                                        new FieldDescriptor[] {
                                                fieldWithPath("username").description("사용자 아이디"),
                                                fieldWithPath("password").description("사용자 비밀번호"),
                                        }
                                ),
                                responseFields(
                                        fieldWithPath("fullName").description("사용자 이름"),
                                        fieldWithPath("accessToken").description("액세스 토큰"),
                                        fieldWithPath("refreshToken").description("리프레시 토큰")
                                )
                        )
                );
    }

    @Test
    @DisplayName("리로그인 실패 - 유효성 검사 실패")
    void reLoginValidFail() throws Exception {
        ReLoginRequest reLoginRequest = new ReLoginRequest("");

        ResultActions perform = this.mockMvc.perform(
                post("/re-login")
                        .content(objectMapper.writeValueAsString(reLoginRequest))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        perform
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(
                        document("fail-user-re-login-valid",
                                requestFields(
                                        new FieldDescriptor[] {
                                                fieldWithPath("refreshToken").description("리프레시 토큰")
                                        }
                                ),
                                responseFields(getFailResponseField)
                        )
                );
    }

    @Test
    @DisplayName("리로그인 실패 - 토큰 값이 올바르지 않음")
    void reLoginTokenFail() throws Exception {
        ReLoginRequest reLoginRequest = new ReLoginRequest("easd");
        ResultActions perform = this.mockMvc.perform(
                post("/re-login")
                        .content(objectMapper.writeValueAsString(reLoginRequest))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        perform
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(
                        document("fail-user-re-login-token-not-valid",
                                requestFields(
                                        new FieldDescriptor[] {
                                                fieldWithPath("refreshToken").description("리프레시 토큰")
                                        }
                                ),
                                responseFields(getFailResponseField)
                        )
                );
    }

    @Test
    @DisplayName("리로그인 실패 - 아이디가 존재하지 않음")
    void reLoginIdFail() throws Exception {
        User user = new User(null, "qqqqqqq", "심판1", "$2a$10$zG7hMrPY77D4YGyljxeK2uAE8.ujhC5HJ2Cy/CsDrtCctLzf.EGYW", "1999-10-28", "01083384583", "test1234@test.com", Gender.M, true, UserStatus.ACTIVE, ApplyStatus.APPROVED, RoleType.REFEREE);
        String token = securityTokenProvider.createToken(user.getUsername(), user.getRole().name(), SecurityTokenType.REFRESH_TOKEN);
        ReLoginRequest reLoginRequest = new ReLoginRequest(token);

        ResultActions perform = this.mockMvc.perform(
                post("/re-login")
                        .content(objectMapper.writeValueAsString(reLoginRequest))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        perform
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(
                        document("fail-user-re-login-id-not-exist",
                                requestFields(
                                        new FieldDescriptor[] {
                                                fieldWithPath("refreshToken").description("리프레시 토큰")
                                        }
                                ),
                                responseFields(getFailResponseField)
                        )
                );
    }

    @Test
    @DisplayName("리로그인 성공")
    void reLoginSuccess() throws Exception {
        User user = new User(null, "referee1", "심판1", "$2a$10$zG7hMrPY77D4YGyljxeK2uAE8.ujhC5HJ2Cy/CsDrtCctLzf.EGYW", "1999-10-28", "01083384583", "test1234@test.com", Gender.M, true, UserStatus.ACTIVE, ApplyStatus.APPROVED, RoleType.REFEREE);
        String token = securityTokenProvider.createToken(user.getUsername(), user.getRole().name(), SecurityTokenType.REFRESH_TOKEN);
        ReLoginRequest reLoginRequest = new ReLoginRequest(token);

        ResultActions perform = this.mockMvc.perform(
                post("/re-login")
                        .content(objectMapper.writeValueAsString(reLoginRequest))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        perform
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("success-user-re-login",
                                requestFields(
                                        new FieldDescriptor[] {
                                                fieldWithPath("refreshToken").description("리프레시 토큰")
                                        }
                                ),
                                responseFields(
                                        fieldWithPath("fullName").description("사용자 이름"),
                                        fieldWithPath("accessToken").description("액세스 토큰"),
                                        fieldWithPath("refreshToken").description("리프레시 토큰")
                                )
                        )
                );
    }


}
