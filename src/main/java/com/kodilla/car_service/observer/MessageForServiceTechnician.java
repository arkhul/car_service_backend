package com.kodilla.car_service.observer;

import com.kodilla.car_service.domain.Repair;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Component
public class MessageForServiceTechnician implements Observable {

    private final List<Observer> observers = new ArrayList<>();

    @Override
    public void notifyAllObservers(final Repair repair) {
        for (Observer observer : observers) {
            observer.sendASms(repair);
        }
    }

    @Override
    public List<Observer> addObserver(final Observer observer) {
        observers.add(observer);
        return observers;
    }

    @Override
    public List<Observer> removeObserver(final Observer observer) {
        observers.remove(observer);
        return observers;
    }
}
