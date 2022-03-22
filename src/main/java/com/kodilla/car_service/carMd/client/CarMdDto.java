package com.kodilla.car_service.carMd.client;

import com.kodilla.car_service.dto.CarDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CarMdDto {

    private Message message;

    private CarDto data;
}
