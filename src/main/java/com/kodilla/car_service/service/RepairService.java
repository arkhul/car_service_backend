package com.kodilla.car_service.service;

import com.kodilla.car_service.domain.Repair;
import com.kodilla.car_service.repository.RepairRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RepairService {

    private final RepairRepository repairRepository;

    public List<Repair> getRepairs() {
        return repairRepository.findAll();
    }

    public Optional<Repair> getRepair(final Long repairId) {
        return repairRepository.findById(repairId);
    }

    public Repair saveRepair(final Repair repair) {
        return repairRepository.save(repair);
    }
}
