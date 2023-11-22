package com.aladdinsys.api.domains.admin_district.controller;

import com.aladdinsys.api.common.constant.SuccessCode;
import com.aladdinsys.api.common.response.DataResponseBody;
import com.aladdinsys.api.common.response.ResponseBody;
import com.aladdinsys.api.domains.admin_district.dto.AdministrativeDistrictPatchDto;
import com.aladdinsys.api.domains.admin_district.dto.AdministrativeDistrictPostDto;
import com.aladdinsys.api.domains.admin_district.dto.AdministrativeDistrictResponseDto;
import com.aladdinsys.api.domains.admin_district.service.AdministrativeDistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.aladdinsys.api.common.constant.SuccessCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/districts")
public class AdministrativeDistrictController {


   private final AdministrativeDistrictService service;

    @GetMapping("/{id}")
    public DataResponseBody<AdministrativeDistrictResponseDto> getDistrict(@PathVariable Long id) {
        return DataResponseBody.of(service.findById(id));
    }

    @GetMapping
    public DataResponseBody<List<AdministrativeDistrictResponseDto>> getDistricts() {
        return DataResponseBody.of(service.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DataResponseBody<Long> post(@Valid @RequestBody AdministrativeDistrictPostDto dto) {
        return DataResponseBody.of(SuccessCode.SUCCESS_CREATE, service.save(dto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseBody delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseBody.of(SUCCESS_DELETE);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseBody put(@PathVariable Long id, @Valid @RequestBody AdministrativeDistrictPostDto dto) {
        service.put(id, dto);
        return ResponseBody.of(SUCCESS_PUT);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseBody patch(@PathVariable Long id, @Valid @RequestBody AdministrativeDistrictPatchDto dto) {
        service.patch(id, dto);
        return ResponseBody.of(SUCCESS_PATCH);
    }



}
