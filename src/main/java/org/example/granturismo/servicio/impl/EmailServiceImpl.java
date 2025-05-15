package org.example.granturismo.servicio.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.granturismo.servicio.IEmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements IEmailService {

    private final JavaMailSender javaMailSender;

    @Value("${app.frontend.verification-url}")
    private String urlVerificacion;

    @Override
    public void enviarCorreoVerificacion(String destinatario, String token) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            String link = urlVerificacion + "?token=" + token;

            helper.setTo(destinatario);
            helper.setSubject("Verificación de cuenta");
            helper.setText("<h3>Bienvenido</h3><p>Verifica tu cuenta dando clic <a href=\"" + link + "\">aquí</a>.</p>", true);

            javaMailSender.send(message);
            log.info("Correo de verificación enviado a {}", destinatario);
        } catch (MessagingException e) {
            log.error("Error enviando correo de verificación", e);
        }
    }
}
