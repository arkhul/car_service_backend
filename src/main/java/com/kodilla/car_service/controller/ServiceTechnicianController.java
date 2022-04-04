package com.kodilla.car_service.controller;

import com.kodilla.car_service.domain.ServiceTechnician;
import com.kodilla.car_service.dto.ServiceTechnicianDto;
import com.kodilla.car_service.exception.ServiceTechnicianNotFoundException;
import com.kodilla.car_service.mapper.ServiceTechnicianMapper;
import com.kodilla.car_service.observer.MessageForServiceTechnician;
import com.kodilla.car_service.service.ServiceTechnicianService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class ServiceTechnicianController {

    private final ServiceTechnicianMapper serviceTechnicianMapper;

    private final ServiceTechnicianService serviceTechnicianService;

    private final MessageForServiceTechnician messageForServiceTechnician;

    @RequestMapping(method = RequestMethod.GET, value = "/technicians")
    public List<ServiceTechnicianDto> getServiceTechnicians() {
        return serviceTechnicianMapper.mapToServiceTechnicianDtoList(
                serviceTechnicianService.getServiceTechnicians());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/technicians/{id}")
    public ServiceTechnicianDto getServiceTechnician(@PathVariable Long id) throws Exception {
        return serviceTechnicianMapper.mapToServiceTechnicianDto(serviceTechnicianService.getServiceTechnician(id)
                .orElseThrow(ServiceTechnicianNotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/technicians", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ServiceTechnician createServiceTechnician(@RequestBody ServiceTechnicianDto serviceTechnicianDto) {
        messageForServiceTechnician.addObserver(serviceTechnicianDto);
        return serviceTechnicianService.saveServiceTechnician(serviceTechnicianMapper.
                mapToServiceTechnician(serviceTechnicianDto));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/technicians")
    public ServiceTechnicianDto updateServiceTechnician(@RequestBody ServiceTechnicianDto serviceTechnicianDto) {
        return serviceTechnicianMapper.mapToServiceTechnicianDto(serviceTechnicianService
                .saveServiceTechnician(serviceTechnicianMapper.mapToServiceTechnician(serviceTechnicianDto)));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/technicians/{id}")
    public void deleteServiceTechnician(@PathVariable Long id) throws ServiceTechnicianNotFoundException {
        ServiceTechnicianDto serviceTechnicianDto = serviceTechnicianMapper
                .mapToServiceTechnicianDto(serviceTechnicianService.getServiceTechnician(id)
                        .orElseThrow(ServiceTechnicianNotFoundException::new));
        messageForServiceTechnician.removeObserver(serviceTechnicianDto);
        serviceTechnicianService.deleteServiceTechnician(id);
    }
}