package com.aladdinsys.api.domains.human_resource.controller;

import com.aladdinsys.api.common.response.ResponseBody;
import com.aladdinsys.api.common.response.DataResponseBody;
import com.aladdinsys.api.domains.human_resource.dto.EmployeeRequestDto;
import com.aladdinsys.api.domains.human_resource.dto.EmployeeResponseDto;
import com.aladdinsys.api.domains.human_resource.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.aladdinsys.api.common.constant.SuccessCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService service;

    @GetMapping
    public DataResponseBody<List<EmployeeResponseDto>> getEmployees() {
        return DataResponseBody.of(service.findAll());
    }

    @GetMapping("/{id}")
    public DataResponseBody<EmployeeResponseDto> getEmployee(@PathVariable Long id) {
        return DataResponseBody.of(service.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DataResponseBody<Long> post(@Valid @RequestBody EmployeeRequestDto dto) {
        return DataResponseBody.of(service.save(dto));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseBody put(@PathVariable Long id, @Valid @RequestBody EmployeeRequestDto dto) {
        service.updateById(id, dto);
        return ResponseBody.of(SUCCESS_PUT);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseBody patch(@PathVariable Long id, @Valid @RequestBody EmployeeRequestDto dto) {
        service.updateById(id, dto);
        return ResponseBody.of(SUCCESS_PATCH);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseBody delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseBody.of(SUCCESS_DELETE);
    }
    
}
