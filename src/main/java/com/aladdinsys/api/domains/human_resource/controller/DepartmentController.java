/* (C) 2023 */
package com.aladdinsys.api.domains.human_resource.controller;

import static com.aladdinsys.api.common.constant.SuccessCode.*;

import com.aladdinsys.api.common.constant.SuccessCode;
import com.aladdinsys.api.common.response.DataResponseBody;
import com.aladdinsys.api.common.response.ResponseBody;
import com.aladdinsys.api.domains.human_resource.dto.DepartmentRequestDto;
import com.aladdinsys.api.domains.human_resource.dto.DepartmentResponseDto;
import com.aladdinsys.api.domains.human_resource.dto.EmployeeResponseDto;
import com.aladdinsys.api.domains.human_resource.service.DepartmentService;
import com.aladdinsys.api.domains.human_resource.service.EmployeeService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/departments")
public class DepartmentController {

  private final DepartmentService service;

  private final EmployeeService employeeService;

  @GetMapping
  public DataResponseBody<List<DepartmentResponseDto>> getDepartments() {
    return DataResponseBody.of(service.findAll());
  }

  @GetMapping("/{id}")
  public DataResponseBody<DepartmentResponseDto> getDepartment(@PathVariable Long id) {
    return DataResponseBody.of(service.findById(id));
  }

  @GetMapping("/{id}/employees")
  public DataResponseBody<List<EmployeeResponseDto>> getEmployeesByDepartment(
      @PathVariable Long id) {
    return DataResponseBody.of(employeeService.findByDepartment(id));
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
