package org.example.granturismo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.granturismo.modelo.Actividad;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ActividadDetalleDTO {

    private Long idActividadDetalle;
    private String titulo;
    private String descripcion;
    private String imagenUrl;
    private Integer orden;
    private PaqueteDTO paquete;
    private ActividadDTO actividad;


    public record ActividadDetalleCADTO(
            Long idActividadDetalle,
            String titulo,
            String descripcion,
            String imagenUrl,
            Integer orden,
            Long paquete,
            Long actividad
    ){}
}
