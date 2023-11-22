package com.aladdinsys.api.domains.admin_district.controller;

import com.aladdinsys.api.common.constant.ErrorCode;
import com.aladdinsys.api.common.constant.SuccessCode;
import com.aladdinsys.api.common.exception.CustomException;
import com.aladdinsys.api.domains.admin_district.dto.AdministrativeDistrictPatchDto;
import com.aladdinsys.api.domains.admin_district.dto.AdministrativeDistrictPostDto;
import com.aladdinsys.api.domains.admin_district.dto.AdministrativeDistrictResponseDto;
import com.aladdinsys.api.domains.admin_district.service.AdministrativeDistrictService;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ExtendWith({MockitoExtension.class, RestDocumentationExtension.class})
@Transactional
class AdministrativeDistrictControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    EntityManager em;

    @Autowired
    AdministrativeDistrictService service;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        service.save(new AdministrativeDistrictPostDto(
                "2023", "덕양구",
                "소계", "반수 (개)", "267.31"));

        em.clear();
    }

    @Test
    @DisplayName("GET /districts")
    void getAdministrativeDistricts() throws Exception {
        List<AdministrativeDistrictResponseDto> districts = service.findAll();
        AdministrativeDistrictResponseDto dto = districts.get(0);

        int id = dto.id().intValue();
        String standardYear = dto.standardYear();
        String cityCountyDistrictName = dto.cityCountyDistrictName();
        String administrationName = dto.administrationName();
        String itemName = dto.itemName();
        String dataValue = dto.dataValue();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/districts")
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
                                fieldWithPath("result[].cityCountyDistrictName").type(JsonFieldType.STRING).description("시군구명"),
                                fieldWithPath("result[].administrationName").type(JsonFieldType.STRING).description("행정동명"),
                                fieldWithPath("result[].itemName").type(JsonFieldType.STRING).description("항목명"),
                                fieldWithPath("result[].dataValue").type(JsonFieldType.STRING).description("데이터 값")
                        )
                ))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertThat(JsonPath.<Integer>read(jsonResponse, "$.result[0].id")).isEqualTo(id);
        assertThat(JsonPath.<String>read(jsonResponse, "$.result[0].standardYear")).isEqualTo(standardYear);
        assertThat(JsonPath.<String>read(jsonResponse, "$.result[0].cityCountyDistrictName")).isEqualTo(cityCountyDistrictName);
        assertThat(JsonPath.<String>read(jsonResponse, "$.result[0].administrationName")).isEqualTo(administrationName);
        assertThat(JsonPath.<String>read(jsonResponse, "$.result[0].itemName")).isEqualTo(itemName);
        assertThat(JsonPath.<String>read(jsonResponse, "$.result[0].dataValue")).isEqualTo(dataValue);
        assertThat(JsonPath.<String>read(jsonResponse, "$.result[0].dataValue")).isEqualTo(dataValue);

    }

    @Test
    @DisplayName("GET /districts/{id}")
    void getAdministrativeDistrictOne() throws Exception {
        int testDistrictId = service.findAll().get(0).id().intValue();
        String standardYear = service.findAll().get(0).standardYear();
        String cityCountyDistrictName = service.findAll().get(0).cityCountyDistrictName();
        String administrationName = service.findAll().get(0).administrationName();
        String itemName = service.findAll().get(0).itemName();
        String dataValue = service.findAll().get(0).dataValue();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/districts/{id}", testDistrictId)
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
                                fieldWithPath("result.cityCountyDistrictName").type(JsonFieldType.STRING).description("시군구명"),
                                fieldWithPath("result.administrationName").type(JsonFieldType.STRING).description("행정동명"),
                                fieldWithPath("result.itemName").type(JsonFieldType.STRING).description("항목명"),
                                fieldWithPath("result.dataValue").type(JsonFieldType.STRING).description("데이터 값")
                        )
                ))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertThat(JsonPath.<Integer>read(jsonResponse, "$.result.id")).isEqualTo(testDistrictId);
        assertThat(JsonPath.<String>read(jsonResponse, "$.result.standardYear")).isEqualTo(standardYear);
        assertThat(JsonPath.<String>read(jsonResponse, "$.result.cityCountyDistrictName")).isEqualTo(cityCountyDistrictName);
        assertThat(JsonPath.<String>read(jsonResponse, "$.result.administrationName")).isEqualTo(administrationName);
        assertThat(JsonPath.<String>read(jsonResponse, "$.result.itemName")).isEqualTo(itemName);
        assertThat(JsonPath.<String>read(jsonResponse, "$.result.dataValue")).isEqualTo(dataValue);
    }

    @Test
    @DisplayName("POST /districts/{id}")
    void post() throws Exception {

        AdministrativeDistrictPostDto postDto = new AdministrativeDistrictPostDto(
                "2025", "덕양구", "주교동", "행정동수 (개)", "3322");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/districts")
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
        AdministrativeDistrictResponseDto administrativeDistrictResponseDto = service.findById(id);

        assertThat(administrativeDistrictResponseDto.standardYear()).isEqualTo("2025");
        assertThat(administrativeDistrictResponseDto.cityCountyDistrictName()).isEqualTo("덕양구");
        assertThat(administrativeDistrictResponseDto.administrationName()).isEqualTo("주교동");
        assertThat(administrativeDistrictResponseDto.itemName()).isEqualTo("행정동수 (개)");
        assertThat(administrativeDistrictResponseDto.dataValue()).isEqualTo("3322");
    }

    @Test
    @DisplayName("PUT /districts/{id}")
    void put() throws Exception {

        AdministrativeDistrictResponseDto responseDto = service.findAll().get(0);
        Long id = responseDto.id();

        AdministrativeDistrictPatchDto putDto = AdministrativeDistrictPatchDto.builder()
                .id(id)
                .standardYear("2027")
                .cityCountyDistrictName("일산동구")
                .administrationName("식사동")
                .itemName("면적 (㎢)")
                .dataValue("12345")
                .build();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/districts/{id}", responseDto.id())
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

        AdministrativeDistrictResponseDto afterPut = service.findById(responseDto.id());

        assertThat(afterPut.standardYear()).isEqualTo("2027");
        assertThat(afterPut.cityCountyDistrictName()).isEqualTo("일산동구");
        assertThat(afterPut.administrationName()).isEqualTo("식사동");
        assertThat(afterPut.itemName()).isEqualTo("면적 (㎢)");
        assertThat(afterPut.dataValue()).isEqualTo("12345");
    }



    @Test
    @DisplayName("PATCH /districts/{id}")
    void patch() throws Exception {

        AdministrativeDistrictResponseDto responseDto = service.findAll().get(0);

        Long id = responseDto.id();
        AdministrativeDistrictPatchDto patchDto = AdministrativeDistrictPatchDto.builder()
                .itemName("법정동수 (개)")
                .dataValue("1234")
                .build();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.patch("/districts/{id}", responseDto.id())
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

        AdministrativeDistrictResponseDto afterPatch = service.findById(responseDto.id());

        assertThat(afterPatch.itemName()).isEqualTo("1234");
        assertThat(afterPatch.dataValue()).isEqualTo("1234");

    }

    @Test
    @DisplayName("DELETE /districts/{id}")
    void delete() throws Exception {

        AdministrativeDistrictResponseDto responseDto = service.findAll().get(0);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/districts/{id}",responseDto.id())
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

