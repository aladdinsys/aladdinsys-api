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
                .largeExerciseFacility(dto.largeExerciseFacility())
                .middleExerciseFacility(dto.middleExerciseFacility())
                .itemNm(dto.itemNm())
                .dataValue(dto.dataValue())
                .build();

        ExerciseEntity saved = repository.save(entity);
        return saved.Id();
    }

    @Transactional(readOnly = true)
    public ExerciseResponseDto findById(final Long Id) {
        ExerciseEntity entity = repository.findById(Id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        return ExerciseResponseDto.of(entity.getId(), entity.getStdYy(), entity.getLargeExerciseFacility(), entity.getMiddleExerciseFacility(), entity.getItemNm(), entity.getDataValue());
    }

    @Transactional
    public List<ExerciseResponseDto> findAll(){
        List<ExerciseEntity> entities = repository.findAll();

        return entities.stream()
                .map(entity -> ExerciseResponseDto.of(entity.getId(), entity.getStdYy(), entity.getLargeExerciseFacility(), entity.getMiddleExerciseFacility(), entity.getItemNm(), entity.getDataValue()))
                .toList();
    }

    @Transactional
    public void updateExercise(final Long Id, final ExercisePostDto dto){
        ExerciseEntity fetch = repository.findById(Id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        fetch.update(dto.stdYy(), dto.largeExerciseFacility(), dto.middleExerciseFacility(), dto.itemNm(), dto.dataValue());
    }

    @Transactional
    public void patchExercise(final Long Id, final ExercisePatchDto dto){
        ExerciseEntity fetch = repository.findById(Id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        Optional.ofNullable(dto.stdYy()).ifPresent(fetch::patchStdYy);
        Optional.ofNullable(dto.largeExerciseFacility()).ifPresent(fetch::patchLargeExerciseFacility);
        Optional.ofNullable(dto.middleExerciseFacility()).ifPresent(fetch::patchMiddleExerciseFacility);
        Optional.ofNullable(dto.itemNm()).ifPresent(fetch::patchItemNm);
        Optional.ofNullable(dto.dataValue()).ifPresent(fetch::patchDataValue);
    }

    @Transactional
    public void deleteExercise(final Long Id){
        repository.deleteById(Id);
    }

}
