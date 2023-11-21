package com.aladdinsys.api.domains.admin_district.controller;

import com.aladdinsys.api.common.constant.SuccessCode;
import com.aladdinsys.api.common.response.DataResponseBody;
import com.aladdinsys.api.common.response.ResponseBody;
import com.aladdinsys.api.domains.admin_district.dto.DistrictPostDto;
import com.aladdinsys.api.domains.admin_district.dto.DistrictResponseDto;
import com.aladdinsys.api.domains.admin_district.service.DistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.aladdinsys.api.common.constant.SuccessCode.SUCCESS_DELETE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/district")
public class DistrictController {


   private final DistrictService service;

    @GetMapping("/{id}")
    public DataResponseBody<DistrictResponseDto> getDistrict(@PathVariable Long id) {
        return DataResponseBody.of(service.findById(id));
    }

    @GetMapping
    public DataResponseBody<List<DistrictResponseDto>> getDistricts() {
        return DataResponseBody.of(service.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DataResponseBody<Long> post(@Valid @RequestBody DistrictPostDto dto) {
        return DataResponseBody.of(SuccessCode.SUCCESS_CREATE, service.save(dto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseBody delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseBody.of(SUCCESS_DELETE);
    }


}
