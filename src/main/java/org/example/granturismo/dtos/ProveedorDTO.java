package org.example.granturismo.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.granturismo.modelo.Usuario;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProveedorDTO {

    private Long idProveedor;
    private String nombreCompleto;
    private String email;
    private String telefono;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaRegistro;
    private UsuarioDTO usuario;


    public record ProveedorCADTO(
            Long idProveedor,
            String nombreCompleto,
            String email,
            String telefono,
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            LocalDateTime fechaRegistro,
            Long usuario
    ){}
}
