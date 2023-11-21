package com.aladdinsys.api.testjpa.controller;

import com.aladdinsys.api.testjpa.dto.SampleRequestDto;
import com.aladdinsys.api.testjpa.repository.SampleRepository;
import com.aladdinsys.api.testjpa.entity.SampleEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sample")
public class SampleController {

    @Autowired
    public SampleRepository sampleRepository;

    // 전체 조회
    @GetMapping
    public List<SampleEntity> getAll(){
        return sampleRepository.findAll();
    }

    // 조회 by ID
    @GetMapping("/{data_id}")
    public Optional<SampleEntity> getFindId(@PathVariable Long data_id){
        return sampleRepository.findById(data_id);
    }

    // 등록
    @PostMapping
    public SampleEntity sampleCreate(@RequestParam String std_yy,
                                     @RequestParam String phsrtn_fclty_lclas_nm,
                                     @RequestParam String phsrtn_fclty_mclas_nm,
                                     @RequestParam String iem_nm,
                                     @RequestParam String data_val) {
        SampleEntity sampleEntity = new SampleEntity();

        sampleEntity.setStd_yy(std_yy);
        sampleEntity.setPhsrtn_fclty_lclas_nm(phsrtn_fclty_lclas_nm);
        sampleEntity.setPhsrtn_fclty_mclas_nm(phsrtn_fclty_mclas_nm);
        sampleEntity.setIem_nm(iem_nm);
        sampleEntity.setData_val(data_val);

        return sampleRepository.save(sampleEntity);
    }

//    등록
//    @PostMapping
//    public SampleEntity sampleCreate(@Valid @RequestBody SampleRequestDto dto) {
//        SampleEntity sampleEntity = new SampleEntity();
//
//        sampleEntity.setStd_yy(dto.std_yy());
//        sampleEntity.setPhsrtn_fclty_lclas_nm(dto.phsrtn_fclty_lclas_nm());
//        sampleEntity.setPhsrtn_fclty_mclas_nm(dto.phsrtn_fclty_mclas_nm());
//        sampleEntity.setIem_nm(dto.iem_nm());
//        sampleEntity.setData_val(dto.data_val());
//
//        return sampleRepository.save(sampleEntity);
//    }

    // 수정
    @PutMapping("/{data_id}")
    public SampleEntity sampleUpdate(@PathVariable Long data_id,
                                     @RequestParam String std_yy,
                                     @RequestParam String phsrtn_fclty_lclas_nm,
                                     @RequestParam String phsrtn_fclty_mclas_nm,
                                     @RequestParam String iem_nm,
                                     @RequestParam String data_val) {

        if(sampleRepository.existsById(data_id)){
            SampleEntity sampleEntity = new SampleEntity();

            sampleEntity.setData_id(data_id);
            sampleEntity.setStd_yy(std_yy);
            sampleEntity.setPhsrtn_fclty_lclas_nm(phsrtn_fclty_lclas_nm);
            sampleEntity.setPhsrtn_fclty_mclas_nm(phsrtn_fclty_mclas_nm);
            sampleEntity.setIem_nm(iem_nm);
            sampleEntity.setData_val(data_val);

            return sampleRepository.save(sampleEntity);
        } else {
            return null;
        }
    }

    // 삭제
    @DeleteMapping("/{data_id}")
    public void sampleDelete(@PathVariable Long data_id){
        sampleRepository.deleteById(data_id);
    }
}
