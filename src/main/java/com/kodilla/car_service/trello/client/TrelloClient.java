package com.kodilla.car_service.trello.client;

import com.kodilla.car_service.domain.Mail;
import com.kodilla.car_service.dto.RepairDto;
import com.kodilla.car_service.dto.TrelloCardDto;
import com.kodilla.car_service.trello.config.TrelloConfig;
import com.kodilla.car_service.emailService.SimpleEmailService;
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

    private final SimpleEmailService simpleEmailService;

    private final TrelloConfig trelloConfig;

    private static final Logger LOGGER = LoggerFactory.getLogger(TrelloClient.class);

    public void createCard(RepairDto repairDto) {
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
        restTemplate.postForObject(url, null, TrelloCardDto.class);
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
                .queryParam("desc", repairDto.getDamageDescription())
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
                .filter(p -> Objects.nonNull(p.getId()) && Objects.nonNull(p.getName())
                        && Objects.nonNull(p.getDesc()) && Objects.nonNull(p.getIdList())
                        && Objects.nonNull(p.getPos()))
                .collect(Collectors.toList());
    }
}