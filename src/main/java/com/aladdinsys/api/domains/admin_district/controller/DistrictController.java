package com.aladdinsys.api.domains.admin_district.controller;

import com.aladdinsys.api.common.response.DataResponseBody;
import com.aladdinsys.api.domains.admin_district.dto.DistrictResponseDto;
import com.aladdinsys.api.domains.admin_district.service.DistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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


}
