package com.taeheon.graal.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taeheon.graal.member.model.dto.MemberCreateDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class})
public class MemberControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    public void getMember() throws Exception {
        this.mockMvc.perform(get("/member/{id}", 1L).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("member-get-one",
                        pathParameters(parameterWithName("id").description("member id")),
                        responseFields(
                                fieldWithPath("memberId").description("member id"),
                                fieldWithPath("age").description("member age"),
                                fieldWithPath("name").description("member name")
                        )));
    }

    @Test
    public void postMember() throws Exception {
        MemberCreateDTO createDTO = new MemberCreateDTO();
        createDTO.setAge(10);
        createDTO.setName("test");
        this.mockMvc
                .perform(
                        post("/member")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(this.objectMapper.writeValueAsBytes(createDTO))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("age", is(10)))
                .andExpect(jsonPath("name", is("test")))
                .andDo(document("member-post",
                        requestFields(
                                fieldWithPath("age").description("member id"),
                                fieldWithPath("name").description("member name")
                        ),
                        responseFields(
                                fieldWithPath("memberId").description("member id"),
                                fieldWithPath("age").description("member age"),
                                fieldWithPath("name").description("member name")
                        )));
    }
}
