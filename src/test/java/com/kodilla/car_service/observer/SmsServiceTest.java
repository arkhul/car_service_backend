package com.kodilla.car_service.observer;

import com.kodilla.car_service.domain.*;
import com.kodilla.car_service.repair.RepairStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class SmsServiceTest {

    private final Client client = new Client(
            123456789L, "firstname", "lastname", "address", "email");

    private final Car car = new Car("vin", "make", "model", 2000, "engine", client);

    private final ServiceTechnician serviceTechnician = new ServiceTechnician(
            1L, "Nobody", new BigDecimal("1"), 1L);

    private final Cost cost = new Cost(new BigDecimal("1"), new BigDecimal("1"), new BigDecimal("1"));

    private final Repair repair = new Repair(1L, "DamageDesc", RepairStatus.TO_DO,
            LocalDate.of(2022, 2, 10), null,
            null, null, car, serviceTechnician, cost);

    @Test
    void sendASmsTest() {
        // Given
        SmsService smsService = new SmsService();

        // When
        String txt = smsService.sendASms(repair);

        // Then
        assertEquals("New car in service: make model 2000 DamageDesc", txt);
    }
}