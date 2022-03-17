package com.kodilla.car_service.mapper;

import com.kodilla.car_service.domain.Client;
import com.kodilla.car_service.dto.ClientDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientMapper {

    public Client mapToClient(final ClientDto clientDto) {
        return new Client(
                clientDto.getPhoneNumber(),
                clientDto.getFirstname(),
                clientDto.getLastname(),
                clientDto.getAddress(),
                clientDto.getEmail()
        );
    }

    public ClientDto mapToClientDto(final Client client) {
        return new ClientDto(
                client.getPhoneNumber(),
                client.getFirstname(),
                client.getLastname(),
                client.getAddress(),
                client.getEmail()
        );
    }

    public List<ClientDto> mapToClientDtoList(final List<Client> clientList) {
        return clientList.stream()
                .map(this::mapToClientDto)
                .collect(Collectors.toList());
    }
}
