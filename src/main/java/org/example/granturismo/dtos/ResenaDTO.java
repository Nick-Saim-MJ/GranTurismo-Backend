package org.example.granturismo.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.granturismo.modelo.Paquete;
import org.example.granturismo.modelo.Usuario;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResenaDTO {

    private Long idResena;
    private String comentario;
    private Integer calificacion;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fecha;
    private UsuarioDTO usuario;
    private PaqueteDTO paquete;



    public record ResenaCADTO(
            Long idResena,
            String comentario,
            Integer calificacion,
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            LocalDateTime fecha,
            Long usuario,
            Long paquete
    ){}


}
