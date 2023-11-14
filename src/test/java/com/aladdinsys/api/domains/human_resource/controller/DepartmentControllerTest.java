package com.aladdinsys.api.domains.human_resource.controller;

import com.aladdinsys.api.common.constant.ErrorCode;
import com.aladdinsys.api.common.constant.SuccessCode;
import com.aladdinsys.api.common.exception.CustomException;
import com.aladdinsys.api.domains.human_resource.dto.DepartmentRequestDto;
import com.aladdinsys.api.domains.human_resource.dto.DepartmentResponseDto;
import com.aladdinsys.api.domains.human_resource.service.DepartmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.aladdinsys.api.ApiDocumentUtils.getDocumentRequest;
import static com.aladdinsys.api.ApiDocumentUtils.getDocumentResponse;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ExtendWith({MockitoExtension.class, RestDocumentationExtension.class})
@Transactional
class DepartmentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    EntityManager em;

    @Autowired
    DepartmentService departmentService;

    ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {

        objectMapper = new ObjectMapper();

        Long id = departmentService.save(new DepartmentRequestDto("이름"));

        em.clear();
    }

    @Test
    @DisplayName("GET /departments")
    void getDepartments() throws Exception {

        List<DepartmentResponseDto> departments = departmentService.findAll();
        DepartmentResponseDto dto = departments.get(0);

        Integer id = dto.id().intValue();
        String name = dto.name();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/departments")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.result").exists())
                        .andDo(document("{class-name}/{method-name}",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("timestamp").type(JsonFieldType.STRING).description("시간"),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"),
                                fieldWithPath("result[].id").type(JsonFieldType.NUMBER).description("아이디"),
                                fieldWithPath("result[].name").type(JsonFieldType.STRING).description("이름")
                        )
                ))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertThat(JsonPath.<Integer>read(jsonResponse, "$.result[0].id")).isEqualTo(id);
        assertThat(JsonPath.<String>read(jsonResponse, "$.result[0].name")).isEqualTo(name);
    }

    @Test
    @DisplayName("GET /departments/{id}")
    void getDepartmentOne() throws Exception {

        String name = "부서 이름";
        Long savedId = departmentService.save(new DepartmentRequestDto(name));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/departments/{id}", savedId)
                            .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.result").exists())
                        .andDo(document("{class-name}/{method-name}",
                                    getDocumentRequest(),
                                    getDocumentResponse(),
                                    responseFields(
                                            fieldWithPath("timestamp").type(JsonFieldType.STRING).description("시간"),
                                            fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                            fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"),
                                            fieldWithPath("result.id").type(JsonFieldType.NUMBER).description("아이디"),
                                            fieldWithPath("result.name").type(JsonFieldType.STRING).description("이름")
                                    )
                                )
                        )
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertThat(JsonPath.<Integer>read(jsonResponse, "$.result.id")).isEqualTo(savedId.intValue());
        assertThat(JsonPath.<String>read(jsonResponse, "$.result.name")).isEqualTo(name);
    }

    @Test
    @DisplayName("POST /departments")
    void post() throws Exception {

        DepartmentRequestDto requestDto = DepartmentRequestDto.builder()
                                            .name("부서")
                                            .build();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/departments")
                                                    .contentType(MediaType.APPLICATION_JSON)
                                                    .content(objectMapper.writeValueAsString(requestDto)))
                                    .andExpect(status().isCreated())
                                    .andDo(document("{class-name}/{method-name}",
                                            getDocumentRequest(),
                                            getDocumentResponse(),
                                            responseFields(
                                                    fieldWithPath("timestamp").type(JsonFieldType.STRING).description("시간"),
                                                    fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                                    fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"),
                                                    fieldWithPath("result").type(JsonFieldType.NUMBER).description("아이디")
                                            )
                                        )
                                    )
                                    .andReturn();

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertThat(JsonPath.<String>read(jsonResponse, "$.status")).isEqualTo(SuccessCode.SUCCESS_CREATE.getHttpStatus().name());
    }

    @Test
    @DisplayName("PUT /departments/{id}")
    void put() throws Exception {

        DepartmentRequestDto requestDto = DepartmentRequestDto.builder()
                .name("부서_수정")
                .build();

        DepartmentResponseDto responseDto = departmentService.findAll().get(0);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/departments/{id}", responseDto.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isAccepted())
                .andDo(document("{class-name}/{method-name}",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                responseFields(
                                        fieldWithPath("timestamp").type(JsonFieldType.STRING).description("시간"),
                                        fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("메세지")
                                )
                        )
                )
                .andReturn();

        DepartmentResponseDto afterPut = departmentService.findAll().get(0);

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertThat(JsonPath.<String>read(jsonResponse, "$.status")).isEqualTo(SuccessCode.SUCCESS_PUT.getHttpStatus().name());
        assertThat(afterPut.name()).isEqualTo(requestDto.name());

    }

    @Test
    @DisplayName("DELETE /departments/{id}")
    void delete() throws Exception {

        DepartmentResponseDto responseDto = departmentService.findAll().get(0);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/departments/{id}",responseDto.id())
                                            .contentType(MediaType.APPLICATION_JSON))
                                    .andExpect(status().isNoContent())
                                    .andDo(document("{class-name}/{method-name}",
                                            getDocumentRequest(),
                                            getDocumentResponse(),
                                            responseFields(
                                                    fieldWithPath("timestamp").type(JsonFieldType.STRING).description("시간"),
                                                    fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                                    fieldWithPath("message").type(JsonFieldType.STRING).description("메세지")
                                            )
                                        )
                                    )
                                    .andReturn();

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

        CustomException exception = assertThrows(CustomException.class, () -> {
            departmentService.findById(responseDto.id());
        });

        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.NOT_FOUND);
        assertThat(JsonPath.<String>read(jsonResponse, "$.status")).isEqualTo(SuccessCode.SUCCESS_DELETE.getHttpStatus().name());
    }
}