package com.kodilla.car_service.carMd.client;

import com.kodilla.car_service.carMd.config.CarMdConfig;
import com.kodilla.car_service.dto.CarDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CarMdFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarMdFacade.class);

    public static int quantityOfCredits = 22;

    private final CarMdConfig carMdConfig;

    private final RestTemplate restTemplate;

    private CarDto carDto;

    public URI getUrl(String vin) {
        LOGGER.info("Decoding the vin. " + --quantityOfCredits + " credits left.");
        return UriComponentsBuilder.fromHttpUrl(carMdConfig.getCarMdEndpoint() + "/decode")
                .queryParam("vin", vin)
                .build()
                .encode()
                .toUri();
    }

    public HttpEntity<CarMdDto> getHttpEntityWithHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("authorization", carMdConfig.getCarMdAppKey());
        headers.set("partner-token", carMdConfig.getCarMdAppToken());
        return new HttpEntity<>(headers);
    }

    public boolean isCreditAvailable() {
        return quantityOfCredits != 0;
    }

    public Optional<CarDto> getCarFromCarMdDatabase(String vin) {
        ResponseEntity<CarMdDto> respEntity = restTemplate.exchange(
                getUrl(vin),
                HttpMethod.GET,
                getHttpEntityWithHeaders(),
                CarMdDto.class);
        Optional<CarDto> response = Optional.ofNullable(Objects.requireNonNull(respEntity.getBody()).getData());
        if (response.isPresent()) {
            LOGGER.info("The data has been correctly retrieved from the CarMd database.");
            carDto = response.get();
            carDto.setVin(vin);
        } else {
            LOGGER.info("No data found for the searched value: " + vin + ".");
        }
        return response;
    }

    public CarDto getCarDto() {
        return carDto;
    }
}
