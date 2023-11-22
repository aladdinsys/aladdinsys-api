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
    @GetMapping("/{dataId}")
    public DataResponseBody<ExerciseResponseDto> getExerciseId(@PathVariable Long dataId) {
        return DataResponseBody.of(service.findById(dataId));
    }

    // 등록
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DataResponseBody<Long> createExercise(@Valid @RequestBody ExercisePostDto dto) {
        return DataResponseBody.of(SUCCESS_CREATE, service.save(dto));
    }

    // 수정
    @PutMapping("/{dataId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseBody putExercise(@PathVariable Long dataId, @Valid @RequestBody ExercisePostDto dto) {
        service.updateExercise(dataId, dto);
        return ResponseBody.of(SUCCESS_PUT);
    }

    // 부분수정
    @PatchMapping("/{dataId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseBody patchExercise(@PathVariable Long dataId, @Valid @RequestBody ExercisePatchDto dto) {
        service.patchExercise(dataId, dto);
        return ResponseBody.of(SUCCESS_PATCH);
    }

    // 삭제
    @DeleteMapping("/{dataId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseBody deleteExercise(@PathVariable Long dataId){
        service.deleteExercise(dataId);
        return ResponseBody.of(SUCCESS_DELETE);
    }
}
