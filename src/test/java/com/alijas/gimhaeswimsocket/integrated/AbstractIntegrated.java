package com.alijas.gimhaeswimsocket.integrated;

import com.alijas.gimhaeswimsocket.modules.common.dto.LoginRequest;
import com.alijas.gimhaeswimsocket.modules.common.dto.ResUserLoginDTO;
import com.alijas.gimhaeswimsocket.modules.user.repository.UserRepository;
import com.alijas.gimhaeswimsocket.security.SecurityConstants;
import com.alijas.gimhaeswimsocket.security.SecurityService;
import com.alijas.gimhaeswimsocket.security.SecurityTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.headers.RequestHeadersSnippet;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.Objects;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AbstractIntegrated {

    protected MockMvc mockMvc;

    protected final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    protected SecurityTokenProvider provider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityService securityService;

    @Autowired
    protected SecurityTokenProvider securityTokenProvider;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .apply(documentationConfiguration(restDocumentation))
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    public ResUserLoginDTO getRefereeToken() throws Exception {
        LoginRequest reqUserLoginDTO = new LoginRequest("referee1", "1234");

        SecurityContextHolder.clearContext();
        String result = mockMvc.perform(
                        MockMvcRequestBuilders.post("/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(reqUserLoginDTO))
                )
                .andReturn()
                .getResponse()
                .getContentAsString();

        ResUserLoginDTO resUserLoginDTO = objectMapper.readValue(result, ResUserLoginDTO.class);

        String accessToken = Objects.requireNonNull(resUserLoginDTO.getAccessToken()).replace(SecurityConstants.TOKEN_HEADER, "");
        Authentication authentication = securityTokenProvider.getAuthentication(accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return resUserLoginDTO;
    }

    public ResUserLoginDTO getReferee4Token() throws Exception {
        LoginRequest reqUserLoginDTO = new LoginRequest("referee4", "1234");

        SecurityContextHolder.clearContext();
        String result = mockMvc.perform(
                        MockMvcRequestBuilders.post("/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(reqUserLoginDTO))
                )
                .andReturn()
                .getResponse()
                .getContentAsString();

        ResUserLoginDTO resUserLoginDTO = objectMapper.readValue(result, ResUserLoginDTO.class);

        String accessToken = Objects.requireNonNull(resUserLoginDTO.getAccessToken()).replace(SecurityConstants.TOKEN_HEADER, "");
        Authentication authentication = securityTokenProvider.getAuthentication(accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return resUserLoginDTO;
    }

    protected FieldDescriptor[] getFailResponseField = {
            fieldWithPath("type")
                    .type(JsonFieldType.STRING)
                    .attributes(getFormatAttribute(""))
                    .description("type"),
            fieldWithPath("title")
                    .type(JsonFieldType.STRING)
                    .attributes(getFormatAttribute(""))
                    .description("에러 코드 (이름)"),
            fieldWithPath("status")
                    .type(JsonFieldType.NUMBER)
                    .attributes(getFormatAttribute(""))
                    .description("에러 코드"),
            fieldWithPath("detail")
                    .type(JsonFieldType.STRING)
                    .attributes(getFormatAttribute(""))
                    .description("에러 메세지 (중요)"),
            fieldWithPath("instance")
                    .type(JsonFieldType.STRING)
                    .attributes(getFormatAttribute(""))
                    .description("요청 경로")
    };
    public Attributes.Attribute getFormatAttribute(String value){
        return new Attributes.Attribute("format", value);
    }

    protected RequestHeadersSnippet getJwtRequestHeadersSnippet () {
        return requestHeaders(
                headerWithName("Authorization").description("토큰 정보")
        );
    }

    protected ParameterDescriptor[] getPageParameterDescriptors = {
            parameterWithName("page").description("페이지 번호"),
            parameterWithName("size").description("한 페이지에 보여줄 개수"),
            parameterWithName("sort").description("정렬 방식")
    };
}
