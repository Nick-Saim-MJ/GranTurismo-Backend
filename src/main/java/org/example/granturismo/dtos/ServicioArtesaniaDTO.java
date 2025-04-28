package org.example.granturismo.dtos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.granturismo.modelo.Servicio;
import org.example.granturismo.modelo.ServicioArtesania;
import org.example.granturismo.modelo.ServicioHoteleria;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ServicioArtesaniaDTO {


    private Long idArtesania;
    private String tipoArtesania;
    private ServicioArtesania.Nivel nivelDificultad;
    private Integer duracionTaller;
    private Boolean incluyeMaterial;
    private String artesania;
    private String origenCultural;
    private Integer maxParticipantes;
    private Boolean visitaTaller;
    private String artesano;

    private ServicioDTO servicio;



    public record ServicioArtesaniaCADTO(
            Long idArtesania,
            String tipoArtesania,
            Integer estrellas,
            ServicioArtesania.Nivel nivelDificultad,
            Integer duracionTaller,
            Boolean incluyeMaterial,
            String artesania,
            String origenCultural,
            Integer maxParticipantes,
            Boolean visitaTaller,
            String artesano,
            Long servicio

    ){}
}
