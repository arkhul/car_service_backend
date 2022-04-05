package com.kodilla.car_service.controller;

import com.kodilla.car_service.dto.TrelloCardDto;
import com.kodilla.car_service.trello.client.TrelloClient;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringJUnitWebConfig
@WebMvcTest(TrelloController.class)
class TrelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TrelloController trelloController;

    @MockBean
    private TrelloClient trelloClient;

    @Test
    void getToDoCards() throws Exception {
        // Given
        when(trelloController.getToDoCards()).thenReturn(
                List.of(new TrelloCardDto("Card1", "Desc1", null, "CardId", null)));

        // When & Then
        mockMvc
                .perform(get("/v1/trello/todo")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("Card1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].desc", Matchers.is("Desc1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is("CardId")));
    }

    @Test
    void getInProgressCards() throws Exception {
        // Given
        when(trelloController.getInProgressCards()).thenReturn(
                List.of(new TrelloCardDto("Card1", "Desc1", null, "CardId", null)));

        // When & Then
        mockMvc
                .perform(get("/v1/trello/inprogress")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("Card1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].desc", Matchers.is("Desc1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is("CardId")));
    }
}