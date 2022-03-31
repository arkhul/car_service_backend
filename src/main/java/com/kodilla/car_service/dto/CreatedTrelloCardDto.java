package com.kodilla.car_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreatedTrelloCardDto {

    private String id;

    private String name;

    private String shortUrl;
}
