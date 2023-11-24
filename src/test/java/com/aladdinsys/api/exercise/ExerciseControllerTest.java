package com.aladdinsys.api.exercise;

import com.aladdinsys.api.common.constant.ErrorCode;
import com.aladdinsys.api.common.constant.SuccessCode;
import com.aladdinsys.api.common.exception.CustomException;
import com.aladdinsys.api.exercise.dto.ExercisePatchDto;
import com.aladdinsys.api.exercise.dto.ExercisePostDto;
import com.aladdinsys.api.exercise.dto.ExerciseResponseDto;
import com.aladdinsys.api.exercise.service.ExerciseService;
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

        int id = dto.id().intValue();
        String standardYear = dto.standardYear();
        String largeExerciseFacility = dto.largeExerciseFacility();
        String middleExerciseFacility = dto.middleExerciseFacility();
        String itemName = dto.itemName();
        String dataValue = dto.dataValue();

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
                                fieldWithPath("result[].id").type(JsonFieldType.NUMBER).description("아이디"),
                                fieldWithPath("result[].standardYear").type(JsonFieldType.STRING).description("기준년도"),
                                fieldWithPath("result[].largeExerciseFacility").type(JsonFieldType.STRING).description("대분류"),
                                fieldWithPath("result[].middleExerciseFacility").type(JsonFieldType.STRING).description("중분류"),
                                fieldWithPath("result[].itemName").type(JsonFieldType.STRING).description("항목명"),
                                fieldWithPath("result[].dataValue").type(JsonFieldType.STRING).description("데이터 값")
                        )
                ))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertThat(JsonPath.<Integer>read(jsonResponse, "$.result[0].id")).isEqualTo(id);
        assertThat(JsonPath.<String>read(jsonResponse, "$.result[0].standardYear")).isEqualTo(standardYear);
        assertThat(JsonPath.<String>read(jsonResponse, "$.result[0].largeExerciseFacility")).isEqualTo(largeExerciseFacility);
        assertThat(JsonPath.<String>read(jsonResponse, "$.result[0].middleExerciseFacility")).isEqualTo(middleExerciseFacility);
        assertThat(JsonPath.<String>read(jsonResponse, "$.result[0].itemName")).isEqualTo(itemName);
        assertThat(JsonPath.<String>read(jsonResponse, "$.result[0].dataValue")).isEqualTo(dataValue);
    }

    @Test
    @DisplayName("GET /exercise/{id}")
    void getAdministrativeDistrictOne() throws Exception {
        int id = service.findAll().get(0).id().intValue();
        String standardYear = service.findAll().get(0).standardYear();
        String largeExerciseFacility = service.findAll().get(0).largeExerciseFacility();
        String middleExerciseFacility = service.findAll().get(0).middleExerciseFacility();
        String itemName = service.findAll().get(0).itemName();
        String dataValue = service.findAll().get(0).dataValue();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/exercise/{id}", id)
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
                                fieldWithPath("result.standardYear").type(JsonFieldType.STRING).description("기준년도"),
                                fieldWithPath("result.largeExerciseFacility").type(JsonFieldType.STRING).description("대분류"),
                                fieldWithPath("result.middleExerciseFacility").type(JsonFieldType.STRING).description("중분류"),
                                fieldWithPath("result.itemName").type(JsonFieldType.STRING).description("항목명"),
                                fieldWithPath("result.dataValue").type(JsonFieldType.STRING).description("데이터 값")
                        )
                ))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertThat(JsonPath.<Integer>read(jsonResponse, "$.result.id")).isEqualTo(id);
        assertThat(JsonPath.<String>read(jsonResponse, "$.result.standardYear")).isEqualTo(standardYear);
        assertThat(JsonPath.<String>read(jsonResponse, "$.result.largeExerciseFacility")).isEqualTo(largeExerciseFacility);
        assertThat(JsonPath.<String>read(jsonResponse, "$.result.middleExerciseFacility")).isEqualTo(middleExerciseFacility);
        assertThat(JsonPath.<String>read(jsonResponse, "$.result.itemName")).isEqualTo(itemName);
        assertThat(JsonPath.<String>read(jsonResponse, "$.result.dataValue")).isEqualTo(dataValue);
    }

    @Test
    @DisplayName("POST /exercise/{id}")
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

        assertThat(responseDto.standardYear()).isEqualTo("2023");
        assertThat(responseDto.largeExerciseFacility()).isEqualTo("체육시설");
        assertThat(responseDto.middleExerciseFacility()).isEqualTo("농구장");
        assertThat(responseDto.itemName()).isEqualTo("시설수 (개)");
        assertThat(responseDto.dataValue()).isEqualTo("1");
    }

    @Test
    @DisplayName("PUT /exercise/{id}")
    void put() throws Exception {

        ExerciseResponseDto responseDto = service.findAll().get(0);
        Long id = responseDto.id();

        ExercisePatchDto putDto = ExercisePatchDto.builder()
                .id(id)
                .standardYear("2027")
                .largeExerciseFacility("체육시설")
                .middleExerciseFacility("농구장")
                .itemName("시설수 (개)")
                .dataValue("10")
                .build();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/exercise/{id}", responseDto.id())
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

        ExerciseResponseDto afterPut = service.findById(responseDto.id());

        assertThat(afterPut.standardYear()).isEqualTo("2027");
        assertThat(afterPut.largeExerciseFacility()).isEqualTo("체육시설");
        assertThat(afterPut.middleExerciseFacility()).isEqualTo("농구장");
        assertThat(afterPut.itemName()).isEqualTo("시설수 (개)");
        assertThat(afterPut.dataValue()).isEqualTo("10");
    }

    @Test
    @DisplayName("PATCH /exercise/{id}")
    void patch() throws Exception {

        ExerciseResponseDto responseDto = service.findAll().get(0);

        Long id = responseDto.id();
        ExercisePatchDto patchDto = ExercisePatchDto.builder()
                .dataValue("20")
                .build();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.patch("/exercise/{id}", responseDto.id())
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

        ExerciseResponseDto afterPatch = service.findById(responseDto.id());

        assertThat(afterPatch.dataValue()).isEqualTo("20");
    }

    @Test
    @DisplayName("DELETE /exercise/{id}")
    void delete() throws Exception {

        ExerciseResponseDto responseDto = service.findAll().get(0);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/exercise/{id}",responseDto.id())
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
            service.findById(responseDto.id());
        });

        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.NOT_FOUND);
        assertThat(JsonPath.<String>read(jsonResponse, "$.status")).isEqualTo(SuccessCode.SUCCESS_DELETE.getHttpStatus().name());
    }
}
