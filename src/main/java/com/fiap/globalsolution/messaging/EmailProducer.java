package com.fiap.globalsolution.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmailProducer {

    private static final Logger logger = LoggerFactory.getLogger(EmailProducer.class);

    private final RabbitTemplate rabbitTemplate;

    public EmailProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendEmail(String to, String subject, String body) {
        EmailMessage message = new EmailMessage(to, subject, body);
        rabbitTemplate.convertAndSend("emailQueue", message);
        logger.info("Email message sent to queue: {}", message);
    }

    public void sendWelcomeEmail(String email, String nome) {
        String subject = "Bem-vindo à Plataforma de Cursos Corporativos";
        String body = String.format("Olá %s,\n\nBem-vindo à nossa plataforma de cursos corporativos!\n\nAtenciosamente,\nEquipe Global Solution", nome);
        sendEmail(email, subject, body);
    }

    public void sendCourseCompletionEmail(String email, String nome, String cursoNome) {
        String subject = "Parabéns pela conclusão do curso!";
        String body = String.format("Olá %s,\n\nParabéns por concluir o curso '%s'!\n\nSeu certificado está disponível na plataforma.\n\nAtenciosamente,\nEquipe Global Solution", nome, cursoNome);
        sendEmail(email, subject, body);
    }
}
