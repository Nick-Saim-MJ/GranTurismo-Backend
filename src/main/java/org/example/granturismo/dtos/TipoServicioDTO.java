package org.example.granturismo.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TipoServicioDTO {
    private Long idTipo;
    private String nombre;
    private String descripcion;




    public record TipoServicioCADTO(
            Long idTipo,
            String nombre,
            String descripcion
    ){}
}
