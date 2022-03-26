package com.kodilla.car_service.observer;

import com.kodilla.car_service.domain.Repair;

public interface Observable {

    void notifyAllObservers(Repair repair);

    void addObserver(Observer observer);

    void removeObserver(Observer observer);
}
