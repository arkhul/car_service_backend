package com.kodilla.car_service.domain;

import com.kodilla.car_service.repair.RepairStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Entity
@Table(name = "REPAIRS")
@AllArgsConstructor
@NoArgsConstructor
public class Repair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String damageDescription;

    @NotNull
    private RepairStatus repairStatus;

    private LocalDate admissionDate;

    private LocalDate releaseDate;

    private String repairDescription;

    private BigDecimal repairTimeInManHours;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne
    @JoinColumn(name = "service_technician_id")
    private ServiceTechnician serviceTechnician;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Cost cost;

    public Repair(Long id, String damageDescription, Car car, ServiceTechnician serviceTechnician, Cost cost) {
        this.id = id;
        this.damageDescription = damageDescription;
        this.repairStatus = RepairStatus.TO_DO;
        this.admissionDate = LocalDate.now();
        this.car = car;
        this.serviceTechnician = serviceTechnician;
        this.cost = cost;
    }

    public void setRepairStatus(final RepairStatus repairStatus) {
        this.repairStatus = repairStatus;
    }

    public void setReleaseDate(final LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setRepairDescription(final String repairDescription) {
        this.repairDescription = repairDescription;
    }

    public void setRepairTimeInManHours(final BigDecimal repairTimeInManHours) {
        this.repairTimeInManHours = repairTimeInManHours;
    }

    public void setServiceTechnician(final ServiceTechnician serviceTechnician) {
        this.serviceTechnician = serviceTechnician;
    }

    public void setCost(final Cost cost) {
        this.cost = cost;
    }
}
