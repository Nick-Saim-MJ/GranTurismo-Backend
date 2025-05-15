package org.example.granturismo.servicio;

public interface IEmailService {
    void enviarCorreoVerificacion(String destinatario, String token);
}
