package com.kodilla.car_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TrelloCardDto {

    private String name;

    private String desc;

    private String pos;

    private String id;

    private String idList;
}