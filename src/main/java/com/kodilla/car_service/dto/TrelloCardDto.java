package com.kodilla.car_service.dto;

import lombok.Data;

@Data
public class TrelloCardDto {

    private String name;

    private String desc;

    private String pos;

    private String id;

    private String idList;
}