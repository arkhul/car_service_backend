package com.kodilla.car_service.service;

import com.kodilla.car_service.domain.ServiceTechnician;
import com.kodilla.car_service.repository.ServiceTechnicianRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServiceTechnicianService {

    private final ServiceTechnicianRepository serviceTechnicianRepository;

    public List<ServiceTechnician> getServiceTechnician() {
        return serviceTechnicianRepository.findAll();
    }

    public Optional<ServiceTechnician> getServiceTechnician(final Long id) {
        return serviceTechnicianRepository.findById(id);
    }

    public ServiceTechnician saveServiceTechnician(final ServiceTechnician serviceTechnician) {
        return serviceTechnicianRepository.save(serviceTechnician);
    }

    public void deleteServiceTechnician(final Long id) {
        serviceTechnicianRepository.deleteById(id);
    }

    public ServiceTechnician findByName(String name) {
        return serviceTechnicianRepository.findByName(name);
    }
}
