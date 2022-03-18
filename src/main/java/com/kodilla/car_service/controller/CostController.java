package com.kodilla.car_service.controller;

import com.kodilla.car_service.dto.CostDto;
import com.kodilla.car_service.mapper.CostMapper;
import com.kodilla.car_service.service.CostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1")
public class CostController {

    @Autowired
    private CostMapper costMapper;

    @Autowired
    private CostService costService;

    @RequestMapping(method = RequestMethod.GET, value = "/costs")
    public List<CostDto> getCosts() {
        return costMapper.mapToCostDtoList(costService.getCosts());
    }
}