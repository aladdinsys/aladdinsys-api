/* (C) 2023 */
package com.aladdinsys.api.domains.human_resource.controller;

import static com.aladdinsys.api.common.constant.SuccessCode.*;

import com.aladdinsys.api.common.response.DataResponseBody;
import com.aladdinsys.api.common.response.ResponseBody;
import com.aladdinsys.api.domains.human_resource.dto.EmployeePatchDto;
import com.aladdinsys.api.domains.human_resource.dto.EmployeePostDto;
import com.aladdinsys.api.domains.human_resource.dto.EmployeeResponseDto;
import com.aladdinsys.api.domains.human_resource.service.EmployeeService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
  public DataResponseBody<Long> post(@Valid @RequestBody EmployeePostDto dto) {
    return DataResponseBody.of(SUCCESS_CREATE, service.save(dto));
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public ResponseBody put(@PathVariable Long id, @Valid @RequestBody EmployeePostDto dto) {
    service.put(id, dto);
    return ResponseBody.of(SUCCESS_PUT);
  }

  @PatchMapping("/{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public ResponseBody patch(@PathVariable Long id, @Valid @RequestBody EmployeePatchDto dto) {
    service.patch(id, dto);
    return ResponseBody.of(SUCCESS_PATCH);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseBody delete(@PathVariable Long id) {
    service.deleteById(id);
    return ResponseBody.of(SUCCESS_DELETE);
  }
}
