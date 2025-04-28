package org.example.granturismo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.granturismo.modelo.ServicioHoteleria;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class ServicioHoteleriaDTO {
    private Long idHoteleria;
    private String tipoHabitacion;
    private Integer estrellas;
    private ServicioHoteleria.Desayuno incluyeDesayuno;
    private Integer maxPersonas;

    private ServicioDTO servicio;



    public record ServicioHoteleriaCADTO(
            Long idHoteleria,
            String tipoHabitacion,
            Integer estrellas,
            ServicioHoteleria.Desayuno incluyeDesayuno,
            Integer maxPersonas,
            Long servicio

    ){}
}

