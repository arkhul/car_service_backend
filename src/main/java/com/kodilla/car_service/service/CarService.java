package com.kodilla.car_service.service;

import com.kodilla.car_service.domain.Car;
import com.kodilla.car_service.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    public List<Car> getCars() {
        return carRepository.findAll();
    }

    public Optional<Car> getCar(final String vin) {
        return carRepository.findById(vin);
    }

   public Car saveCar(final Car car) {
       return carRepository.save(car);
   }
}
