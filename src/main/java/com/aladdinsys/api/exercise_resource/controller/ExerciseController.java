package com.aladdinsys.api.exercise_resource.controller;

import com.aladdinsys.api.common.response.DataResponseBody;
import com.aladdinsys.api.common.response.ResponseBody;
import com.aladdinsys.api.exercise_resource.dto.ExercisePatchDto;
import com.aladdinsys.api.exercise_resource.dto.ExercisePostDto;
import com.aladdinsys.api.exercise_resource.dto.ExerciseResponseDto;
import com.aladdinsys.api.exercise_resource.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.aladdinsys.api.common.constant.SuccessCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exercise")
public class ExerciseController {

    private final ExerciseService service;

    //조회
    @GetMapping
    public DataResponseBody<List<ExerciseResponseDto>> getExercise(){
        return DataResponseBody.of(service.findAll());
    }

    // 조회 by ID
    @GetMapping("/{Id}")
    public DataResponseBody<ExerciseResponseDto> getExerciseId(@PathVariable Long Id) {
        return DataResponseBody.of(service.findById(Id));
    }

    // 등록
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DataResponseBody<Long> createExercise(@Valid @RequestBody ExercisePostDto dto) {
        return DataResponseBody.of(SUCCESS_CREATE, service.save(dto));
    }

    // 수정
    @PutMapping("/{Id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseBody putExercise(@PathVariable Long Id, @Valid @RequestBody ExercisePostDto dto) {
        service.updateExercise(Id, dto);
        return ResponseBody.of(SUCCESS_PUT);
    }

    // 부분수정
    @PatchMapping("/{Id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseBody patchExercise(@PathVariable Long Id, @Valid @RequestBody ExercisePatchDto dto) {
        service.patchExercise(Id, dto);
        return ResponseBody.of(SUCCESS_PATCH);
    }

    // 삭제
    @DeleteMapping("/{Id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseBody deleteExercise(@PathVariable Long Id){
        service.deleteExercise(Id);
        return ResponseBody.of(SUCCESS_DELETE);
    }
}
