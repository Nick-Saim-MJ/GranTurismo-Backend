package org.example.granturismo.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.granturismo.modelo.TipoServicio;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class ServicioDTO {

    private Long idServicio;
    private String nombreServicio;
    private String descripcion;
    private BigDecimal precioBase;
    private String estado;
    private TipoServicioDTO tipo;



    public record ServicioCADTO(
            Long idServicio,
            String nombreServicio,
            String descripcion,
            BigDecimal precioBase,
            String estado,
            Long tipo

    ){}
}