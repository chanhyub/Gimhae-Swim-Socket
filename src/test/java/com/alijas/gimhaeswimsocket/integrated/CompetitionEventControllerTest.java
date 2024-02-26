package com.alijas.gimhaeswimsocket.integrated;

import com.alijas.gimhaeswimsocket.security.SecurityConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("대회 종목 컨트롤러 통합 테스트")
public class CompetitionEventControllerTest extends AbstractIntegrated {

    @Test
    @DisplayName("대회 종목 목록 조회 실패 - 대회 ID가 존재하지 않음")
    void getCompetitionEventListFail() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);

        ResultActions perform = this.mockMvc.perform(
                get("/competition-events")
                        .param("competitionId", "")
                        .param("page", String.valueOf(pageable.getPageNumber()))
                        .param("size", String.valueOf(pageable.getPageSize()))
                        .param("sort", "id,desc")
                        .header(SecurityConstants.TOKEN_HEADER, getRefereeToken().getAccessToken())
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        );

        perform
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(document("fail-competition-event-competition-id-not-exist",
                            getJwtRequestHeadersSnippet(),
                            queryParameters(
                                    parameterWithName("competitionId").description("대회 ID"),
                                    parameterWithName("page").description("페이지 번호"),
                                    parameterWithName("size").description("한 페이지에 보여줄 개수"),
                                    parameterWithName("sort").description("정렬 방식")
                            ),
                            responseFields(getFailResponseField)
                        )
                );
    }

    @Test
    @DisplayName("대회 종목 목록 조회 실패 - 대회가 존재하지 않음")
    void getCompetitionEventListFail2() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);

        ResultActions perform = this.mockMvc.perform(
                get("/competition-events")
                        .param("competitionId", "0")
                        .param("page", String.valueOf(pageable.getPageNumber()))
                        .param("size", String.valueOf(pageable.getPageSize()))
                        .param("sort", "id,desc")
                        .header(SecurityConstants.TOKEN_HEADER, getRefereeToken().getAccessToken())
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        );

        perform
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(document("fail-competition-event-competition-not-exist",
                            getJwtRequestHeadersSnippet(),
                            queryParameters(
                                    parameterWithName("competitionId").description("대회 ID"),
                                    parameterWithName("page").description("페이지 번호"),
                                    parameterWithName("size").description("한 페이지에 보여줄 개수"),
                                    parameterWithName("sort").description("정렬 방식")
                            ),
                            responseFields(getFailResponseField)
                        )
                );
    }

    @Test
    @DisplayName("대회 종목 목록 조회 성공")
    void getCompetitionEventList() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);

        ResultActions perform = this.mockMvc.perform(
                get("/competition-events")
                        .param("competitionId", "1")
                        .param("page", String.valueOf(pageable.getPageNumber()))
                        .param("size", String.valueOf(pageable.getPageSize()))
                        .param("sort", "id,desc")
                        .header(SecurityConstants.TOKEN_HEADER, getRefereeToken().getAccessToken())
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        );

        perform
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("success-competition-event-list",
                            getJwtRequestHeadersSnippet(),
                            queryParameters(
                                    parameterWithName("competitionId").description("대회 ID"),
                                    parameterWithName("page").description("페이지 번호"),
                                    parameterWithName("size").description("한 페이지에 보여줄 개수"),
                                    parameterWithName("sort").description("정렬 방식")
                            ),
                            responseFields(
                                    getCompetitionEventResponseField()
                            )
                        )
                );
    }

    private FieldDescriptor[] getCompetitionEventResponseField() {
        return new FieldDescriptor[]{
                subsectionWithPath("content").description("내용 배열"),
                fieldWithPath("content[].id").description("종목 고유번호"),
                fieldWithPath("content[].eventType").description("종목 타입"),
                fieldWithPath("content[].gender").description("종목 성별"),
                fieldWithPath("content[].department").description("종목 부"),
                fieldWithPath("content[].event").description("종목"),
                fieldWithPath("content[].meter").description("종목 미터"),
                subsectionWithPath("pageable").description("page 종합 정보"),
                fieldWithPath("pageable.pageNumber").description("페이지 번호"),
                fieldWithPath("pageable.pageSize").description("페이지 크기"),
                fieldWithPath("pageable.sort").description("정렬 정보"),
                fieldWithPath("pageable.sort.empty").description("정렬 정보"),
                fieldWithPath("pageable.sort.sorted").description("정렬 정보"),
                fieldWithPath("pageable.sort.unsorted").description("정렬 정보"),
                fieldWithPath("pageable.offset").description("offset"),
                fieldWithPath("pageable.paged").description("paged"),
                fieldWithPath("pageable.unpaged").description("unpaged"),
                fieldWithPath("last").description("마지막 페이지 여부"),
                fieldWithPath("totalElements").description("전체 요소 수"),
                fieldWithPath("totalPages").description("전체 페이지 수"),
                fieldWithPath("size").description("페이지 크기"),
                fieldWithPath("number").description("페이지 번호"),
                fieldWithPath("sort.empty").description("empty"),
                fieldWithPath("sort.unsorted").description("unsorted"),
                fieldWithPath("sort.sorted").description("sorted"),
                fieldWithPath("first").description("첫번째 페이지 여부"),
                fieldWithPath("numberOfElements").description("현재 페이지의 요소 수"),
                fieldWithPath("empty").description("빈 페이지 여부"),
        };
    }

}
