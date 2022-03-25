package com.kodilla.car_service.carMd.client;

import com.kodilla.car_service.dto.CarDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CarMdClient {

    private final CarMdFacade carMdFacade;

    public Optional<CarDto> findCarByVinInCarMd(final String vin) {
        if (!carMdFacade.isCreditAvailable()) {
            CarDto carDto = new CarDto();
            carDto.setVin("No access to the CarMD App (no credits). Please, contact the software owner");
            return Optional.of(carDto);
        } else {
            return carMdFacade.getCarFromCarMdDatabase(vin);
        }
    }
}
