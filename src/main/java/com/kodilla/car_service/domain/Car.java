package com.kodilla.car_service.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
}