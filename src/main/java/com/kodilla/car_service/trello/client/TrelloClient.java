package com.kodilla.car_service.trello.client;

import com.kodilla.car_service.dto.RepairDto;
import com.kodilla.car_service.dto.TrelloCardDto;
import com.kodilla.car_service.trello.config.TrelloConfig;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TrelloClient {

    private final RestTemplate restTemplate;

    private final TrelloConfig trelloConfig;

    private static final Logger LOGGER = LoggerFactory.getLogger(TrelloClient.class);

    public TrelloCardDto createCard(RepairDto repairDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndpoint() + "/cards")
                .queryParam("key", trelloConfig.getTrelloAppKey())
                .queryParam("token", trelloConfig.getTrelloToken())
                .queryParam("name", repairDto.getCar())
                .queryParam("desc", repairDto.getDamageDescription())
                .queryParam("pos", "bottom")
                .queryParam("idList", "622f32652b798678df8eaa84")
                .build()
                .encode()
                .toUri();

        return restTemplate.postForObject(url, null, TrelloCardDto.class);
    }

    public void updateCard(String cardId, RepairDto repairDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndpoint() +
                "/cards/" + cardId)
                .queryParam("key", trelloConfig.getTrelloAppKey())
                .queryParam("token", trelloConfig.getTrelloToken())
                .queryParam("name", repairDto.getCar())
                .queryParam("desc", repairDto.getDamageDescription())
                .queryParam("pos", "top")
                .queryParam("idList", "622f326c008ba782dae6c146")
                .queryParam("id", cardId)
                .build()
                .encode()
                .toUri();

        restTemplate.put(url, null);
    }

    public List<TrelloCardDto> getCardDataList() {
        URI url = UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndpoint() +
                "/boards/" + trelloConfig.getTrelloBoard() + "/cards")
                .queryParam("key", trelloConfig.getTrelloAppKey())
                .queryParam("token", trelloConfig.getTrelloToken())
                .queryParam("fields", "id,name,desc,idList,pos")
                .build()
                .encode()
                .toUri();
        TrelloCardDto[] cardResponse = restTemplate.getForObject(url, TrelloCardDto[].class);
        return Optional.ofNullable(cardResponse)
                .map(Arrays::asList)
                .orElse(Collections.emptyList())
                .stream()
                .filter(p -> Objects.nonNull(p.getId()) && Objects.nonNull(p.getName())
                        && Objects.nonNull(p.getDesc()) && Objects.nonNull(p.getIdList())
                        && Objects.nonNull(p.getPos()))
                .collect(Collectors.toList());
    }
}