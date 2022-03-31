package com.kodilla.car_service.mapper;

import com.kodilla.car_service.domain.Car;
import com.kodilla.car_service.domain.Client;
import com.kodilla.car_service.dto.CarDto;
import com.kodilla.car_service.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarMapperTest {

    @InjectMocks
    private CarMapper carMapper;

    @Mock
    private ClientRepository clientRepository;

    @Test
    void mapToCarTest() {
        // Given
        Client client = new Client(123456789L, "John", "Doe", "Address",
                "Email");
        CarDto carDto = new CarDto("vin123", "make", "model", 2000, "engine",
                "123456789");
        when(clientRepository.findByPhoneNumber(123456789L)).thenReturn(client);

        // When
        Car car = carMapper.mapToCar(carDto);

        // Then
        assertEquals("vin123", car.getVin());
        assertEquals("make", car.getMake());
        assertEquals("model", car.getModel());
        assertEquals(2000, car.getYear());
        assertEquals("engine", car.getEngine());
        assertEquals(123456789L, car.getClient().getPhoneNumber());
    }

    @Test
    void mapToCarDtoTest() {
        // Given
        Client client = new Client(123123123L, "John", "Smith", "Address",
                "Email");
        Car car = new Car("vin123", "make", "model", 2010, "engine", client);

        // When
        CarDto carDto = carMapper.mapToCarDto(car);

        // Then
        assertEquals("vin123", carDto.getVin());
        assertEquals("make", carDto.getMake());
        assertEquals("model", carDto.getModel());
        assertEquals(2010, carDto.getYear());
        assertEquals("engine", carDto.getEngine());
        assertEquals("John Smith", carDto.getClient());
    }

    @Test
    void mapToCarDtoListTest() {
        // Given
        Client client1 = new Client(111111111L, "John", "Smith", "Address1",
                "Email1");
        Client client2 = new Client(222222222L, "John", "Doe", "Address2",
                "Email2");
        Car car1 = new Car("vin11", "make11", "model11", 2011,
                "engine11", client1);
        Car car2 = new Car("vin22", "make22", "model22", 2022,
                "engine22", client2);
        List<Car> carList = Arrays.asList(car1, car2);

        // When
        List<CarDto> carDtoList = carMapper.mapToCarDtoList(carList);

        // Then
        assertEquals(2, carDtoList.size());
        assertEquals("vin11", carDtoList.get(0).getVin());
        assertEquals("model11", carDtoList.get(0).getModel());
        assertEquals("John Smith", carDtoList.get(0).getClient());
        assertEquals("vin22", carDtoList.get(1).getVin());
        assertEquals("engine22", carDtoList.get(1).getEngine());
        assertEquals("John Doe", carDtoList.get(1).getClient());
    }
}