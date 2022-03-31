package com.kodilla.car_service.controller;

import com.kodilla.car_service.carMd.client.CarMdClient;
import com.kodilla.car_service.carMd.client.CarMdFacade;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

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
}