package com.kodilla.car_service.controller;

import com.google.gson.Gson;
import com.kodilla.car_service.domain.ServiceTechnician;
import com.kodilla.car_service.dto.ServiceTechnicianDto;
import com.kodilla.car_service.mapper.ServiceTechnicianMapper;
import com.kodilla.car_service.observer.MessageForServiceTechnician;
import com.kodilla.car_service.service.ServiceTechnicianService;
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

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig
@WebMvcTest(ServiceTechnicianController.class)
class ServiceTechnicianControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ServiceTechnicianController serviceTechnicianController;

    @MockBean
    private ServiceTechnicianMapper serviceTechnicianMapper;

    @MockBean
    private ServiceTechnicianService serviceTechnicianService;

    @MockBean
    private MessageForServiceTechnician messageForServiceTechnician;

    @Test
    void getServiceTechniciansTest() throws Exception {
        // Given
        when(serviceTechnicianController.getServiceTechnicians()).thenReturn(
                List.of(new ServiceTechnicianDto(20L, "John Doe",
                        new BigDecimal("100"), 112233L)));

        // When & Then
        mockMvc
                .perform(get("/v1/technicians")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(20)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("John Doe")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].manHourRate", Matchers.is(100)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].phoneNumber", Matchers.is(112233)));
    }

    @Test
    void createServiceTechnicianTest() throws Exception {
        // Given
        ServiceTechnician serviceTechnician = new ServiceTechnician(20L, "John Doe",
                new BigDecimal("100"), 112233L);
        ServiceTechnicianDto serviceTechnicianDto = new ServiceTechnicianDto(20L, "John Doe",
                new BigDecimal("100"), 112233L);
        when(serviceTechnicianController.createServiceTechnician(serviceTechnicianDto)).
                thenReturn(serviceTechnician);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(serviceTechnicianDto);

        // When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/technicians")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(20)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("John Doe")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.manHourRate", Matchers.is(100)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber", Matchers.is(112233)));
    }

    @Test
    void updateServiceTechnicianTest() throws Exception {
        // Given
        ServiceTechnicianDto serviceTechnicianDto = new ServiceTechnicianDto(20L, "John Doe",
                new BigDecimal("100"), 112233L);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(serviceTechnicianDto);
        when(serviceTechnicianController.updateServiceTechnician(any(ServiceTechnicianDto.class)))
                .thenReturn(serviceTechnicianDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/technicians")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(20)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("John Doe")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.manHourRate", Matchers.is(100)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber", Matchers.is(112233)));
    }

    @Test
    void deleteServiceTechnicianWithExceptionTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/v1/technicians/" + any(Long.class))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }
}