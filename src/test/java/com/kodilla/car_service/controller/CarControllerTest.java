package com.kodilla.car_service.controller;

import com.google.gson.Gson;
import com.kodilla.car_service.carMd.client.CarMdClient;
import com.kodilla.car_service.carMd.client.CarMdFacade;
import com.kodilla.car_service.domain.Car;
import com.kodilla.car_service.domain.Client;
import com.kodilla.car_service.dto.CarDto;
import com.kodilla.car_service.mapper.CarMapper;
import com.kodilla.car_service.service.CarService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringJUnitWebConfig
@WebMvcTest(CarController.class)
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CarController carController;

    @MockBean
    private CarMapper carMapper;

    @MockBean
    private CarService carService;

    @MockBean
    private CarMdFacade carMdFacade;

    @MockBean
    private CarMdClient carMdClient;

    @Test
    void getCarsTest() throws Exception {
        // Given
        when(carController.getCars()).thenReturn(
                List.of(new CarDto("111111", "CarMake", "CarModel", 2000,
                        "CarEngine", "CarClient")));

        // When & Then
        mockMvc
                .perform(get("/v1/cars")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].vin", Matchers.is("111111")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].make", Matchers.is("CarMake")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].model", Matchers.is("CarModel")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].engine", Matchers.is("CarEngine")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].client", Matchers.is("CarClient")));
    }

    // uncomment when you change the return type from void to Car
//    @Test
//    void createNewCarTest() throws Exception {
//        // Given
//        Car car = new Car("0000", "CarMake", "CarModel", 2000, "CarEngine",
//                new Client(123123L, "Jack", "Black", "Address", "Email"));
//        CarDto carDto = new CarDto("0000", "CarMake", "CarModel", 2000,
//                "CarEngine", "CarClient");
//        when(carController.createNewCar(carDto)).thenReturn(car);
//        Gson gson = new Gson();
//        String jsonContent = gson.toJson(carDto);
//
//        // When & Then
//        mockMvc
//                .perform(MockMvcRequestBuilders
//                        .post("/v1/cars")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .characterEncoding("UTF-8")
//                        .content(jsonContent))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.vin", Matchers.is("0000")))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.make", Matchers.is("CarMake")))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.model", Matchers.is("CarModel")))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.engine", Matchers.is("CarEngine")))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.client.lastname", Matchers.is("Black")));
//    }

    // uncomment when you change the return type from void to Car
//    @Test
//    void createCarWithGivenDataFromCarMdTest() throws Exception {
//        // Given
//        Long phoneNumber = 600600600L;
//        Car car = new Car("0000", "CarMake", "CarModel", 2000, "CarEngine",
//                new Client(phoneNumber, "Jack", "Black", "Address", "Email"));
//        CarDto carDto = new CarDto("0000", "CarMake", "CarModel", 2000,
//                "CarEngine", null);
//        when(carMdFacade.getCarDto()).thenReturn(carDto);
//        when(carController.createCarWithGivenDataFromCarMd(phoneNumber)).thenReturn(car);
//
//        // When & Then
//        mockMvc
//                .perform(MockMvcRequestBuilders
//                        .post("/v1/cars/{phoneNumber}", 600600600L))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.vin", Matchers.is("0000")))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.make", Matchers.is("CarMake")))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.model", Matchers.is("CarModel")))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.engine", Matchers.is("CarEngine")))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.client.phoneNumber", Matchers.is(600600600)));
//    }

    @Test
    void updateCarTest() throws Exception {
        // Given
        CarDto carDto = new CarDto("333", "CarMake", "CarModel", 2000,
                "CarEngine", "John Smith");
        Gson gson = new Gson();
        String jsonContent = gson.toJson(carDto);
        when(carController.updateCar(any(CarDto.class))).thenReturn(carDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vin", Matchers.is("333")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.make", Matchers.is("CarMake")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model", Matchers.is("CarModel")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.engine", Matchers.is("CarEngine")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.client", Matchers.is("John Smith")));
    }
}