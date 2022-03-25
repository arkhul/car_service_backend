package com.kodilla.car_service.controller;

import com.kodilla.car_service.dto.RepairDto;
import com.kodilla.car_service.exception.RepairNotFoundException;
import com.kodilla.car_service.mapper.RepairMapper;
import com.kodilla.car_service.repairFasade.RepairFasade;
import com.kodilla.car_service.service.RepairService;
import com.kodilla.car_service.trello.client.TrelloClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class RepairController {

    private final RepairMapper repairMapper;

    private final RepairService repairService;

    private final TrelloClient trelloClient;

    private final RepairFasade repairFasade;

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
    public RepairDto startRepair(@PathVariable Long repairId,
                                 @PathVariable String technicianName)
            throws RepairNotFoundException {
        return repairMapper.mapToRepairDto(repairService.saveRepair(
                repairFasade.startRepair(repairId, technicianName)));
    }

    @RequestMapping(method = RequestMethod.PATCH,
            value = "/repairs/end/{repairId}/{repairDesc}/{repairTime}/{partsCost}")
    public RepairDto endRepair(@PathVariable Long repairId,
                               @PathVariable String repairDesc,
                               @PathVariable BigDecimal repairTime,
                               @PathVariable BigDecimal partsCost) throws RepairNotFoundException {
        return repairMapper.mapToRepairDto(repairService.saveRepair(
                repairFasade.endRepair(repairId, repairDesc, repairTime, partsCost)));
    }
}














