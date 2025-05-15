package org.example.granturismo.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity


@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "user", nullable = false, unique = true, length = 20)
    private String user;

    @Column(name = "clave", nullable = false, length = 100)
    private String clave;

    @Column(name = "email", nullable = false, unique = true, length = 50)
    private String email;

    @Column(name = "token_verificacion", unique = true)
    private String tokenVerificacion;

    @Column(name = "fecha_expiracion_token")
    private LocalDateTime fechaExpiracionToken = LocalDateTime.now().plusDays(1); // por ejemplo


    @Column(name = "verificado", length = 2)
    private String verificado = "NO";

    // Métodos auxiliares para facilitar el uso como booleano en código:

    public boolean isVerificado() {
        return "SI".equalsIgnoreCase(this.verificado);
    }

    public void setVerificado(boolean verificado) {
        this.verificado = verificado ? "SI" : "NO";
    }
    // getter y setter string (para mapear directamente)
    public String getVerificadoStr() {
        return verificado;
    }

    public void setVerificadoStr(String verificado) {
        this.verificado = verificado;
    }


}