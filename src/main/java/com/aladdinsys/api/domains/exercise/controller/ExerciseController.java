/* (C) 2023 */
package com.aladdinsys.api.domains.exercise.controller;

import static com.aladdinsys.api.common.constant.SuccessCode.*;

import com.aladdinsys.api.common.response.DataResponseBody;
import com.aladdinsys.api.common.response.ResponseBody;
import com.aladdinsys.api.domains.exercise.dto.ExercisePatchDto;
import com.aladdinsys.api.domains.exercise.dto.ExercisePostDto;
import com.aladdinsys.api.domains.exercise.dto.ExerciseResponseDto;
import com.aladdinsys.api.domains.exercise.service.ExerciseService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exercise")
public class ExerciseController {

  private final ExerciseService service;

  // 조회
  @GetMapping
  public DataResponseBody<List<ExerciseResponseDto>> getExercise() {
    return DataResponseBody.of(service.findAll());
  }

  // 조회 by ID
  @GetMapping("/{id}")
  public DataResponseBody<ExerciseResponseDto> getExerciseId(@PathVariable Long id) {
    return DataResponseBody.of(service.findById(id));
  }

  // 등록
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public DataResponseBody<Long> createExercise(@Valid @RequestBody ExercisePostDto dto) {
    return DataResponseBody.of(SUCCESS_CREATE, service.save(dto));
  }

  // 수정
  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public ResponseBody putExercise(@PathVariable Long id, @Valid @RequestBody ExercisePostDto dto) {
    service.updateExercise(id, dto);
    return ResponseBody.of(SUCCESS_PUT);
  }

  // 부분수정
  @PatchMapping("/{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public ResponseBody patchExercise(
      @PathVariable Long id, @Valid @RequestBody ExercisePatchDto dto) {
    service.patchExercise(id, dto);
    return ResponseBody.of(SUCCESS_PATCH);
  }

  // 삭제
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseBody deleteExercise(@PathVariable Long id) {
    service.deleteExercise(id);
    return ResponseBody.of(SUCCESS_DELETE);
  }
}
