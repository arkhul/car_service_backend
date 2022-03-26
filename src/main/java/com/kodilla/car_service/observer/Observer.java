package com.kodilla.car_service.observer;

import com.kodilla.car_service.domain.Repair;

public interface Observer {

    void sendASms(Repair repair);
}