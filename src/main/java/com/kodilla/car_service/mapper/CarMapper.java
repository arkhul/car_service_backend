package com.kodilla.car_service.mapper;

import com.kodilla.car_service.domain.Car;
import com.kodilla.car_service.dto.CarDto;
import com.kodilla.car_service.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarMapper {

    private final ClientRepository clientRepository;

    public Car mapToCar(final CarDto carDto) {
        return new Car(
                carDto.getVin(),
                carDto.getMake(),
                carDto.getModel(),
                carDto.getYear(),
                carDto.getEngine(),
                clientRepository.findByPhoneNumber(Long.valueOf(carDto.getClient()))
        );
    }

    public CarDto mapToCarDto(final Car car) {
        return new CarDto(
                car.getVin(),
                car.getMake(),
                car.getModel(),
                car.getYear(),
                car.getEngine(),
                car.getClient().getFirstname() + " " + car.getClient().getLastname()
        );
    }

    public List<CarDto> mapToCarDtoList(final List<Car> carList) {
        return carList.stream()
                .map(this::mapToCarDto)
                .collect(Collectors.toList());
    }
}
