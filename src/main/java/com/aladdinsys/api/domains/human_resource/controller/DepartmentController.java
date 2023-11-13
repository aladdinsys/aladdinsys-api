package com.aladdinsys.api.domains.human_resource.controller;

import com.aladdinsys.api.common.constant.SuccessCode;
import com.aladdinsys.api.common.response.DataResponseBody;
import com.aladdinsys.api.common.response.ResponseBody;
import com.aladdinsys.api.domains.human_resource.dto.DepartmentRequestDto;
import com.aladdinsys.api.domains.human_resource.dto.DepartmentResponseDto;
import com.aladdinsys.api.domains.human_resource.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.aladdinsys.api.common.constant.SuccessCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService service;

    @GetMapping
    public DataResponseBody<List<DepartmentResponseDto>> getDepartments() {
        return DataResponseBody.of(service.findAll());
    }

    @GetMapping("/{id}")
    public DataResponseBody<DepartmentResponseDto> getDepartment(@PathVariable Long id) {
        return DataResponseBody.of(service.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DataResponseBody<Long> post(@Valid @RequestBody DepartmentRequestDto dto) {
        return DataResponseBody.of(SuccessCode.SUCCESS_CREATE, service.save(dto));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseBody put(@PathVariable Long id, @Valid @RequestBody DepartmentRequestDto dto) {
        service.updateById(id, dto);
        return ResponseBody.of(SUCCESS_PUT);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseBody delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseBody.of(SUCCESS_DELETE);
    }
}