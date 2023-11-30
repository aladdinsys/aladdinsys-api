package com.aladdinsys.api.domains.grid.controller;

import com.aladdinsys.api.common.constant.ErrorCode;
import com.aladdinsys.api.common.constant.SuccessCode;
import com.aladdinsys.api.common.exception.CustomException;
import com.aladdinsys.api.domains.grid.dto.GridItemRequestDto;
import com.aladdinsys.api.domains.grid.dto.GridItemResponseDto;
import com.aladdinsys.api.domains.grid.dto.GridRequestDto;
import com.aladdinsys.api.domains.grid.dto.GridResponseDto;
import com.aladdinsys.api.domains.grid.service.GridService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.*;
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
import java.util.Set;

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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
class GridControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    EntityManager em;

    @Autowired
    GridService service;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {

        objectMapper = new ObjectMapper();

//        service.save(new GridRequestDto("프리셋 1", 10, 10, null));
        service.save(new GridRequestDto("프리셋 2", 10, 10, Set.of(GridItemRequestDto.of(1, 1, 0, 0, 1L), GridItemRequestDto.of(1, 1, 1, 1, 1L))));

        em.clear();
    }

    @Test
    @DisplayName("GET /grids")
    void getGrids() throws Exception {
        List<GridResponseDto> grids = service.findAll();
        GridResponseDto dto = grids.get(0);

        int id = dto.id().intValue();
        String name = dto.name();
        Integer cols = dto.cols();
        Integer rows = dto.rows();
        Set<GridItemResponseDto> items = dto.items();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/grids")
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
                                fieldWithPath("result[].rows").type(JsonFieldType.NUMBER).description("행"),
                                fieldWithPath("result[].cols").type(JsonFieldType.NUMBER).description("열"),
                                fieldWithPath("result[].items").type(JsonFieldType.ARRAY).description("Items").optional(),
                                fieldWithPath("result[].items[].id").type(JsonFieldType.NUMBER).description("Item 아이디"),
                                fieldWithPath("result[].items[].width").type(JsonFieldType.NUMBER).description("넓이"),
                                fieldWithPath("result[].items[].height").type(JsonFieldType.NUMBER).description("높이"),
                                fieldWithPath("result[].items[].x").type(JsonFieldType.NUMBER).description("X좌표"),
                                fieldWithPath("result[].items[].y").type(JsonFieldType.NUMBER).description("Y좌표"),
                                fieldWithPath("result[].items[].contentId").type(JsonFieldType.NUMBER).description("콘텐트 아이디")
                        )
                ))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertThat(JsonPath.<Integer>read(jsonResponse, "$.result[0].id")).isEqualTo(id);
        assertThat(JsonPath.<String>read(jsonResponse, "$.result[0].name")).isEqualTo(name);
        assertThat(JsonPath.<Integer>read(jsonResponse, "$.result[0].rows")).isEqualTo(rows);
        assertThat(JsonPath.<Integer>read(jsonResponse, "$.result[0].cols")).isEqualTo(cols);
    }

    @Test
    @Order(1)
    @DisplayName("GET /grids/{id}")
    void getGrid() throws Exception {
        GridResponseDto dto = service.findById(1L);

        int id = dto.id().intValue();
        String name = dto.name();
        Integer cols = dto.cols();
        Integer rows = dto.rows();
        Set<GridItemResponseDto> items = dto.items();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/grids/{id}", id)
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
                                fieldWithPath("result.rows").type(JsonFieldType.NUMBER).description("행"),
                                fieldWithPath("result.cols").type(JsonFieldType.NUMBER).description("열"),
                                fieldWithPath("result.items").type(JsonFieldType.ARRAY).description("Items").optional(),
                                fieldWithPath("result.items[].id").type(JsonFieldType.NUMBER).description("Item 아이디"),
                                fieldWithPath("result.items[].width").type(JsonFieldType.NUMBER).description("넓이"),
                                fieldWithPath("result.items[].height").type(JsonFieldType.NUMBER).description("높이"),
                                fieldWithPath("result.items[].x").type(JsonFieldType.NUMBER).description("X좌표"),
                                fieldWithPath("result.items[].y").type(JsonFieldType.NUMBER).description("Y좌표"),
                                fieldWithPath("result.items[].contentId").type(JsonFieldType.NUMBER).description("콘텐트 아이디")
                        )
                ))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertThat(JsonPath.<Integer>read(jsonResponse, "$.result.id")).isEqualTo(id);
        assertThat(JsonPath.<String>read(jsonResponse, "$.result.name")).isEqualTo(name);
        assertThat(JsonPath.<Integer>read(jsonResponse, "$.result.rows")).isEqualTo(rows);
        assertThat(JsonPath.<Integer>read(jsonResponse, "$.result.cols")).isEqualTo(cols);
    }

    @Test
    @DisplayName("POST /grids/{id}")
    void post() throws Exception {

        GridRequestDto postDto = new GridRequestDto("새로운 Preset", 10, 10, Set.of(
                                                                                                GridItemRequestDto.of(1, 1, 0, 0, 1L),
                                                                                                GridItemRequestDto.of(5, 2, 1, 1, 1L)
                                                                                        ));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/grids")
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
        GridResponseDto responseDto = service.findById(id);

        assertThat(responseDto.name()).isEqualTo("새로운 Preset");
        assertThat(responseDto.cols()).isEqualTo(10);
        assertThat(responseDto.rows()).isEqualTo(10);
    }

    @Test
    @DisplayName("PUT /grids/{id}")
    void put() throws Exception {

        GridResponseDto responseDto = service.findAll().get(0);
        Long id = responseDto.id();

        GridRequestDto putDto = new GridRequestDto("Put 테스트 Preset", 20, 20, null);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/grids/{id}", id)
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

        GridResponseDto afterPut = service.findById(id);

        assertThat(afterPut.name()).isEqualTo("Put 테스트 Preset");
        assertThat(afterPut.cols()).isEqualTo(20);
        assertThat(afterPut.rows()).isEqualTo(20);
    }

    @Test
    @DisplayName("DELETE /grids/{id}")
    void delete() throws Exception {

        GridResponseDto responseDto = service.findAll().get(0);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/grids/{id}", responseDto.id())
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