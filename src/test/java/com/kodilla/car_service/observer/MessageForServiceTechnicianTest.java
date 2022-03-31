package com.kodilla.car_service.observer;

import com.kodilla.car_service.domain.*;
import com.kodilla.car_service.dto.ServiceTechnicianDto;
import com.kodilla.car_service.repair.RepairStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class MessageForServiceTechnicianTest {

    private final Client client = new Client(
            123456789L, "firstname", "lastname", "address", "email");

    private final Car car = new Car("vin", "make", "model", 2000, "engine", client);

    private final ServiceTechnician serviceTechnician = new ServiceTechnician(
            1L, "Nobody", new BigDecimal("1"), 1L);

    private final Cost cost = new Cost(new BigDecimal("1"), new BigDecimal("1"), new BigDecimal("1"));

    private final Repair repair = new Repair(1L, "DamageDesc", RepairStatus.TO_DO,
            LocalDate.of(2022, 2, 10), null,
            null, null, car, serviceTechnician, cost);

    @InjectMocks
    private MessageForServiceTechnician messageForServiceTechnician;

    @Mock
    private final SmsService smsService = new SmsService();

    @Test
    void addObserverTest() {
        // Given
        Observer observer = new ServiceTechnicianDto();

        // When
        List<Observer> observers = messageForServiceTechnician.addObserver(observer);

        // Then
        assertNotNull(observers);
        assertEquals(1, observers.size());
    }

    @Test
    void removeObserverTest() {
        // Given
        Observer observer = new ServiceTechnicianDto();
        List<Observer> observers = new ArrayList<>();

        // When
        observers = messageForServiceTechnician.addObserver(observer);
        observers = messageForServiceTechnician.addObserver(observer);
        observers = messageForServiceTechnician.addObserver(observer);
        observers = messageForServiceTechnician.removeObserver(observer);

        // Then
        assertEquals(2, observers.size());
    }

    @Test
    void getObservers() {
    }
}