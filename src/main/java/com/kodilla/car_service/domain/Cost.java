package com.kodilla.car_service.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Entity
@Table(name = "COSTS")
@AllArgsConstructor
@NoArgsConstructor
public class Cost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id;

    @NotNull
    private BigDecimal partsCost;

    @NotNull
    private BigDecimal labourCost;

    @NotNull
    private BigDecimal totalCost;
}
