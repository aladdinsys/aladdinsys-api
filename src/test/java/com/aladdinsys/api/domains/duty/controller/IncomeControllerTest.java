package com.aladdinsys.api.domains.duty.controller;

import com.aladdinsys.api.domains.duty.dto.IncomeResponseDto;
import com.aladdinsys.api.domains.duty.service.IncomeService;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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

import javax.persistence.EntityManager;
import java.util.List;

import static com.aladdinsys.api.ApiDocumentUtils.getDocumentRequest;
import static com.aladdinsys.api.ApiDocumentUtils.getDocumentResponse;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
class IncomeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    EntityManager em;

    @InjectMocks
    IncomeController incomeController;

    @Autowired
    IncomeService incomeService;

    @Test
    @DisplayName("GET /Incomes - success")
    void getIncomes() throws Exception {


        List<IncomeResponseDto> incomes = incomeService.findAll();
        IncomeResponseDto dto = incomes.get(0);

        int id = Math.toIntExact(dto.id());
        String name = dto.name();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/incomes")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.data").exists())
                        .andDo(document("{class-name}/{method-name}",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                responseFields(
                                        fieldWithPath("timestamp").type(JsonFieldType.STRING).description("시간"),
                                        fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"),
                                        fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("아이디"),
                                        fieldWithPath("data[].name").type(JsonFieldType.STRING).description("이름")
                                )
                        ))
                        .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();

        assertThat(JsonPath.<List<Object>>read(jsonResponse, "$.data")).hasSize(2);
        assertThat(JsonPath.<Integer>read(jsonResponse, "$.data[0].id")).isEqualTo(id);
        assertThat(JsonPath.<String>read(jsonResponse, "$.data[0].name")).isEqualTo(name);

    }

    @Test
    void getIncome() {
    }

    @Test
    void postBuilding() {
    }

    @Test
    void put() {
    }

    @Test
    void delete() {
    }
}