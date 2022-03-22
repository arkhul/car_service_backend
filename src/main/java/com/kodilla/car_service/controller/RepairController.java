package com.kodilla.car_service.controller;

import com.kodilla.car_service.domain.Cost;
import com.kodilla.car_service.domain.Repair;
import com.kodilla.car_service.domain.ServiceTechnician;
import com.kodilla.car_service.dto.RepairDto;
import com.kodilla.car_service.dto.TrelloCardDto;
import com.kodilla.car_service.exception.RepairNotFoundException;
import com.kodilla.car_service.mapper.RepairMapper;
import com.kodilla.car_service.repairStatus.RepairStatus;
import com.kodilla.car_service.service.CostService;
import com.kodilla.car_service.service.RepairService;
import com.kodilla.car_service.service.ServiceTechnicianService;
import com.kodilla.car_service.trello.client.TrelloClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class RepairController {

    private final RepairMapper repairMapper;

    private final RepairService repairService;

    private final TrelloClient trelloClient;

    private final CostService costService;

    private final ServiceTechnicianService serviceTechnicianService;

    @RequestMapping(method = RequestMethod.GET, value = "/repairs")
    public List<RepairDto> getRepairs() {
        return repairMapper.mapToRepairDtoList(repairService.getRepairs());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/repairs", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createRepair(@RequestBody RepairDto repairDto) {
        repairService.saveRepair(repairMapper.mapToRepair(repairDto));
        trelloClient.createCard(repairDto);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/repairs/start/{repairId}/{technicianName}")
    public RepairDto startRepair(@PathVariable Long repairId, @PathVariable String technicianName) throws RepairNotFoundException {
        Repair repair = repairService.getRepair(repairId).orElseThrow(RepairNotFoundException::new);
        repair.setRepairStatus(RepairStatus.IN_PROGRESS);
        ServiceTechnician serviceTechnician = serviceTechnicianService.findByName(technicianName);
        repair.setServiceTechnician(serviceTechnician);
        String email = repair.getCar().getClient().getEmail();
        RepairDto repairDto = repairMapper.mapToRepairDto(repair);
        String vin = repair.getCar().getVin();
        List<String> cardIdList = trelloClient.getCardDataList().stream()
                .filter(c -> c.getName().equals(vin))
                .map(TrelloCardDto::getId)
                .collect(Collectors.toList());
        String cardId = cardIdList.get(0);
        trelloClient.updateCardAfterStartRepair(cardId, email, repairDto);
        return repairMapper.mapToRepairDto(repairService.saveRepair(repair));
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/repairs/end/{repairId}/{repairDesc}/{repairTime}/{partsCost}")
    public RepairDto endRepair(@PathVariable Long repairId, @PathVariable String repairDesc, @PathVariable BigDecimal repairTime,
                               @PathVariable BigDecimal partsCost) throws RepairNotFoundException {
        Repair repair = repairService.getRepair(repairId).orElseThrow(RepairNotFoundException::new);
        BigDecimal labour_cost = repair.getServiceTechnician().getManHourRate().multiply(repairTime);
        Cost cost = new Cost(partsCost, labour_cost, partsCost.add(labour_cost));
        costService.saveCost(cost);
        repair.setRepairStatus(RepairStatus.DONE);
        repair.setReleaseDate(LocalDate.now());
        repair.setRepairDescription(repairDesc);
        repair.setRepairTimeInManHours(repairTime);
        repair.setCost(cost);
        String email = repair.getCar().getClient().getEmail();
        RepairDto repairDto = repairMapper.mapToRepairDto(repair);
        String vin = repair.getCar().getVin();
        List<String> cardIdList = trelloClient.getCardDataList().stream()
                .filter(c -> c.getName().equals(vin))
                .map(TrelloCardDto::getId)
                .collect(Collectors.toList());
        String cardId = cardIdList.get(0);
        trelloClient.updateCardAfterEndRepair(cardId, email, repairDto);
        return repairMapper.mapToRepairDto(repairService.saveRepair(repair));
    }
}














