package org.example.granturismo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaqueteDetalleDTO {


    private Long idPaqueteDetalle;
    private Integer cantidad;
    private BigDecimal precioEspecial;
    private PaqueteDTO paquete;
    private ServicioDTO servicio;


    public record PaqueteDetalleCADTO(
            Long idPaqueteDetalle,
            Integer cantidad,
            BigDecimal precioEspecial,
            Long paquete,
            Long servicio
    ){}
}
