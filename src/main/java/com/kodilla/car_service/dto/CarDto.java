package com.kodilla.car_service.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
public class CarDto {

    private String vin;

    private String make;

    private String model;

    private int year;

    private String engine;

    private String client;

    public CarDto(final String vin, final String make, final String model, final int year, final String engine, final String client) {
        this.vin = vin;
        this.make = make;
        this.model = model;
        this.year = year;
        this.engine = engine;
        this.client = client;
    }
}
