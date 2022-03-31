package com.kodilla.car_service.observer;

import com.kodilla.car_service.domain.Repair;

import java.util.List;

public interface Observable {

    void notifyAllObservers(Repair repair);

    List<Observer> addObserver(Observer observer);

    List<Observer> removeObserver(Observer observer);
}