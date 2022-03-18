package com.kodilla.car_service.mapper;

import com.kodilla.car_service.domain.Cost;
import com.kodilla.car_service.dto.CostDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CostMapper {

    public CostDto mapToCostDto(final Cost cost) {
        return new CostDto(
                cost.getId(),
                cost.getLabourCost(),
                cost.getPartsCost(),
                cost.getTotalCost()
        );
    }

    public List<CostDto> mapToCostDtoList(final List<Cost> costList) {
        return costList.stream()
                .map(this::mapToCostDto)
                .collect(Collectors.toList());
    }
}
