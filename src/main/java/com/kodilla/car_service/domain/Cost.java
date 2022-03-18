package com.kodilla.car_service.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Entity
@Table(name = "COSTS")
@AllArgsConstructor
@NoArgsConstructor
public class Cost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private BigDecimal partsCost;

    @NotNull
    private BigDecimal labourCost;

    @NotNull
    private BigDecimal totalCost;

    public Cost(final BigDecimal partsCost, final BigDecimal labourCost, final BigDecimal totalCost) {
        this.partsCost = partsCost;
        this.labourCost = labourCost;
        this.totalCost = totalCost;
    }
}
