package com.kodilla.car_service.mapper;

import com.kodilla.car_service.domain.*;
import com.kodilla.car_service.dto.RepairDto;
import com.kodilla.car_service.repair.RepairStatus;
import com.kodilla.car_service.repository.CarRepository;
import com.kodilla.car_service.repository.CostRepository;
import com.kodilla.car_service.repository.ServiceTechnicianRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RepairMapperTest {

    @InjectMocks
    private RepairMapper repairMapper;

    @Mock
    private CarRepository carRepository;

    @Mock
    private ServiceTechnicianRepository serviceTechnicianRepository;

    @Mock
    private CostRepository costRepository;

    @Test
    void mapToRepairTest() {
        // Given
        Cost cost = new Cost(new BigDecimal("100"), new BigDecimal("100"),
                new BigDecimal("1000"));
        ServiceTechnician serviceTechnician = new ServiceTechnician(20L, "Jack Black",
                new BigDecimal("20"), 222L);
        Client client = new Client(111111111L, "John", "Smith", "Address1",
                "Email1");
        Car car = new Car("VIN111", "make111", "model111", 2011,
                "engine111", client);
        RepairDto repairDto = new RepairDto(1L, "DamageDesc", null, null, null, null, null,
                "VIN111", "Jack Black", new BigDecimal("1000"));
        when(carRepository.findByVin("VIN111")).thenReturn(car);
        when(serviceTechnicianRepository.findByName("Jack Black")).thenReturn(serviceTechnician);
        when(costRepository.findByTotalCost(new BigDecimal("1000"))).thenReturn(cost);

        // When
        Repair repair = repairMapper.mapToRepair(repairDto);

        // Then
        assertEquals("DamageDesc", repair.getDamageDescription());
        assertEquals(LocalDate.now(), repair.getAdmissionDate());
        assertNull(repair.getReleaseDate());
        assertNull(repair.getRepairDescription());
        assertNull(repair.getRepairTimeInManHours());
        assertEquals("VIN111", repair.getCar().getVin());
        assertEquals(222L, repair.getServiceTechnician().getPhoneNumber());
        assertEquals(new BigDecimal("1000"), repair.getCost().getTotalCost());
    }

    @Test
    void mapToRepairDtoTest() {
        // Given
        Cost cost = new Cost(new BigDecimal("100"), new BigDecimal("100"),
                new BigDecimal("1000"));
        ServiceTechnician serviceTechnician = new ServiceTechnician(20L, "Jack Black",
                new BigDecimal("20"), 222L);
        Client client = new Client(111111111L, "John", "Smith", "Address1",
                "Email1");
        Car car = new Car("VIN111", "make111", "model111", 2011,
                "engine111", client);
        Repair repair = new Repair(1L, "DamageDesc", RepairStatus.DONE,
                LocalDate.of(2022, 3, 15), LocalDate.of(2022, 3, 20),
                "RepairDesc", new BigDecimal("40"), car, serviceTechnician, cost);

        // When
        RepairDto repairDto = repairMapper.mapToRepairDto(repair);

        // Then
        assertEquals("VIN111", repairDto.getCar());
        assertEquals("DONE", repairDto.getRepairStatus());
        assertEquals(new BigDecimal("1000"), repairDto.getCost());
        assertEquals("DamageDesc", repairDto.getDamageDescription());
        assertEquals("RepairDesc", repairDto.getRepairDescription());
        assertEquals("Jack Black", repairDto.getServiceTechnician());
        assertEquals(LocalDate.of(2022, 3, 15), repairDto.getAdmissionDate());
    }

    @Test
    void mapToRepairDtoListTest() {
        // Given
        Cost cost11 = new Cost(new BigDecimal("11"), new BigDecimal("11"),
                new BigDecimal("11"));
        ServiceTechnician serviceTechnician11 = new ServiceTechnician(11L, "Jack Black11",
                new BigDecimal("11"), 11L);
        Client client11 = new Client(111111111L, "John", "Smith", "Address11",
                "Email11");
        Car car11 = new Car("VIN11", "make11", "model11", 2011,
                "engine11", client11);
        Cost cost22 = new Cost(new BigDecimal("22"), new BigDecimal("22"),
                new BigDecimal("22"));
        ServiceTechnician serviceTechnician22 = new ServiceTechnician(22L, "Jack Black22",
                new BigDecimal("22"), 22L);
        Client client22 = new Client(222222222L, "John", "Doe", "Address22",
                "Email22");
        Car car22 = new Car("VIN22", "make22", "model22", 2022,
                "engine22", client22);
        List<Repair> repairList = Arrays.asList(
                new Repair(1L, "DamageDesc1", RepairStatus.IN_PROGRESS,
                        LocalDate.of(2022, 1, 11),
                        LocalDate.of(2022, 1, 12),
                        "RepairDesc11", new BigDecimal("11"),
                        car11, serviceTechnician11, cost11),
                new Repair(2L, "DamageDesc2", RepairStatus.DONE,
                        LocalDate.of(2022, 2, 22),
                        LocalDate.of(2022, 2, 23),
                        "RepairDesc22", new BigDecimal("22"),
                        car22, serviceTechnician22, cost22)
        );

        // When
        List<RepairDto> repairDtoList = repairMapper.mapToRepairDtoList(repairList);

        // Then
        assertEquals(2, repairDtoList.size());
        assertEquals(1L, repairDtoList.get(0).getId());
        assertEquals(2L, repairDtoList.get(1).getId());
        assertEquals("RepairDesc11", repairDtoList.get(0).getRepairDescription());
        assertEquals("RepairDesc22", repairDtoList.get(1).getRepairDescription());
        assertEquals("VIN11", repairDtoList.get(0).getCar());
        assertEquals("VIN22", repairDtoList.get(1).getCar());
    }
}