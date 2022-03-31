package com.kodilla.car_service.mapper;

import com.kodilla.car_service.domain.Cost;
import com.kodilla.car_service.dto.CostDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CostMapperTest {

    private final CostMapper costMapper = new CostMapper();

    @Test
    void mapToCostDtoTest() {
        // Given
        Cost cost = new Cost(new BigDecimal("111"), new BigDecimal("222"),
                new BigDecimal("333"));

        // When
        CostDto costDto = costMapper.mapToCostDto(cost);

        // Then
        assertEquals(new BigDecimal("222"), costDto.getPartsCost());
        assertEquals(new BigDecimal("333"), costDto.getTotalCost());
    }

    @Test
    void mapToCostDtoListTest() {
        // Given
        Cost cost1 = new Cost(new BigDecimal("1"), new BigDecimal("11"),
                new BigDecimal("111"));
        Cost cost2 = new Cost(new BigDecimal("2"), new BigDecimal("22"),
                new BigDecimal("222"));
        List<Cost> costList = Arrays.asList(cost1, cost2);

        // When
        List<CostDto> costDtoList = costMapper.mapToCostDtoList(costList);

        // Then
        assertEquals(2, costDtoList.size());
        assertEquals(new BigDecimal("111"), costDtoList.get(0).getTotalCost());
        assertEquals(new BigDecimal("222"), costDtoList.get(1).getTotalCost());
    }
}