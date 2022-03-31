package com.kodilla.car_service.repair;

import com.kodilla.car_service.domain.*;
import com.kodilla.car_service.dto.TrelloCardDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class RepairFasadeTest {

    private final Client client = new Client(
            123456789L, "firstname", "lastname", "address", "email");

    private final Car car = new Car("vin", "make", "model", 2000, "engine", client);

    private final ServiceTechnician serviceTechnician = new ServiceTechnician(
            1L, "Nobody", new BigDecimal("1"), 1L);

    private final Cost cost = new Cost(new BigDecimal("1"), new BigDecimal("1"), new BigDecimal("1"));

    private final Repair repair = new Repair(1L, "DamageDesc", RepairStatus.TO_DO,
            LocalDate.of(2022, 2, 10), null,
            null, null, car, serviceTechnician, cost);

    @Test
    void changeDataAfterStartRepairTest() {
        // Given
        ServiceTechnician newServiceTechnician = new ServiceTechnician(
                1L, "John Doe", new BigDecimal("10"), 111222333L);

        // When
        repair.setRepairStatus(RepairStatus.IN_PROGRESS);
        repair.setServiceTechnician(newServiceTechnician);

        // Then
        assertEquals(RepairStatus.IN_PROGRESS, repair.getRepairStatus());
        assertEquals("John Doe", repair.getServiceTechnician().getName());
        assertNull(repair.getRepairDescription());
        assertNull(repair.getRepairTimeInManHours());
    }

    @Test
    void changeDataAfterEndRepairTest() {
        // Given
        String repairDesc = "RepairDesc";
        BigDecimal repairTime = new BigDecimal("50");
        BigDecimal partsCost = new BigDecimal("1000");
        ServiceTechnician newServiceTechnician = new ServiceTechnician(
                1L, "John Doe", new BigDecimal("10"), 111222333L);

        // When
        repair.setServiceTechnician(newServiceTechnician);
        BigDecimal labourCost = repair.getServiceTechnician().getManHourRate().multiply(repairTime);
        BigDecimal totalCost = partsCost.add(labourCost);
        Cost newRepairCost = new Cost(partsCost, labourCost, totalCost);
        repair.setRepairStatus(RepairStatus.DONE);
        repair.setReleaseDate(LocalDate.now());
        repair.setRepairDescription(repairDesc);
        repair.setRepairTimeInManHours(repairTime);
        repair.setCost(newRepairCost);

        // Then
        assertNotNull(repair.getRepairDescription());
        assertNotNull(repair.getRepairTimeInManHours());
        assertNotNull(repair.getReleaseDate());
        assertEquals("John Doe", repair.getServiceTechnician().getName());
        assertEquals(RepairStatus.DONE, repair.getRepairStatus());
        assertEquals("RepairDesc", repair.getRepairDescription());
        assertEquals(new BigDecimal("1500"), repair.getCost().getTotalCost());
    }

    @Test
    void getEmailAndCardIdTest() {
        // Given
        String email = repair.getCar().getClient().getEmail();
        String vin = repair.getCar().getVin();
        List<TrelloCardDto> trelloCardDtoList = Collections.singletonList(new TrelloCardDto(
                "vin", "desc", "pos", "id", "idList"));

        // When
        List<String> cardIdList = trelloCardDtoList.stream()
                .filter(c -> c.getName().equals(vin))
                .map(TrelloCardDto::getId)
                .collect(Collectors.toList());
        String cardId = cardIdList.get(0);

        // Then
        assertEquals("email", email);
        assertEquals("vin", vin);
        assertNotNull(cardId);
        assertEquals("id", cardId);
    }
}