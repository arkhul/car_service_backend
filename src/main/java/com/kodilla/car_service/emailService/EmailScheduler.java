package com.kodilla.car_service.emailService;

import com.kodilla.car_service.domain.Mail;
import com.kodilla.car_service.domain.Repair;
import com.kodilla.car_service.repair.RepairStatus;
import com.kodilla.car_service.service.RepairService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class EmailScheduler {

    private static final String SUBJECT = "Daily";

    @Value("${spring.mail.bossMail}")
    private String bossMail;

    private final SimpleEmailService simpleEmailService;

    private final RepairService repairService;

    @Scheduled(cron = "0 0 19 * * MON-FRI")
    public void sendInformationEmail() {
        simpleEmailService.sendMail(
                new Mail(
                        bossMail,
                        SUBJECT,
                        messageContent()
                )
        );
    }

    private String messageContent() {
        return "Report of day: " + LocalDate.now() + "\n" +
                "Number of new cars accepted for service: " +
                repairService.getRepairs().stream()
                        .map(Repair::getAdmissionDate)
                        .filter(r -> r.equals(LocalDate.now()))
                        .count() + ".\n" +
                "Number of cars in the process of repair: " +
                repairService.getRepairs().stream()
                        .map(Repair::getRepairStatus)
                        .filter(c -> c.equals(RepairStatus.IN_PROGRESS))
                        .count() + ".\n" +
                "Number of cars whose repairs have been completed: " +
                repairService.getRepairs().stream()
                        .map(Repair::getAdmissionDate)
                        .filter(r -> r.equals(LocalDate.now()))
                        .count() + ".\n";
    }
}