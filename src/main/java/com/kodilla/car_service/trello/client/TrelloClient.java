package com.kodilla.car_service.trello.client;

import com.kodilla.car_service.domain.Mail;
import com.kodilla.car_service.dto.CreatedTrelloCardDto;
import com.kodilla.car_service.dto.RepairDto;
import com.kodilla.car_service.dto.TrelloCardDto;
import com.kodilla.car_service.emailService.SimpleEmailService;
import com.kodilla.car_service.trello.config.TrelloConfig;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TrelloClient {

    private final RestTemplate restTemplate;

    private final SimpleEmailService simpleEmailService;

    private final TrelloConfig trelloConfig;

    private static final Logger LOGGER = LoggerFactory.getLogger(TrelloClient.class);

    public List<TrelloCardDto> getCardsFromTrelloToDoList() {
        URI url = UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndpoint() +
                "/lists/622f32652b798678df8eaa84/cards")
                .queryParam("key", trelloConfig.getTrelloAppKey())
                .queryParam("token", trelloConfig.getTrelloToken())
                .queryParam("fields", "name,desc")
                .build()
                .encode()
                .toUri();
        try {
            TrelloCardDto[] cardsResponse = restTemplate.getForObject(url, TrelloCardDto[].class);
            return new ArrayList<>(Optional.ofNullable(cardsResponse)
                    .map(Arrays::asList)
                    .orElse(Collections.emptyList()));
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public CreatedTrelloCardDto createCard(RepairDto repairDto) {
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
        return restTemplate.postForObject(url, null, CreatedTrelloCardDto.class);
    }

    public void updateTheCardAfterRepairStatusChanges(String cardId, String email, RepairDto repairDto) {
        simpleEmailService.sendMail(
                new Mail(
                        email,
                        "Repair status changed",
                        "Dear customer, " +
                                "we changed the repair status of your car (vin: " +
                                repairDto.getCar() + "). The current status is: " +
                                repairDto.getRepairStatus() + ". Best regards."

                ));
        LOGGER.info("Mail text: The current status is: " + repairDto.getRepairStatus());
        restTemplate.put(getUrl(cardId, repairDto), null);
    }

    public URI getUrl(String cardId, RepairDto repairDto) {
        String repairStatus = repairDto.getRepairStatus();
        String idList;
        if (repairStatus.equals("IN_PROGRESS")) {
            idList = "622f326c008ba782dae6c146";
            LOGGER.info("Change status to IN_PROGRESS");
        } else {
            idList = "622f3270e0d1a952c91794ae";
            LOGGER.info("Change status to DONE");
        }
        return UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndpoint() +
                "/cards/" + cardId)
                .queryParam("key", trelloConfig.getTrelloAppKey())
                .queryParam("token", trelloConfig.getTrelloToken())
                .queryParam("name", repairDto.getCar())
                .queryParam("desc", repairDto.getDamageDescription() + " " + repairDto.getServiceTechnician())
                .queryParam("pos", "top")
                .queryParam("idList", idList)
                .queryParam("id", cardId)
                .build()
                .encode()
                .toUri();
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
                .filter(p -> Objects.nonNull(p.getId()) && Objects.nonNull(p.getName()) &&
                        Objects.nonNull(p.getDesc()))
                .collect(Collectors.toList());
    }

    public List<TrelloCardDto> getCardsFromTrelloInProgressList() {
        URI url = UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndpoint() +
                "/lists/622f326c008ba782dae6c146/cards")
                .queryParam("key", trelloConfig.getTrelloAppKey())
                .queryParam("token", trelloConfig.getTrelloToken())
                .queryParam("fields", "name,desc")
                .build()
                .encode()
                .toUri();
        try {
            TrelloCardDto[] cardsResponse = restTemplate.getForObject(url, TrelloCardDto[].class);
            return new ArrayList<>(Optional.ofNullable(cardsResponse)
                    .map(Arrays::asList)
                    .orElse(Collections.emptyList()));
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }
}