package com.beemiel.springrestdocsexample.controller;

import com.beemiel.springrestdocsexample.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Description;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
@AutoConfigureRestDocs
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberController memberController;

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
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                ));
    }

    @Test
    @Description("회원 하나 가져오기")
    public void testMember() throws Exception {
        Member member = Member.builder().id(1L)
                .name("TEST")
                .age(99)
                .build();

        when(memberController.test(any())).thenReturn(member);

//        get("/accounts/{id}", 1L)
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

}
