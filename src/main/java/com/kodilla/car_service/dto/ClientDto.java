package com.kodilla.car_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {

    private Long phoneNumber;

    private String firstname;

    private String lastname;

    private String address;

    private String email;
}
