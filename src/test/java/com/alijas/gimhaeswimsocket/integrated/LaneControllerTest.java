package com.alijas.gimhaeswimsocket.integrated;

import com.alijas.gimhaeswimsocket.security.SecurityConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("레인 컨트롤러 통합 테스트")
public class LaneControllerTest extends AbstractIntegrated {

    @Test
    @DisplayName("레인 목록 조회 실패 - 조 ID가 존재하지 않음")
    void getLaneListFailBySectionId() throws Exception {

        ResultActions perform = this.mockMvc.perform(
                get("/lanes")
                        .param("sectionId", "")
                        .header(SecurityConstants.TOKEN_HEADER, getRefereeToken().getAccessToken())
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        );

        perform
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(document("fail-lane-section-id-not-exist",
                                getJwtRequestHeadersSnippet(),
                                queryParameters(
                                        parameterWithName("sectionId").description("조 ID")
                                ),
                                responseFields(getFailResponseField)
                        )
                );
    }

    @Test
    @DisplayName("레인 목록 조회 실패 - 조가 존재하지 않음")
    void getLaneListFailBySectionNotExist() throws Exception {

        ResultActions perform = this.mockMvc.perform(
                get("/lanes")
                        .param("sectionId", "0")
                        .header(SecurityConstants.TOKEN_HEADER, getRefereeToken().getAccessToken())
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        );

        perform
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(document("fail-lane-section-not-exist",
                                getJwtRequestHeadersSnippet(),
                                queryParameters(
                                        parameterWithName("sectionId").description("조 ID")
                                ),
                                responseFields(getFailResponseField)
                        )
                );
    }

    @Test
    @DisplayName("레인 목록 조회 실패 - 해당 레인 담당 심판이 아님")
    void getLaneListFailByNotReferee() throws Exception {

        ResultActions perform = this.mockMvc.perform(
                get("/lanes")
                        .param("sectionId", "1")
                        .header(SecurityConstants.TOKEN_HEADER, getReferee4Token().getAccessToken())
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        );

        perform
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(document("fail-lane-not-referee",
                                getJwtRequestHeadersSnippet(),
                                queryParameters(
                                        parameterWithName("sectionId").description("조 ID")
                                ),
                                responseFields(getFailResponseField)
                        )
                );
    }

    @Test
    @DisplayName("레인 목록 조회 성공")
    void getLaneList() throws Exception {

        ResultActions perform = this.mockMvc.perform(
                get("/lanes")
                        .param("sectionId", "1")
                        .header(SecurityConstants.TOKEN_HEADER, getRefereeToken().getAccessToken())
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        );

        perform
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("success-lane-list",
                                getJwtRequestHeadersSnippet(),
                                queryParameters(
                                        parameterWithName("sectionId").description("조 ID")
                                ),
                                responseFields(getLaneListResponseField())
                        )
                );
    }

    private FieldDescriptor[] getLaneListResponseField() {
        return new FieldDescriptor[]{
                fieldWithPath("[].id").description("레인 고유번호"),
                fieldWithPath("[].laneNumber").description("레인 차수"),
                fieldWithPath("[].user.id").description("경기인 고유번호"),
                fieldWithPath("[].user.fullName").description("경기인 이름"),
                fieldWithPath("[].referee.id").description("심판 고유번호"),
                fieldWithPath("[].referee.refereeName").description("심판 이름")
        };
    }


}
