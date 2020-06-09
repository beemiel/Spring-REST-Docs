package com.beemiel.springrestdocsexample.controller;

import com.beemiel.springrestdocsexample.entity.Member;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Description;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MemberController.class)
@AutoConfigureRestDocs
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberController memberController;

    private Member member;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        member = Member.builder().id(1L)
                .name("TEST")
                .age(99)
                .build();
    }

    @Test
    @Description("모든 회원 가져오기")
    public void testList() throws Exception {
        List<Member> members = Arrays.asList(
                new Member(1L, "lynn", 11),
                new Member(2L, "lynn2", 22),
                new Member(3L, "lynn3", 33)
        );

        when(memberController.listTest()).thenReturn(members);

        mockMvc.perform(get("/test/list"))
                .andExpect(status().isOk())
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(modifyUris()
                                .scheme("https")
                                .host("aws-test.com")
                                .removePort(),prettyPrint()),
                        //modifyUris를 사용하면 request 및 response에 있는 URI를 변경할 수 있다.
                        preprocessResponse(prettyPrint())
                ));
    }

    @Test
    @Description("회원 하나 가져오기")
    public void testMember() throws Exception {
        when(memberController.test(any())).thenReturn(member);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/test/list/{id}", member.getId()))
                .andExpect(status().isOk())
                .andDo(document("{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("회원의 id")
                        ),
                        responseFields(
                                fieldWithPath("id").description("회원의 id").type(Long.class),
                                fieldWithPath("name").description("Member's name"),
                                fieldWithPath("age").description("Member's age")
                        )
                ));
//                .andExpect((ResultMatcher) jsonPath("$.data.name", is("TEST12")));

    }

    @Test
    public void saveMember() throws Exception {
        Member member2 = new Member(4L, 99);
        when(memberController.postTest(any())).thenReturn(member2);

        mockMvc.perform(post("/test/post")
                    .content(objectMapper.writeValueAsString(member2))
                    .contentType(MediaType.APPLICATION_JSON)) //이거 해야지 member가 리퀘스트 파람이 아니라 바디에 제이슨타입으로 들어가는듯?
                .andExpect(status().isOk())
//                .andExpect(content().json("{\"id\": 4, \"age\": 99}"))
                .andExpect(jsonPath("$.age", is(99)))
                .andDo(document(
                       "{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("id").description("post").type(Long.class),
                                fieldWithPath("name").description("post").optional(),
                                fieldWithPath("age").description("post")
                        ),
                        requestFields(
                                fieldWithPath("id").description("request/post").type(Long.class),
                                fieldWithPath("name").description("request/post").optional(),
                                fieldWithPath("age").description("request/post")
                        )
                ));
    }

    @Test
    public void updateMember() {
        when(memberController.update(any())).thenReturn(member);
    }

}
