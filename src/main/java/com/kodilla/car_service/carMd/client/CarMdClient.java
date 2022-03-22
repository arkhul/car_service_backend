package com.kodilla.car_service.carMd.client;

import com.kodilla.car_service.carMd.config.CarMdConfig;
import com.kodilla.car_service.dto.CarDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CarMdClient {

    private final RestTemplate restTemplate;

    private final CarMdConfig carMdConfig;

    private CarDto carDto;

    public Optional<CarDto> findCarByVinInCarMd(final String vin) {
        URI url = UriComponentsBuilder.fromHttpUrl(carMdConfig.getCarMdEndpoint() + "/decode")
                .queryParam("vin", vin)
                .build()
                .encode()
                .toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("authorization", carMdConfig.getCarMdAppKey());
        headers.set("partner-token", carMdConfig.getCarMdAppToken());

        HttpEntity<CarMdDto> entity = new HttpEntity<>(headers);
        ResponseEntity<CarMdDto> respEntity = restTemplate.exchange(url, HttpMethod.GET, entity, CarMdDto.class);

        Optional<CarDto> response = Optional.ofNullable(Objects.requireNonNull(respEntity.getBody()).getData());
        if (response.isPresent()) {
            carDto = response.get();
            carDto.setVin(vin);
        }
        return response;
    }

    public CarDto getCarDto() {
        return carDto;
    }
}
