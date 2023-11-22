package com.aladdinsys.api.exercise_resource.service;

import com.aladdinsys.api.common.constant.ErrorCode;
import com.aladdinsys.api.common.exception.CustomException;
import com.aladdinsys.api.exercise_resource.dto.ExercisePatchDto;
import com.aladdinsys.api.exercise_resource.dto.ExercisePostDto;
import com.aladdinsys.api.exercise_resource.dto.ExerciseResponseDto;
import com.aladdinsys.api.exercise_resource.entity.ExerciseEntity;
import com.aladdinsys.api.exercise_resource.repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseRepository repository;

    @Transactional
    public Long save(final ExercisePostDto dto){
        ExerciseEntity entity = ExerciseEntity.builder()
                .stdYy(dto.stdYy())
                .phsrtnFcltyLclasNm(dto.phsrtnFcltyLclasNm())
                .phsrtnFcltyMclasNm(dto.phsrtnFcltyMclasNm())
                .iemNm(dto.iemNm())
                .dataVal(dto.dataVal())
                .build();

        ExerciseEntity saved = repository.save(entity);
        return saved.getDataId();
    }

    @Transactional(readOnly = true)
    public ExerciseResponseDto findById(final Long dataId) {
        ExerciseEntity entity = repository.findById(dataId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        return ExerciseResponseDto.of(entity.getDataId(), entity.getStdYy(), entity.getPhsrtnFcltyLclasNm(), entity.getPhsrtnFcltyMclasNm(), entity.getIemNm(), entity.getDataVal());
    }

    @Transactional
    public List<ExerciseResponseDto> findAll(){
        List<ExerciseEntity> entities = repository.findAll();

        return entities.stream()
                .map(entity -> ExerciseResponseDto.of(entity.getDataId(), entity.getStdYy(), entity.getPhsrtnFcltyLclasNm(), entity.getPhsrtnFcltyLclasNm(), entity.getIemNm(), entity.getDataVal()))
                .toList();
    }

    @Transactional
    public void updateExercise(final Long dataId, final ExercisePostDto dto){
        ExerciseEntity fetch = repository.findById(dataId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        fetch.update(dto.stdYy(), dto.phsrtnFcltyLclasNm(), dto.phsrtnFcltyMclasNm(), dto.iemNm(), dto.dataVal());
    }

    @Transactional
    public void patchExercise(final Long dataId, final ExercisePatchDto dto){
        ExerciseEntity fetch = repository.findById(dataId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        Optional.ofNullable(dto.stdYy()).ifPresent(fetch::patchStdYy);
        Optional.ofNullable(dto.phsrtnFcltyLclasNm()).ifPresent(fetch::patchPhsrtnFcltyLclasNm);
        Optional.ofNullable(dto.phsrtnFcltyMclasNm()).ifPresent(fetch::patchPhsrtnFcltyMclasNm);
        Optional.ofNullable(dto.iemNm()).ifPresent(fetch::patchIemNm);
        Optional.ofNullable(dto.dataVal()).ifPresent(fetch::patchDataVal);
    }

    @Transactional
    public void deleteExercise(final Long dataId){
        repository.deleteById(dataId);
    }

}
