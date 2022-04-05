package com.kodilla.car_service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TrelloCardDto {

    private String name;

    private String desc;

    @JsonIgnore
    private String pos;

    private String id;

    @JsonIgnore
    private String idList;
}