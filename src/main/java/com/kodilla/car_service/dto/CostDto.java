package com.kodilla.car_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CostDto {

    private Long id;

    private BigDecimal partsCost;

    private BigDecimal labourCost;

    private BigDecimal totalCost;
}
