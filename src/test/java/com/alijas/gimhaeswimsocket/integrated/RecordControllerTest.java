package com.alijas.gimhaeswimsocket.integrated;

import com.alijas.gimhaeswimsocket.modules.record.dto.RecordTotalSaveRequest;
import com.alijas.gimhaeswimsocket.modules.record.request.RecordSaveRequest;
import com.alijas.gimhaeswimsocket.security.SecurityConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("기록 컨트롤러 통합 테스트")
public class RecordControllerTest extends AbstractIntegrated {

    @Test
    @DisplayName("기록 등록 실패 - 레인 번호가 없음")
    void saveRecordFailByLaneIdNotExist() throws Exception {
        RecordSaveRequest recordSaveRequest = new RecordSaveRequest(null, "1:00");

        ResultActions perform = this.mockMvc.perform(
                post("/records")
                        .content(objectMapper.writeValueAsString(recordSaveRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(SecurityConstants.TOKEN_HEADER, getRefereeToken().getAccessToken())
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        );

        perform
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(
                        document("fail-record-save-valid1",
                                getJwtRequestHeadersSnippet(),
                                requestFields(
                                        fieldWithPath("laneId").description("레인 번호"),
                                        fieldWithPath("record").description("기록")
                                ),
                                responseFields(getFailResponseField)
                        )
                );
    }

    @Test
    @DisplayName("기록 등록 실패 - 기록이 없음")
    void saveRecordFailByRecordNotExist() throws Exception {
        RecordSaveRequest recordSaveRequest = new RecordSaveRequest(1L, "");

        ResultActions perform = this.mockMvc.perform(
                post("/records")
                        .content(objectMapper.writeValueAsString(recordSaveRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(SecurityConstants.TOKEN_HEADER, getRefereeToken().getAccessToken())
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        );

        perform
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(
                        document("fail-record-save-valid2",
                                getJwtRequestHeadersSnippet(),
                                requestFields(
                                        fieldWithPath("laneId").description("레인 번호"),
                                        fieldWithPath("record").description("기록")
                                ),
                                responseFields(getFailResponseField)
                        )
                );
    }

    @Test
    @DisplayName("기록 등록 실패 - 레인이 존재하지 않음")
    void saveRecordFailByLaneNotExist() throws Exception {
        RecordSaveRequest recordSaveRequest = new RecordSaveRequest(0L, "1:00");

        ResultActions perform = this.mockMvc.perform(
                post("/records")
                        .content(objectMapper.writeValueAsString(recordSaveRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(SecurityConstants.TOKEN_HEADER, getRefereeToken().getAccessToken())
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        );

        perform
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(
                        document("fail-record-save-lane-not-exist",
                                getJwtRequestHeadersSnippet(),
                                requestFields(
                                        fieldWithPath("laneId").description("레인 번호"),
                                        fieldWithPath("record").description("기록")
                                ),
                                responseFields(getFailResponseField)
                        )
                );
    }

    @Test
    @DisplayName("기록 등록 성공")
    void saveRecordSuccess() throws Exception {
        RecordSaveRequest recordSaveRequest = new RecordSaveRequest(1L, "1:00");

        ResultActions perform = this.mockMvc.perform(
                post("/records")
                        .content(objectMapper.writeValueAsString(recordSaveRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(SecurityConstants.TOKEN_HEADER, getRefereeToken().getAccessToken())
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        );

        perform
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("success-record-save",
                                getJwtRequestHeadersSnippet(),
                                requestFields(
                                        fieldWithPath("laneId").description("레인 번호"),
                                        fieldWithPath("record").description("기록")
                                )
                        )
                );
    }

    @Test
    @DisplayName("점수 정산 실패 - 조 고유번호가 없음")
    void saveTotalRecordFailBySectionIdNotExist() throws Exception {
        RecordTotalSaveRequest recordTotalSaveRequest = new RecordTotalSaveRequest(null, 1L);

        List<FieldDescriptor> requestFieldDescriptors = new ArrayList<>();
        requestFieldDescriptors.add(fieldWithPath("sectionId").description("조 고유번호"));
        requestFieldDescriptors.add(fieldWithPath("competitionEventId").description("대회 종목 고유번호"));

        ResultActions perform = this.mockMvc.perform(
                post("/records/total")
                        .content(objectMapper.writeValueAsString(recordTotalSaveRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(SecurityConstants.TOKEN_HEADER, getRefereeToken().getAccessToken())
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        );

        perform
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(
                        document("fail-record-total-save-valid1",
                                getJwtRequestHeadersSnippet(),
                                requestFields(requestFieldDescriptors),
                                responseFields(getFailResponseField)
                        )
                );
    }

    @Test
    @DisplayName("점수 정산 실패 - 대회 종목 고유번호가 없음")
    void saveTotalRecordFailByCompetitionEventIdNotExist() throws Exception {
        RecordTotalSaveRequest recordTotalSaveRequest = new RecordTotalSaveRequest(1L, null);

        List<FieldDescriptor> requestFieldDescriptors = new ArrayList<>();
        requestFieldDescriptors.add(fieldWithPath("sectionId").description("조 고유번호"));
        requestFieldDescriptors.add(fieldWithPath("competitionEventId").description("대회 종목 고유번호"));

        ResultActions perform = this.mockMvc.perform(
                post("/records/total")
                        .content(objectMapper.writeValueAsString(recordTotalSaveRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(SecurityConstants.TOKEN_HEADER, getRefereeToken().getAccessToken())
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        );

        perform
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(
                        document("fail-record-total-save-valid2",
                                getJwtRequestHeadersSnippet(),
                                requestFields(requestFieldDescriptors),
                                responseFields(getFailResponseField)
                        )
                );
    }

    @Test
    @DisplayName("점수 정산 실패 - 조가 존재하지 않음")
    void saveTotalRecordFailBySectionNotExist() throws Exception {
        RecordTotalSaveRequest recordTotalSaveRequest = new RecordTotalSaveRequest(0L, 1L);

        List<FieldDescriptor> requestFieldDescriptors = new ArrayList<>();
        requestFieldDescriptors.add(fieldWithPath("sectionId").description("조 고유번호"));
        requestFieldDescriptors.add(fieldWithPath("competitionEventId").description("대회 종목 고유번호"));

        ResultActions perform = this.mockMvc.perform(
                post("/records/total")
                        .content(objectMapper.writeValueAsString(recordTotalSaveRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(SecurityConstants.TOKEN_HEADER, getRefereeToken().getAccessToken())
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        );

        perform
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(
                        document("fail-record-total-save-section-not-exist",
                                getJwtRequestHeadersSnippet(),
                                requestFields(requestFieldDescriptors),
                                responseFields(getFailResponseField)
                        )
                );
    }

    @Test
    @DisplayName("점수 정산 실패 - 대회 종목이 존재하지 않음")
    void saveTotalRecordFailByCompetitionEventNotExist() throws Exception {
        RecordTotalSaveRequest recordTotalSaveRequest = new RecordTotalSaveRequest(1L, 0L);

        List<FieldDescriptor> requestFieldDescriptors = new ArrayList<>();
        requestFieldDescriptors.add(fieldWithPath("sectionId").description("조 고유번호"));
        requestFieldDescriptors.add(fieldWithPath("competitionEventId").description("대회 종목 고유번호"));

        ResultActions perform = this.mockMvc.perform(
                post("/records/total")
                        .content(objectMapper.writeValueAsString(recordTotalSaveRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(SecurityConstants.TOKEN_HEADER, getRefereeToken().getAccessToken())
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        );

        perform
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(
                        document("fail-record-total-save-competition-event-not-exist",
                                getJwtRequestHeadersSnippet(),
                                requestFields(requestFieldDescriptors),
                                responseFields(getFailResponseField)
                        )
                );
    }

    @Test
    @DisplayName("점수 정산 성공 - 단체전")
    void saveTotalRecordSuccessByTeam() throws Exception {
        RecordTotalSaveRequest recordTotalSaveRequest = new RecordTotalSaveRequest(2L, 1L);

        List<FieldDescriptor> requestFieldDescriptors = new ArrayList<>();
        requestFieldDescriptors.add(fieldWithPath("sectionId").description("조 고유번호"));
        requestFieldDescriptors.add(fieldWithPath("competitionEventId").description("대회 종목 고유번호"));

        ResultActions perform = this.mockMvc.perform(
                post("/records/total")
                        .content(objectMapper.writeValueAsString(recordTotalSaveRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(SecurityConstants.TOKEN_HEADER, getRefereeToken().getAccessToken())
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        );

        perform
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(
                        document("success-record-total-save-team",
                                getJwtRequestHeadersSnippet(),
                                requestFields(requestFieldDescriptors)
                        )
                );
    }

    @Test
    @DisplayName("점수 정산 성공 - 개인전")
    void saveTotalRecordSuccessByIndividual() throws Exception {
        RecordTotalSaveRequest recordTotalSaveRequest = new RecordTotalSaveRequest(1L, 56L);

        List<FieldDescriptor> requestFieldDescriptors = new ArrayList<>();
        requestFieldDescriptors.add(fieldWithPath("sectionId").description("조 고유번호"));
        requestFieldDescriptors.add(fieldWithPath("competitionEventId").description("대회 종목 고유번호"));

        ResultActions perform = this.mockMvc.perform(
                post("/records/total")
                        .content(objectMapper.writeValueAsString(recordTotalSaveRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(SecurityConstants.TOKEN_HEADER, getRefereeToken().getAccessToken())
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        );

        perform
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("success-record-total-save-individual",
                                getJwtRequestHeadersSnippet(),
                                requestFields(requestFieldDescriptors)
                        )
                );
    }
}
