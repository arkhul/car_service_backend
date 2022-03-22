package com.kodilla.car_service.controller;

import com.kodilla.car_service.domain.Client;
import com.kodilla.car_service.dto.ClientDto;
import com.kodilla.car_service.exception.ClientFoundInDatabaseException;
import com.kodilla.car_service.exception.ClientNotFoundException;
import com.kodilla.car_service.mapper.ClientMapper;
import com.kodilla.car_service.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class ClientController {

    private final ClientMapper clientMapper;

    private final ClientService clientService;

    @RequestMapping(method = RequestMethod.GET, value = "/clients")
    public List<ClientDto> getClients()  {
        return clientMapper.mapToClientDtoList(clientService.getClients());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/clients/{phoneNumber}")
    public ClientDto getClient(@PathVariable Long phoneNumber) throws Exception {
        return clientMapper.mapToClientDto(clientService.getClient(phoneNumber)
                .orElseThrow(ClientNotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/clients", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Client createClient(@RequestBody ClientDto clientDto) throws ClientFoundInDatabaseException {
        if (phoneNumbers().contains(clientDto.getPhoneNumber())) {
            throw new ClientFoundInDatabaseException();
        } else {
           return clientService.saveClient(clientMapper.mapToClient(clientDto));
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/clients")
    public ClientDto updateClient(@RequestBody ClientDto clientDto) {
        return clientMapper.mapToClientDto(clientService.saveClient(clientMapper.mapToClient(clientDto)));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/clients/{phoneNumber}")
    public void deleteClient(@PathVariable Long phoneNumber) throws ClientNotFoundException {
        if (!phoneNumbers().contains(phoneNumber)) {
            throw new ClientNotFoundException();
        } else {
            clientService.deleteClient(phoneNumber);
        }
    }

    private List<Long> phoneNumbers() {
        return clientService.getClients().stream()
                .map(Client::getPhoneNumber)
                .collect(Collectors.toList());
    }
}