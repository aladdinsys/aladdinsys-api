package com.aladdinsys.api.domains.human_resource.controller;

import com.aladdinsys.api.common.constant.ErrorCode;
import com.aladdinsys.api.common.constant.SuccessCode;
import com.aladdinsys.api.common.exception.CustomException;
import com.aladdinsys.api.domains.human_resource.dto.*;
import com.aladdinsys.api.domains.human_resource.service.DepartmentService;
import com.aladdinsys.api.domains.human_resource.service.EmployeeService;
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
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
class EmployeeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    EntityManager em;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    EmployeeService employeeService;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {

        objectMapper = new ObjectMapper();

        Long departmentId = departmentService.save(new DepartmentRequestDto("부서1"));
        employeeService.save(new EmployeePostDto("직원1", departmentId));

        em.clear();
    }

    @Test
    @DisplayName("GET /employees")
    void getEmployees() throws Exception {

        List<EmployeeResponseDto> employees = employeeService.findAll();
        EmployeeResponseDto dto = employees.get(0);

        int id = dto.id().intValue();
        String name = dto.name();
        DepartmentResponseDto departmentDto = dto.department();

        int departmentId = departmentDto.id().intValue();
        String departmentName = departmentDto.name();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/employees")
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
                                fieldWithPath("result[].name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("result[].department.id").type(JsonFieldType.NUMBER).description("부서 아이디"),
                                fieldWithPath("result[].department.name").type(JsonFieldType.STRING).description("부서명")
                        )
                ))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertThat(JsonPath.<Integer>read(jsonResponse, "$.result[0].id")).isEqualTo(id);
        assertThat(JsonPath.<String>read(jsonResponse, "$.result[0].name")).isEqualTo(name);

        assertThat(JsonPath.<Integer>read(jsonResponse, "$.result[0].department.id")).isEqualTo(departmentId);
        assertThat(JsonPath.<String>read(jsonResponse, "$.result[0].department.name")).isEqualTo(departmentName);

    }

    @Test
    @DisplayName("GET /employees/{id}")
    void getEmployeeOne() throws Exception {

        List<EmployeeResponseDto> employees = employeeService.findAll();
        EmployeeResponseDto dto = employees.get(0);

        int id = dto.id().intValue();
        String name = dto.name();
        DepartmentResponseDto departmentDto = dto.department();

        int departmentId = departmentDto.id().intValue();
        String departmentName = departmentDto.name();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/employees/{id}", id)
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
                                        fieldWithPath("result.name").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("result.department.id").type(JsonFieldType.NUMBER).description("부서 아이디"),
                                        fieldWithPath("result.department.name").type(JsonFieldType.STRING).description("부서명")
                                )
                            )
                        )
                        .andReturn();

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertThat(JsonPath.<Integer>read(jsonResponse, "$.result.id")).isEqualTo(id);
        assertThat(JsonPath.<String>read(jsonResponse, "$.result.name")).isEqualTo(name);

        assertThat(JsonPath.<Integer>read(jsonResponse, "$.result.department.id")).isEqualTo(departmentId);
        assertThat(JsonPath.<String>read(jsonResponse, "$.result.department.name")).isEqualTo(departmentName);
    }

    @Test
    @DisplayName("GET /departments/{departmentId}/employees")
    void getEmployeesByDepartment() throws Exception {

        List<EmployeeResponseDto> employees = employeeService.findAll();
        EmployeeResponseDto dto = employees.get(0);

        int id = dto.id().intValue();
        String name = dto.name();
        DepartmentResponseDto departmentDto = dto.department();

        int departmentId = departmentDto.id().intValue();
        String departmentName = departmentDto.name();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/departments/{departmentId}/employees", departmentId)
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
                                fieldWithPath("result[].name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("result[].department.id").type(JsonFieldType.NUMBER).description("부서 아이디"),
                                fieldWithPath("result[].department.name").type(JsonFieldType.STRING).description("부서명")
                        )
                ))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertThat(JsonPath.<Integer>read(jsonResponse, "$.result[0].id")).isEqualTo(id);
        assertThat(JsonPath.<String>read(jsonResponse, "$.result[0].name")).isEqualTo(name);

        assertThat(JsonPath.<Integer>read(jsonResponse, "$.result[0].department.id")).isEqualTo(departmentId);
        assertThat(JsonPath.<String>read(jsonResponse, "$.result[0].department.name")).isEqualTo(departmentName);
    }

    @Test
    @DisplayName("POST /employees/{id}")
    void post() throws Exception {

        String employeeName = "직원2";
        DepartmentResponseDto departmentResponseDto = departmentService.findAll().get(0);

        EmployeePostDto requestDto = new EmployeePostDto(employeeName, departmentResponseDto.id());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/employees")
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

        // JsonPath 를 활용 Response 의 ID 값 조회
        Long id = JsonPath.<Integer>read(jsonResponse, "$.result").longValue();

        // 조회된 ID 값으로 Employee 정보 조회
        EmployeeResponseDto employeeResponseDto = employeeService.findById(id);

        assertThat(employeeResponseDto.name()).isEqualTo(employeeName);
        assertThat(JsonPath.<String>read(jsonResponse, "$.status")).isEqualTo(SuccessCode.SUCCESS_CREATE.getHttpStatus().name());
    }

    @Test
    @DisplayName("PUT /employees/{id}")
    void put() throws Exception {

        EmployeeResponseDto responseDto = employeeService.findAll().get(0);

        String employeeName = "수정된 직원 이름";
        DepartmentResponseDto departmentResponseDto = departmentService.findAll().get(0);

        EmployeePostDto requestDto = new EmployeePostDto(employeeName, departmentResponseDto.id());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/employees/{id}", responseDto.id())
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

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertThat(JsonPath.<String>read(jsonResponse, "$.status")).isEqualTo(SuccessCode.SUCCESS_PUT.getHttpStatus().name());

        EmployeeResponseDto afterPut = employeeService.findById(responseDto.id());

        assertThat(afterPut.name()).isEqualTo(employeeName);
    }

    @Test
    @DisplayName("PATCH /employees/{id}")
    void patch() throws Exception {

        EmployeeResponseDto responseDto = employeeService.findAll().get(0);
        DepartmentResponseDto departmentResponseDto = departmentService.findAll().get(0);

        Long departmentId = departmentResponseDto.id();
        EmployeePatchDto requestDto = EmployeePatchDto.builder()
                                            .departmentId(departmentId)
                                            .build();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.patch("/employees/{id}", responseDto.id())
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

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertThat(JsonPath.<String>read(jsonResponse, "$.status")).isEqualTo(SuccessCode.SUCCESS_PUT.getHttpStatus().name());

        EmployeeResponseDto afterPut = employeeService.findById(responseDto.id());

        assertThat(afterPut.department().id()).isEqualTo(departmentId);
        assertThat(JsonPath.<String>read(jsonResponse, "$.status")).isEqualTo(SuccessCode.SUCCESS_PUT.getHttpStatus().name());
    }

    @Test
    @DisplayName("DELETE /employees/{id}")
    void delete() throws Exception {

        EmployeeResponseDto responseDto = employeeService.findAll().get(0);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/employees/{id}",responseDto.id())
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
            employeeService.findById(responseDto.id());
        });

        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.NOT_FOUND);
        assertThat(JsonPath.<String>read(jsonResponse, "$.status")).isEqualTo(SuccessCode.SUCCESS_DELETE.getHttpStatus().name());
    }
}