package com.aladdinsys.api.domains.grid.controller;

import com.aladdinsys.api.common.constant.SuccessCode;
import com.aladdinsys.api.common.response.DataResponseBody;
import com.aladdinsys.api.common.response.ResponseBody;
import com.aladdinsys.api.domains.grid.dto.GridRequestDto;
import com.aladdinsys.api.domains.grid.dto.GridResponseDto;
import com.aladdinsys.api.domains.grid.service.GridService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.aladdinsys.api.common.constant.SuccessCode.SUCCESS_DELETE;
import static com.aladdinsys.api.common.constant.SuccessCode.SUCCESS_PUT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/grids")
public class GridController {

    private final GridService service;

    @GetMapping
    public DataResponseBody<List<GridResponseDto>> getGrids() {
        return DataResponseBody.of(service.findAll());
    }

    @GetMapping("/{id}")
    public DataResponseBody<GridResponseDto> getGrid(@PathVariable Long id) {
        return DataResponseBody.of(service.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DataResponseBody<Long> post(@Valid @RequestBody GridRequestDto dto) {
        return DataResponseBody.of(SuccessCode.SUCCESS_CREATE, service.save(dto));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseBody put(@PathVariable Long id, @Valid @RequestBody GridRequestDto dto) {
        service.update(id, dto);
        return ResponseBody.of(SUCCESS_PUT);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseBody delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseBody.of(SUCCESS_DELETE);
    }
}