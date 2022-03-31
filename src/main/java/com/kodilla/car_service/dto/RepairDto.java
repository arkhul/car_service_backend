package com.kodilla.car_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RepairDto {

    private Long id;

    private String damageDescription;

    private String repairStatus;

    private LocalDate admissionDate;

    private LocalDate releaseDate;

    private String repairDescription;

    private BigDecimal repairTimeInManHours;

    private String car;

    private String serviceTechnician;

    private BigDecimal cost;

    public void setRepairStatus(final String repairStatus) {
        this.repairStatus = repairStatus;
    }
}
