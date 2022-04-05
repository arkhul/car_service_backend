package com.kodilla.car_service.trello.client;

import com.kodilla.car_service.domain.Mail;
import com.kodilla.car_service.dto.CreatedTrelloCardDto;
import com.kodilla.car_service.dto.RepairDto;
import com.kodilla.car_service.dto.TrelloCardDto;
import com.kodilla.car_service.trello.config.TrelloConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrelloClientTest {

    @InjectMocks
    private TrelloClient trelloClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private TrelloConfig trelloConfig;

    private RepairDto repairDto() {
        return new RepairDto(1L, "DamageDescription", "ToDo",
                LocalDate.of(2022, 3, 10), LocalDate.of(2022, 3, 15),
                "Repair description", new BigDecimal("20.00"), "WDW123456789",
                "JohnDoe", new BigDecimal("1000.00"));
    }

    private void setTrelloValues() {
        when(trelloConfig.getTrelloApiEndpoint()).thenReturn("https://test.com");
        when(trelloConfig.getTrelloAppKey()).thenReturn("test");
        when(trelloConfig.getTrelloToken()).thenReturn("test");
    }

    @Test
    void createCardTest() throws URISyntaxException {
        // Given
        setTrelloValues();
        URI url = new URI("https://test.com/cards?key=test&token=test" +
                "&name=" + repairDto().getCar() +
                "&desc=" + repairDto().getDamageDescription() +
                "&pos=bottom&idList=622f32652b798678df8eaa84");
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto(
                "1", "New Card", "https://newcard.com"
        );
        when(restTemplate.postForObject(url, null, CreatedTrelloCardDto.class)).thenReturn(createdTrelloCardDto);

        // When
        CreatedTrelloCardDto newCard = trelloClient.createCard(repairDto());

        // Then
        assertEquals("1", newCard.getId());
        assertEquals("New Card", newCard.getName());
        assertEquals("https://newcard.com", newCard.getShortUrl());

    }

    @Test
    void sendEmailToClientTest() {
        // Given
        Mail mail = new Mail("test@trello.com", "Test", "TestMessage " + repairDto().getCar() + " "
                + repairDto().getRepairStatus());

        // When & Then
        assertEquals("test@trello.com", mail.getMailTo());
        assertEquals("Test", mail.getSubject());
        assertEquals("TestMessage WDW123456789 ToDo", mail.getMessage());
    }

    @Test
    void getUrlWhenRepairStartsTest() throws URISyntaxException {
        // Given
        String cardId = "12345";
        String idList = "622f326c008ba782dae6c146";
        RepairDto repairDto = repairDto();
        repairDto.setRepairStatus("IN_PROGRESS");
        setTrelloValues();

        // When
        URI uri = trelloClient.getUrl(cardId, repairDto);

        // Then
        URI expected = new URI("https://test.com/cards/" + cardId + "?key=test&token=test" +
                "&name=" + repairDto().getCar() +
                "&desc=" + repairDto().getDamageDescription() + "%20" + repairDto().getServiceTechnician() +
                "&pos=top&idList=" + idList +
                "&id=" + cardId);
        assertThat(uri).isEqualTo(expected);
    }

    @Test
    void getUrlWhenRepairEndTest() throws URISyntaxException {
        // Given
        String cardId = "12345";
        String idList = "622f3270e0d1a952c91794ae";
        RepairDto repairDto = repairDto();
        repairDto.setRepairStatus("Done");
        setTrelloValues();

        // When
        URI uri = trelloClient.getUrl(cardId, repairDto);

        // Then
        URI expected = new URI("https://test.com/cards/" + cardId + "?key=test&token=test" +
                "&name=" + repairDto().getCar() +
                "&desc=" + repairDto().getDamageDescription() + "%20" + repairDto().getServiceTechnician() +
                "&pos=top&idList=" + idList +
                "&id=" + cardId);
        assertThat(uri).isEqualTo(expected);
    }

    @Test
    void getCardDataListTest() throws URISyntaxException {
        // Given
        setTrelloValues();
        when(trelloConfig.getTrelloBoard()).thenReturn("test");
        TrelloCardDto[] trelloCardDtos = new TrelloCardDto[1];
        trelloCardDtos[0] = new TrelloCardDto(
                "CardName", "CardDesc", "CardPos", "CardId", "ListId");
        URI url = new URI("https://test.com/boards/test/cards?key=test&token=test&fields=id,name,desc,idList,pos");
        when(restTemplate.getForObject(url, TrelloCardDto[].class)).thenReturn(trelloCardDtos);

        // When
        List<TrelloCardDto> fetchedTrelloCardData = trelloClient.getCardDataList();

        // Then
        assertEquals(1, fetchedTrelloCardData.size());
        assertEquals("CardName", fetchedTrelloCardData.get(0).getName());
        assertEquals("CardDesc", fetchedTrelloCardData.get(0).getDesc());
        assertEquals("CardPos", fetchedTrelloCardData.get(0).getPos());
        assertEquals("ListId", fetchedTrelloCardData.get(0).getIdList());
    }
}