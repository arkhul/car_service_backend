package com.kodilla.car_service.carMd.client;

import com.kodilla.car_service.carMd.config.CarMdConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarMdClientTest {

    @InjectMocks
    private CarMdConfig carMdConfig;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private CarMdClient carMdClient;

    @Test
    void findCarByVinInCarMdTest() {


    }

    @Test
    void getVinDecode() {
        // Given
//        String vin = "WDF69370312345678";
//
//        // When
//        URI url = carMdClient.getVinDecode(vin);
//        System.out.println("Url: " + url);
//        // Then
//        assertThat(url.getScheme()).isEqualTo("http");
//        assertThat(url.getHost()).isEqualTo("api.carmd.com");
//        assertThat(url.getPath()).isEqualTo("v3.0");
//        assertThat(url.getQuery()).isEqualTo("WDF69370312345678");
    }
}