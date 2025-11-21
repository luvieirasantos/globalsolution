package com.fiap.globalsolution.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    private static final Logger logger = LoggerFactory.getLogger(EmailConsumer.class);

    @RabbitListener(queues = "emailQueue")
    public void processEmail(EmailMessage message) {
        logger.info("Processing email message: {}", message);

        // Simula o envio de email
        try {
            // Aqui seria a lógica real de envio de email
            // Por exemplo, usando JavaMailSender
            Thread.sleep(1000); // Simula processamento

            logger.info("Email sent successfully to: {}", message.getTo());
        } catch (InterruptedException e) {
            logger.error("Error processing email: {}", e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
