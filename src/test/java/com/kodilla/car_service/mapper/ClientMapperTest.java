package com.kodilla.car_service.mapper;

import com.kodilla.car_service.domain.Client;
import com.kodilla.car_service.dto.ClientDto;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientMapperTest {

    private final ClientMapper clientMapper = new ClientMapper();

    @Test
    void mapToClientTest() {
        // Given
        ClientDto clientDto = new ClientDto(123456789L, "John", "Doe",
                "Address", "Email");

        // When
        Client client = clientMapper.mapToClient(clientDto);

        // Then
        assertEquals(123456789L, client.getPhoneNumber());
        assertEquals("John Doe", client.getFirstname() + " " + client.getLastname());
        assertEquals("Address", client.getAddress());
        assertEquals("Email", client.getEmail());
    }

    @Test
    void mapToClientDtoTest() {
        // Given
        Client client = new Client(111111111L, "John", "Smith",
                "Address", "Email");

        // When
        ClientDto clientDto = clientMapper.mapToClientDto(client);

        // Then
        assertEquals(111111111L, clientDto.getPhoneNumber());
        assertEquals("John", clientDto.getFirstname());
        assertEquals("Smith", clientDto.getLastname());
        assertEquals("Address", clientDto.getAddress());
        assertEquals("Email", clientDto.getEmail());
    }

    @Test
    void mapToClientDtoListTest() {
        // Given
        Client client1 = new Client(111111111L, "John", "Smith",
                "Address11", "Email11");
        Client client2 = new Client(222222222L, "John", "Doe",
                "Address22", "Email22");
        List<Client> clientList = Arrays.asList(client1, client2);

        // When
        List<ClientDto> clientDtoList = clientMapper.mapToClientDtoList(clientList);

        // Then
        assertEquals(2, clientDtoList.size());
        assertEquals(111111111L, clientDtoList.get(0).getPhoneNumber());
        assertEquals("Smith", clientDtoList.get(0).getLastname());
        assertEquals(222222222L, clientDtoList.get(1).getPhoneNumber());
        assertEquals("Doe", clientDtoList.get(1).getLastname());
    }
}