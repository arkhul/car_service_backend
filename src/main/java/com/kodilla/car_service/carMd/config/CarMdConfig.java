package com.kodilla.car_service.carMd.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class CarMdConfig {

    @Value("${carMd.api.endpoint}")
    private String carMdEndpoint;

    @Value("${carMd.app.authorization}")
    private String carMdAppKey;

    @Value("${carMd.app.partner-token}")
    private String carMdAppToken;
}
