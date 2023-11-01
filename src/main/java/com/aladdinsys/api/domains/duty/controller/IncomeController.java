package com.aladdinsys.api.domains.duty.controller;

import com.aladdinsys.api.common.response.DataResponseBody;
import com.aladdinsys.api.common.response.ResponseBody;
import com.aladdinsys.api.domains.duty.dto.IncomeRequestDto;
import com.aladdinsys.api.domains.duty.dto.IncomeResponseDto;
import com.aladdinsys.api.domains.duty.service.IncomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.aladdinsys.api.common.constant.SuccessCode.SUCCESS_DELETE;
import static com.aladdinsys.api.common.constant.SuccessCode.SUCCESS_PATCH;

@RestController
@RequiredArgsConstructor
@RequestMapping("/incomes")
public class IncomeController {

    private final IncomeService service;

    @GetMapping
    public DataResponseBody<List<IncomeResponseDto>> getIncomes() {
        return DataResponseBody.of(service.findAll());
    }

    @GetMapping("/{id}")
    public DataResponseBody<IncomeResponseDto> getIncome(@PathVariable Long id) {
        return DataResponseBody.of(service.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DataResponseBody<Long> postBuilding(@Valid @RequestBody IncomeRequestDto dto) {
        return DataResponseBody.of(service.save(dto));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseBody put(@PathVariable Long id, @Valid @RequestBody IncomeRequestDto dto) {

        service.updateById(id, dto);

        return ResponseBody.of(SUCCESS_PATCH);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseBody delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseBody.of(SUCCESS_DELETE);
    }

}
