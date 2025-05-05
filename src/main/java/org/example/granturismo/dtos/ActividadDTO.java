package org.example.granturismo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ActividadDTO {

    private Long idActividad;
    private String titulo;
    private String descripcion;
    private String tipo;
    private Integer duracionHoras;
    private String imagenUrl;
    private BigDecimal precioBase;


    public record ActividadCADTO(
            Long idActividad,
            String titulo,
            String descripcion,
            String tipo,
            Integer duracionHoras,
            String imagenUrl,
            BigDecimal precioBase
    ){}

}

