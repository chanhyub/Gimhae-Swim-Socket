package com.alijas.gimhaeswimsocket.integrated;

import com.alijas.gimhaeswimsocket.modules.common.dto.LoginRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.ResultActions;

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
                                        fieldWithPath("accessToken").description("액세스 토큰"),
                                        fieldWithPath("refreshToken").description("리프레시 토큰(구현 안 됨)")
                                )
                        )
                );
    }
}
