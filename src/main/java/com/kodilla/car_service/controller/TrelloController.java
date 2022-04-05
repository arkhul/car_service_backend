package com.kodilla.car_service.controller;

import com.kodilla.car_service.dto.TrelloCardDto;
import com.kodilla.car_service.trello.client.TrelloClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class TrelloController {

    private final TrelloClient trelloClient;

    @RequestMapping(method = RequestMethod.GET, value = "/trello/todo")
    public List<TrelloCardDto> getToDoCards()  {
        return trelloClient.getCardsFromTrelloToDoList();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/trello/inprogress")
    public List<TrelloCardDto> getInProgressCards()  {
        return trelloClient.getCardsFromTrelloInProgressList();
    }
}
