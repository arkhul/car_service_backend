package com.kodilla.car_service.controller;

import com.google.gson.Gson;
import com.kodilla.car_service.domain.Client;
import com.kodilla.car_service.dto.ClientDto;
import com.kodilla.car_service.mapper.ClientMapper;
import com.kodilla.car_service.service.ClientService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringJUnitWebConfig
@WebMvcTest(ClientController.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientController clientController;

    @MockBean
    private ClientMapper clientMapper;

    @MockBean
    private ClientService clientService;

    @Test
    void getClientsTest() throws Exception {
        // Given
        when(clientController.getClients()).thenReturn(
                List.of(new ClientDto(111111L, "Firstname", "Lastname",
                        "Address", "Email")));

        // When & Then
        mockMvc
                .perform(get("/v1/clients")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].phoneNumber", Matchers.is(111111)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstname", Matchers.is("Firstname")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastname", Matchers.is("Lastname")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].address", Matchers.is("Address")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email", Matchers.is("Email")));
    }

    @Test
    void createClientTest() throws Exception {
        // Given
        Client client = new Client(111111L, "FirstnameClient", "LastnameClient",
                "Address", "Email");
        ClientDto clientDto = new ClientDto(111111L, "Firstname", "Lastname",
                "Address", "Email");
        when(clientController.createClient(clientDto)).thenReturn(client);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(clientDto);

        // When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber", Matchers.is(111111)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname", Matchers.is("FirstnameClient")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastname", Matchers.is("LastnameClient")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address", Matchers.is("Address")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is("Email")));
    }

    @Test
    void updateClientTest() throws Exception {
        // Given
        ClientDto clientDto = new ClientDto(111111L, "Firstname", "Lastname",
                "Address", "Email");
        Gson gson = new Gson();
        String jsonContent = gson.toJson(clientDto);
        when(clientController.updateClient(any(ClientDto.class))).thenReturn(clientDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber", Matchers.is(111111)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname", Matchers.is("Firstname")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastname", Matchers.is("Lastname")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address", Matchers.is("Address")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is("Email")));
    }
}