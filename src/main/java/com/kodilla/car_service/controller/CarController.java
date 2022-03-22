package com.kodilla.car_service.controller;

import com.kodilla.car_service.carMd.client.CarMdClient;
import com.kodilla.car_service.dto.CarDto;
import com.kodilla.car_service.exception.CarNotFoundException;
import com.kodilla.car_service.mapper.CarMapper;
import com.kodilla.car_service.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class CarController {

    private final CarMapper carMapper;

    private final CarService carService;

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

    @RequestMapping(method = RequestMethod.POST, value = "/cars", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createNewCar(@RequestBody CarDto carDto) {
        carService.saveCar(carMapper.mapToCar(carDto));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/cars/{phoneNumber}")
    public void createCarWithGivenDataFromCarMd(@PathVariable Long phoneNumber) {
        CarDto carDto = carMdClient.getCarDto();
        carDto.setClient(String.valueOf(phoneNumber));
        carService.saveCar(carMapper.mapToCar(carDto));
    }
}
