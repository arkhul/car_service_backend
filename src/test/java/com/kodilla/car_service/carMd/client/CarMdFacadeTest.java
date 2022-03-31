package com.kodilla.car_service.carMd.client;

import com.kodilla.car_service.carMd.config.CarMdConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarMdFacadeTest {

    @InjectMocks
    private CarMdFacade carMdFacade;

    @Mock
    private CarMdConfig carMdConfig;

    @Test
    void getUrlTest() {
        // Given
        String vin = "VIN123";
        when(carMdConfig.getCarMdEndpoint()).thenReturn("http://test.com");

        // When
        URI url = carMdFacade.getUrl(vin);

        // Then
        assertEquals(0, CarMdFacade.quantityOfCredits);
        assertEquals("http://test.com/decode?vin=VIN123", url.toString());
    }

    @Test
    void getHttpEntityWithHeadersTest() {
        // Given
        when(carMdConfig.getCarMdAppKey()).thenReturn("testKey");
        when(carMdConfig.getCarMdAppToken()).thenReturn("testToken");
        List<String> keys = Collections.singletonList("testKey");
        List<String> tokens = Collections.singletonList("testToken");

        // When
        HttpEntity<CarMdDto> entityWithHeaders = carMdFacade.getHttpEntityWithHeaders();
        HttpHeaders headers = entityWithHeaders.getHeaders();

        // Then
        assertEquals(keys, headers.get("authorization"));
        assertEquals(tokens, headers.get("partner-token"));
    }
}