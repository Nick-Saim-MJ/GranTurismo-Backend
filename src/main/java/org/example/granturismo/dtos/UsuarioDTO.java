package org.example.granturismo.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UsuarioDTO {
    private Long idUsuario;
    @NotNull
    private String user;
    @NotNull
    private String email;
    private String verificado;

    public record CredencialesDto(String user, char[] clave) { }

    public record UsuarioCrearDto(String user, char[] clave, @Email String email) { }

    // Para admins (incluye rol explícito)
    public record UsuarioCrearConRolDto(String user, char[] clave, String rol, @Email String email) {}
}
