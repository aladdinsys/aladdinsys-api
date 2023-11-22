package com.aladdinsys.api.exercise_resource;

import com.aladdinsys.api.common.constant.ErrorCode;
import com.aladdinsys.api.common.constant.SuccessCode;
import com.aladdinsys.api.common.exception.CustomException;
import com.aladdinsys.api.exercise_resource.dto.ExercisePatchDto;
import com.aladdinsys.api.exercise_resource.dto.ExercisePostDto;
import com.aladdinsys.api.exercise_resource.dto.ExerciseResponseDto;
import com.aladdinsys.api.exercise_resource.service.ExerciseService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ExtendWith({MockitoExtension.class, RestDocumentationExtension.class})
@Transactional
class ExerciseControllerTest{
    @Autowired
    MockMvc mockMvc;

    @Autowired
    EntityManager em;

    @Autowired
    ExerciseService service;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        service.save(new ExercisePostDto(
                "2023", "공공체육시설",
                "축구장", "시설수 (개)", "1"));

        em.clear();
    }

    @Test
    @DisplayName("GET /exercise")
    void getExercise() throws Exception {
        List<ExerciseResponseDto> districts = service.findAll();
        ExerciseResponseDto dto = districts.get(0);

        int dataId = dto.dataId().intValue();
        String stdYy = dto.stdYy();
        String phsrtnFcltyLclasNm = dto.phsrtnFcltyLclasNm();
        String phsrtnFcltyMclasNm = dto.phsrtnFcltyMclasNm();
        String iemNm = dto.iemNm();
        String dataVal = dto.dataVal();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/exercise")
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
                                fieldWithPath("result[].dataId").type(JsonFieldType.NUMBER).description("아이디"),
                                fieldWithPath("result[].stdYy").type(JsonFieldType.STRING).description("기준년도"),
                                fieldWithPath("result[].phsrtnFcltyLclasNm").type(JsonFieldType.STRING).description("대분류"),
                                fieldWithPath("result[].phsrtnFcltyMclasNm").type(JsonFieldType.STRING).description("중분류"),
                                fieldWithPath("result[].iemNm").type(JsonFieldType.STRING).description("항목명"),
                                fieldWithPath("result[].dataVal").type(JsonFieldType.STRING).description("데이터 값")
                        )
                ))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertThat(JsonPath.<Integer>read(jsonResponse, "$.result[0].dataId")).isEqualTo(dataId);
        assertThat(JsonPath.<String>read(jsonResponse, "$.result[0].stdYy")).isEqualTo(stdYy);
        assertThat(JsonPath.<String>read(jsonResponse, "$.result[0].phsrtnFcltyLclasNm")).isEqualTo(phsrtnFcltyLclasNm);
        assertThat(JsonPath.<String>read(jsonResponse, "$.result[0].phsrtnFcltyMclasNm")).isEqualTo(phsrtnFcltyMclasNm);
        assertThat(JsonPath.<String>read(jsonResponse, "$.result[0].iemNm")).isEqualTo(iemNm);
        assertThat(JsonPath.<String>read(jsonResponse, "$.result[0].dataVal")).isEqualTo(dataVal);
    }

    @Test
    @DisplayName("GET /exercise/{dataId}")
    void getAdministrativeDistrictOne() throws Exception {
        int dataId = service.findAll().get(0).dataId().intValue();
        String stdYy = service.findAll().get(0).stdYy();
        String phsrtnFcltyLclasNm = service.findAll().get(0).phsrtnFcltyLclasNm();
        String phsrtnFcltyMclasNm = service.findAll().get(0).phsrtnFcltyMclasNm();
        String iemNm = service.findAll().get(0).iemNm();
        String dataVal = service.findAll().get(0).dataVal();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/exercise/{dataId}", dataId)
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
                                fieldWithPath("result.dataId").type(JsonFieldType.NUMBER).description("아이디"),
                                fieldWithPath("result.stdYy").type(JsonFieldType.STRING).description("기준년도"),
                                fieldWithPath("result.phsrtnFcltyLclasNm").type(JsonFieldType.STRING).description("대분류"),
                                fieldWithPath("result.phsrtnFcltyMclasNm").type(JsonFieldType.STRING).description("중분류"),
                                fieldWithPath("result.iemNm").type(JsonFieldType.STRING).description("항목명"),
                                fieldWithPath("result.dataVal").type(JsonFieldType.STRING).description("데이터 값")
                        )
                ))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertThat(JsonPath.<Integer>read(jsonResponse, "$.result.dataId")).isEqualTo(dataId);
        assertThat(JsonPath.<String>read(jsonResponse, "$.result.stdYy")).isEqualTo(stdYy);
        assertThat(JsonPath.<String>read(jsonResponse, "$.result.phsrtnFcltyLclasNm")).isEqualTo(phsrtnFcltyLclasNm);
        assertThat(JsonPath.<String>read(jsonResponse, "$.result.phsrtnFcltyMclasNm")).isEqualTo(phsrtnFcltyMclasNm);
        assertThat(JsonPath.<String>read(jsonResponse, "$.result.iemNm")).isEqualTo(iemNm);
        assertThat(JsonPath.<String>read(jsonResponse, "$.result.dataVal")).isEqualTo(dataVal);
    }

    @Test
    @DisplayName("POST /exercise/{dataId}")
    void post() throws Exception {

        ExercisePostDto postDto = new ExercisePostDto(
                "2023", "체육시설", "농구장", "시설수 (개)", "1");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/exercise")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
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
                ))
                .andReturn();


        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

        Long id = JsonPath.<Integer>read(jsonResponse, "$.result").longValue();
        ExerciseResponseDto responseDto = service.findById(id);

        assertThat(responseDto.stdYy()).isEqualTo("2023");
        assertThat(responseDto.phsrtnFcltyLclasNm()).isEqualTo("체육시설");
        assertThat(responseDto.phsrtnFcltyMclasNm()).isEqualTo("농구장");
        assertThat(responseDto.iemNm()).isEqualTo("시설수 (개)");
        assertThat(responseDto.dataVal()).isEqualTo("1");
    }

    @Test
    @DisplayName("PUT /exercise/{dataId}")
    void put() throws Exception {

        ExerciseResponseDto responseDto = service.findAll().get(0);
        Long dataId = responseDto.dataId();

        ExercisePatchDto putDto = ExercisePatchDto.builder()
                .dataId(dataId)
                .stdYy("2027")
                .phsrtnFcltyLclasNm("체육시설")
                .phsrtnFcltyMclasNm("농구장")
                .iemNm("시설수 (개)")
                .dataVal("10")
                .build();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/exercise/{dataId}", responseDto.dataId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(putDto)))
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

        ExerciseResponseDto afterPut = service.findById(responseDto.dataId());

        assertThat(afterPut.stdYy()).isEqualTo("2027");
        assertThat(afterPut.phsrtnFcltyLclasNm()).isEqualTo("체육시설");
        assertThat(afterPut.phsrtnFcltyMclasNm()).isEqualTo("농구장");
        assertThat(afterPut.iemNm()).isEqualTo("시설수 (개)");
        assertThat(afterPut.dataVal()).isEqualTo("10");
    }

    @Test
    @DisplayName("PATCH /exercise/{dataId}")
    void patch() throws Exception {

        ExerciseResponseDto responseDto = service.findAll().get(0);

        Long dataId = responseDto.dataId();
        ExercisePatchDto patchDto = ExercisePatchDto.builder()
                .dataVal("20")
                .build();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.patch("/exercise/{dataId}", responseDto.dataId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patchDto)))
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

        ExerciseResponseDto afterPatch = service.findById(responseDto.dataId());

        assertThat(afterPatch.dataVal()).isEqualTo("20");
    }

    @Test
    @DisplayName("DELETE /exercise/{dataId}")
    void delete() throws Exception {

        ExerciseResponseDto responseDto = service.findAll().get(0);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/exercise/{dataId}",responseDto.dataId())
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
            service.findById(responseDto.dataId());
        });

        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.NOT_FOUND);
        assertThat(JsonPath.<String>read(jsonResponse, "$.status")).isEqualTo(SuccessCode.SUCCESS_DELETE.getHttpStatus().name());
    }
}
