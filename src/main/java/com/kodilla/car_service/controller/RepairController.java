package com.kodilla.car_service.controller;

import com.kodilla.car_service.domain.Repair;
import com.kodilla.car_service.domain.ServiceTechnician;
import com.kodilla.car_service.dto.RepairDto;
import com.kodilla.car_service.exception.RepairNotFoundException;
import com.kodilla.car_service.mapper.RepairMapper;
import com.kodilla.car_service.repairStatus.RepairStatus;
import com.kodilla.car_service.service.RepairService;
import com.kodilla.car_service.service.ServiceTechnicianService;
import com.kodilla.car_service.trello.client.TrelloClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1")
public class RepairController {

    @Autowired
    private RepairMapper repairMapper;

    @Autowired
    private RepairService repairService;

    @Autowired
    private TrelloClient trelloClient;

    @Autowired
    private ServiceTechnicianService serviceTechnicianService;

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
        RepairDto repairDto = repairMapper.mapToRepairDto(repair);
        String vin = repair.getCar().getVin();
        List<String> cardIdList = trelloClient.getCardDataList().stream()
                .filter(c -> c.getName().equals(vin))
                .map(c -> c.getId())
                .collect(Collectors.toList());
        String cardId = cardIdList.get(0);
        trelloClient.updateCard(cardId, repairDto);
        return repairMapper.mapToRepairDto(repairService.saveRepair(repair));
    }
}














