package com.aladdinsys.api.exercise.service;

import com.aladdinsys.api.common.constant.ErrorCode;
import com.aladdinsys.api.common.exception.CustomException;
import com.aladdinsys.api.exercise.dto.ExercisePatchDto;
import com.aladdinsys.api.exercise.dto.ExercisePostDto;
import com.aladdinsys.api.exercise.dto.ExerciseResponseDto;
import com.aladdinsys.api.exercise.entity.ExerciseEntity;
import com.aladdinsys.api.exercise.repository.ExerciseRepository;
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
                .standardYear(dto.standardYear())
                .largeExerciseFacility(dto.largeExerciseFacility())
                .middleExerciseFacility(dto.middleExerciseFacility())
                .itemName(dto.itemName())
                .dataValue(dto.dataValue())
                .build();

        ExerciseEntity saved = repository.save(entity);
        return saved.getId();
    }

    @Transactional(readOnly = true)
    public ExerciseResponseDto findById(final Long id) {
        ExerciseEntity entity = repository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        return ExerciseResponseDto.of(entity.getId(), entity.getStandardYear(), entity.getLargeExerciseFacility(), entity.getMiddleExerciseFacility(), entity.getItemName(), entity.getDataValue());
    }

    @Transactional
    public List<ExerciseResponseDto> findAll(){
        List<ExerciseEntity> entities = repository.findAll();

        return entities.stream()
                .map(entity -> ExerciseResponseDto.of(entity.getId(), entity.getStandardYear(), entity.getLargeExerciseFacility(), entity.getMiddleExerciseFacility(), entity.getItemName(), entity.getDataValue()))
                .toList();
    }

    @Transactional
    public void updateExercise(final Long id, final ExercisePostDto dto){
        ExerciseEntity fetch = repository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        fetch.update(dto.standardYear(), dto.largeExerciseFacility(), dto.middleExerciseFacility(), dto.itemName(), dto.dataValue());
    }

    @Transactional
    public void patchExercise(final Long id, final ExercisePatchDto dto){
        ExerciseEntity fetch = repository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        Optional.ofNullable(dto.standardYear()).ifPresent(fetch::patchStdYy);
        Optional.ofNullable(dto.largeExerciseFacility()).ifPresent(fetch::patchLargeExerciseFacility);
        Optional.ofNullable(dto.middleExerciseFacility()).ifPresent(fetch::patchMiddleExerciseFacility);
        Optional.ofNullable(dto.itemName()).ifPresent(fetch::patchItemNm);
        Optional.ofNullable(dto.dataValue()).ifPresent(fetch::patchDataValue);
    }

    @Transactional
    public void deleteExercise(final Long id){
        repository.deleteById(id);
    }

}
