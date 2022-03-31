package com.kodilla.car_service.observer;

import com.kodilla.car_service.domain.Repair;
import org.springframework.stereotype.Component;

@Component
public class SmsService {

    // implementation to do in the future

    public String sendASms(Repair repair) {
        return "New car in service: " + repair.getCar().getMake() + " " + repair.getCar().getModel() + " " +
                repair.getCar().getYear() + " " + repair.getDamageDescription();
    }
}
