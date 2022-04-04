package com.kodilla.car_service.emailService;

import com.kodilla.car_service.domain.Mail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SimpleEmailServiceTest {

    @Autowired
    private SimpleEmailService simpleEmailService;

    @Test
    void sendMailTest() {
        // Given
        String[] mailTo = {"boss@test.com"};
        Mail mail = new Mail(mailTo[0], "Subject", "Message txt");

        // When
        SimpleMailMessage simpleMailMessage = simpleEmailService.createMailMessage(mail);

        // Then
        assertEquals(1, Objects.requireNonNull(simpleMailMessage.getTo()).length);
        assertEquals("Subject", simpleMailMessage.getSubject());
        assertEquals("Message txt", simpleMailMessage.getText());
    }
}