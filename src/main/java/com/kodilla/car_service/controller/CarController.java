package com.kodilla.car_service.controller;

import com.kodilla.car_service.carMd.client.CarMdClient;
import com.kodilla.car_service.carMd.client.CarMdFacade;
import com.kodilla.car_service.domain.Car;
import com.kodilla.car_service.dto.CarDto;
import com.kodilla.car_service.exception.CarFoundInDatabaseException;
import com.kodilla.car_service.exception.CarNotFoundException;
import com.kodilla.car_service.mapper.CarMapper;
import com.kodilla.car_service.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class CarController {

    private final CarMapper carMapper;

    private final CarService carService;

    private final CarMdFacade carMdFacade;

    private final CarMdClient carMdClient;

    @RequestMapping(method = RequestMethod.GET, value = "/cars")
    public List<CarDto> getCars() {
        return carMapper.mapToCarDtoList(carService.getCars());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/cars/myservice/{vin}")
    public CarDto getCar(@PathVariable String vin) throws CarNotFoundException {
        return carMapper.mapToCarDto(carService.getCar(vin)
                .orElseThrow(CarNotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/cars/carmd/{vin}")
    public CarDto findCarByVinInCarMd(@PathVariable String vin) throws CarNotFoundException {
        return carMdClient.findCarByVinInCarMd(vin).orElseThrow(CarNotFoundException::new);
    }

    // for testing, we change from void to Car
    @RequestMapping(method = RequestMethod.POST, value = "/cars", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createNewCar(@RequestBody CarDto carDto) throws CarFoundInDatabaseException {
        List<String> vin = carService.getCars().stream()
                .map(Car::getVin)
                .collect(Collectors.toList());
        if (vin.contains(carDto.getVin())) {
            throw new CarFoundInDatabaseException();
        } else {
            carService.saveCar(carMapper.mapToCar(carDto));
        }
    }

    // for testing, we change from void to Car
    @RequestMapping(method = RequestMethod.POST, value = "/cars/{phoneNumber}")
    public void createCarWithGivenDataFromCarMd(@PathVariable Long phoneNumber) {
        CarDto carDto = carMdFacade.getCarDto();
        carDto.setClient(String.valueOf(phoneNumber));
        carService.saveCar(carMapper.mapToCar(carDto));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/cars")
    public CarDto updateCar(@RequestBody CarDto carDto) {
        return carMapper.mapToCarDto(carService.saveCar(carMapper.mapToCar(carDto)));
    }
}
