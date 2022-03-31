package com.kodilla.car_service.mapper;

import com.kodilla.car_service.domain.ServiceTechnician;
import com.kodilla.car_service.dto.ServiceTechnicianDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ServiceTechnicianMapperTest {

    private final ServiceTechnicianMapper serviceTechnicianMapper = new ServiceTechnicianMapper();

    @Test
    void mapToServiceTechnicianTest() {
        // Given
        ServiceTechnicianDto serviceTechnicianDto = new ServiceTechnicianDto(
                1L, "John Doe", new BigDecimal("10"), 123456789L);

        // When
        ServiceTechnician serviceTechnician = serviceTechnicianMapper.mapToServiceTechnician(serviceTechnicianDto);

        // Then
        assertEquals("John Doe", serviceTechnician.getName());
        assertEquals(1L, serviceTechnician.getId());
        assertEquals(123456789L, serviceTechnician.getPhoneNumber());
    }

    @Test
    void mapToServiceTechnicianDtoTest() {
        // Given
        ServiceTechnician serviceTechnician = new ServiceTechnician(
                1L, "Jack Black", new BigDecimal("100"), 123L);

        // When
        ServiceTechnicianDto serviceTechnicianDto = serviceTechnicianMapper.mapToServiceTechnicianDto(serviceTechnician);

        // Then
        assertEquals("Jack Black", serviceTechnicianDto.getName());
        assertEquals(123L, serviceTechnicianDto.getPhoneNumber());
        assertEquals(new BigDecimal("100"), serviceTechnicianDto.getManHourRate());
    }

    @Test
    void mapToServiceTechnicianDtoListTest() {
        // Given
        ServiceTechnician serviceTechnician1 = new ServiceTechnician(
                1L, "Jack Black1", new BigDecimal("100"), 123L);
        ServiceTechnician serviceTechnician2 = new ServiceTechnician(
                2L, "Jack Black2", new BigDecimal("200"), 234L);
        ServiceTechnician serviceTechnician3 = new ServiceTechnician(
                3L, "Jack Black3", new BigDecimal("300"), 345L);
        List<ServiceTechnician> serviceTechnicianList = Arrays.asList(
                serviceTechnician1, serviceTechnician2, serviceTechnician3);

        // When
        List<ServiceTechnicianDto> serviceTechnicianDtoList = serviceTechnicianMapper.mapToServiceTechnicianDtoList(
                serviceTechnicianList);

        // Then
        assertEquals(3, serviceTechnicianDtoList.size());
        assertEquals("Jack Black1", serviceTechnicianDtoList.get(0).getName());
        assertEquals(1L, serviceTechnicianDtoList.get(0).getId());
        assertEquals(123L, serviceTechnicianDtoList.get(0).getPhoneNumber());
        assertEquals("Jack Black2", serviceTechnicianDtoList.get(1).getName());
        assertEquals(2L, serviceTechnicianDtoList.get(1).getId());
        assertEquals(234L, serviceTechnicianDtoList.get(1).getPhoneNumber());
        assertEquals("Jack Black3", serviceTechnicianDtoList.get(2).getName());
        assertEquals(3L, serviceTechnicianDtoList.get(2).getId());
        assertEquals(345L, serviceTechnicianDtoList.get(2).getPhoneNumber());
    }
}