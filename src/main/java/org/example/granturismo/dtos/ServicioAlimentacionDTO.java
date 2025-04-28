package org.example.granturismo.dtos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.granturismo.modelo.ServicioAlimentacion;
import org.example.granturismo.modelo.ServicioArtesania;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class ServicioAlimentacionDTO {
    private Long idAlimentacion;
    private String tipoComida;
    private String estiloGastronomico;
    private ServicioAlimentacion.Bebida incluyeBebidas;
    private ServicioDTO servicio;



    public record ServicioAlimentacionCADTO(
            Long idAlimentacion,
            String tipoComida,
            String estiloGastronomico,
            ServicioAlimentacion.Bebida incluyeBebidas,
            Long servicio

    ){}
}