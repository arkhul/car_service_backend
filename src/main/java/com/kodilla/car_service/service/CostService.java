package com.kodilla.car_service.service;

import com.kodilla.car_service.domain.Cost;
import com.kodilla.car_service.repository.CostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CostService {

    private final CostRepository costRepository;

    public List<Cost> getCosts() {
        return costRepository.findAll();
    }

    public Cost findByTotalCost(BigDecimal totalCost) {
        return costRepository.findByTotalCost(totalCost);
    }

    public void saveCost(final Cost cost) {
        costRepository.save(cost);
    }
}