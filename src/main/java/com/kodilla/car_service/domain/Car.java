package com.kodilla.car_service.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "CARS")
@AllArgsConstructor
@NoArgsConstructor
public class Car {

    @Id
    @NotNull
    private String vin;

    @NotNull
    private String make;

    @NotNull
    private String model;

    @NotNull
    private int year;

    @NotNull
    private String engine;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(
            targetEntity = Repair.class,
            mappedBy = "car",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Repair> repairList = new ArrayList<>();

    public Car(final String vin, final String make, final String model, final int year, final String engine, final Client client) {
        this.vin = vin;
        this.make = make;
        this.model = model;
        this.year = year;
        this.engine = engine;
        this.client = client;
    }
}