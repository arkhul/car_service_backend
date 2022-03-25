package com.kodilla.car_service.repairFasade;

import com.kodilla.car_service.domain.Cost;
import com.kodilla.car_service.domain.Repair;
import com.kodilla.car_service.domain.ServiceTechnician;
import com.kodilla.car_service.dto.RepairDto;
import com.kodilla.car_service.dto.TrelloCardDto;
import com.kodilla.car_service.exception.RepairNotFoundException;
import com.kodilla.car_service.mapper.RepairMapper;
import com.kodilla.car_service.service.CostService;
import com.kodilla.car_service.service.RepairService;
import com.kodilla.car_service.service.ServiceTechnicianService;
import com.kodilla.car_service.trello.client.TrelloClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RepairFasade {

    private final RepairService repairService;

    private final ServiceTechnicianService serviceTechnicianService;

    private final RepairMapper repairMapper;

    private final TrelloClient trelloClient;

    private final CostService costService;

    public Repair startRepair(Long repairId, String technicianName) throws RepairNotFoundException {
        Repair repair = repairService.getRepair(repairId).orElseThrow(RepairNotFoundException::new);
        repair.setRepairStatus(RepairStatus.IN_PROGRESS);
        ServiceTechnician serviceTechnician = serviceTechnicianService.findByName(technicianName);
        repair.setServiceTechnician(serviceTechnician);
        getEmailAndCardId(repair);
        return repair;
    }

    public Repair endRepair(Long repairId, String repairDesc, BigDecimal repairTime, BigDecimal partsCost) throws RepairNotFoundException {
        Repair repair = repairService.getRepair(repairId).orElseThrow(RepairNotFoundException::new);
        BigDecimal labour_cost = repair.getServiceTechnician().getManHourRate().multiply(repairTime);
        Cost cost = new Cost(partsCost, labour_cost, partsCost.add(labour_cost));
        costService.saveCost(cost);
        repair.setRepairStatus(RepairStatus.DONE);
        repair.setReleaseDate(LocalDate.now());
        repair.setRepairDescription(repairDesc);
        repair.setRepairTimeInManHours(repairTime);
        repair.setCost(cost);
        getEmailAndCardId(repair);
        return repair;
    }

    private void getEmailAndCardId(Repair repair) {
        String email = repair.getCar().getClient().getEmail();
        RepairDto repairDto = repairMapper.mapToRepairDto(repair);
        String vin = repair.getCar().getVin();
        List<String> cardIdList = trelloClient.getCardDataList().stream()
                .filter(c -> c.getName().equals(vin))
                .map(TrelloCardDto::getId)
                .collect(Collectors.toList());
        String cardId = cardIdList.get(0);
        trelloClient.updateTheCardAfterRepairStatusChanges(cardId, email, repairDto);
    }
}
