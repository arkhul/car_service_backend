package com.kodilla.car_service.observer;

import com.kodilla.car_service.dto.ServiceTechnicianDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class MessageForServiceTechnicianTest {

    @InjectMocks
    private MessageForServiceTechnician messageForServiceTechnician;

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
        List<Observer> observers;

        // When
        observers = messageForServiceTechnician.addObserver(observer);
        observers = messageForServiceTechnician.addObserver(observer);
        observers = messageForServiceTechnician.addObserver(observer);
        observers = messageForServiceTechnician.removeObserver(observer);

        // Then
        assertEquals(2, observers.size());
    }
}