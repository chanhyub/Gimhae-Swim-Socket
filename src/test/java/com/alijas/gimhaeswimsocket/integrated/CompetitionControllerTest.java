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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("대회 컨트롤러 통합 테스트")
public class CompetitionControllerTest extends AbstractIntegrated {



    @Test
    @DisplayName("대회 리스트 조회")
    void getCompetitionList() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        ResultActions perform = this.mockMvc.perform(
                get("/competitions")
                        .param("page", String.valueOf(pageable.getPageNumber()))
                        .param("size", String.valueOf(pageable.getPageSize()))
                        .param("sort", "id,desc")
                        .header(SecurityConstants.TOKEN_HEADER, getRefereeToken().getAccessToken())
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        );

        perform
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("success-competition-list",
                                getJwtRequestHeadersSnippet(),
                                queryParameters(getPageParameterDescriptors),
                                responseFields(getCompetitionResponseField())
                        )
                );
        ;
    }




    private FieldDescriptor[] getCompetitionResponseField() {
        return new FieldDescriptor[]{
                subsectionWithPath("content").description("내용 배열"),
                fieldWithPath("content[].id").description("대회 고유번호"),
                fieldWithPath("content[].competitionName").description("대회명"),
                fieldWithPath("content[].competitionDate").description("대회 날짜"),
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
