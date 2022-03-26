package com.kodilla.car_service.dto;

import com.kodilla.car_service.domain.Repair;
import com.kodilla.car_service.observer.Observer;
import com.kodilla.car_service.observer.SmsService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ServiceTechnicianDto implements Observer {

    public Long id;

    private String name;

    private BigDecimal manHourRate;

    private Long phoneNumber;

    @Override
    public void sendASms(Repair repair) {
        SmsService smsService = new SmsService();
        smsService.sendASms(repair);
    }
}