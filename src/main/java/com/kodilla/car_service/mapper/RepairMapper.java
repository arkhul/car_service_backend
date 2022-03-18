package com.kodilla.car_service.mapper;

import com.kodilla.car_service.domain.Repair;
import com.kodilla.car_service.dto.RepairDto;
import com.kodilla.car_service.repository.CarRepository;
import com.kodilla.car_service.repository.CostRepository;
import com.kodilla.car_service.repository.ServiceTechnicianRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RepairMapper {

    private final CarRepository carRepository;

    private final ServiceTechnicianRepository serviceTechnicianRepository;

    private final CostRepository costRepository;

    public Repair mapToRepair(final RepairDto repairDto) {
        return new Repair(
                repairDto.getId(),
                repairDto.getDamageDescription(),
                carRepository.findByVin(repairDto.getCar()),
                serviceTechnicianRepository.findByName(repairDto.getServiceTechnician()),
                costRepository.findByTotalCost(repairDto.getCost())
        );
    }

    public RepairDto mapToRepairDto(final Repair repair) {
        return new RepairDto(
                repair.getId(),
                repair.getDamageDescription(),
                repair.getRepairStatus().name(),
                repair.getAdmissionDate(),
                repair.getReleaseDate(),
                repair.getRepairDescription(),
                repair.getRepairTimeInManHours(),
                repair.getCar().getVin(),
                repair.getServiceTechnician().getName(),
                repair.getCost().getTotalCost()
        );
    }

    public List<RepairDto> mapToRepairDtoList(final List<Repair> repairList) {
        return repairList.stream()
                .map(this::mapToRepairDto)
                .collect(Collectors.toList());
    }
}

