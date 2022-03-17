package com.kodilla.car_service.controller;

import com.kodilla.car_service.domain.ServiceTechnician;
import com.kodilla.car_service.dto.ServiceTechnicianDto;
import com.kodilla.car_service.exception.ServiceTechnicianNotFoundException;
import com.kodilla.car_service.mapper.ServiceTechnicianMapper;
import com.kodilla.car_service.service.ServiceTechnicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1")
public class ServiceTechnicianController {

    @Autowired
    private ServiceTechnicianMapper serviceTechnicianMapper;

    @Autowired
    private ServiceTechnicianService serviceTechnicianService;

    @RequestMapping(method = RequestMethod.GET, value = "/technician")
    public List<ServiceTechnicianDto> getServiceTechnicians() {
        return serviceTechnicianMapper.mapToServiceTechnicianDtoList(
                serviceTechnicianService.getServiceTechnician());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/technician/{id}")
    public ServiceTechnicianDto getServiceTechnician(@PathVariable Long id) throws Exception {
        return serviceTechnicianMapper.mapToServiceTechnicianDto(serviceTechnicianService.getServiceTechnician(id)
                .orElseThrow(ServiceTechnicianNotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/technician", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createServiceTechnician(@RequestBody ServiceTechnicianDto serviceTechnicianDto) {
        serviceTechnicianService.saveServiceTechnician(serviceTechnicianMapper.mapToServiceTechnician(serviceTechnicianDto));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/technician")
    public ServiceTechnicianDto updateServiceTechnician(@RequestBody ServiceTechnicianDto serviceTechnicianDto) {
        return serviceTechnicianMapper.mapToServiceTechnicianDto(serviceTechnicianService
                .saveServiceTechnician(serviceTechnicianMapper.mapToServiceTechnician(serviceTechnicianDto)));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/technician/{id}")
    public void deleteServiceTechnician(@PathVariable Long id) throws ServiceTechnicianNotFoundException {
        List<Long> theList = serviceTechnicianService.getServiceTechnician().stream()
                .map(ServiceTechnician::getId)
                .collect(Collectors.toList());
        if (!theList.contains(id)) {
            throw new ServiceTechnicianNotFoundException();
        } else {
            serviceTechnicianService.deleteServiceTechnician(id);
        }
    }
}
