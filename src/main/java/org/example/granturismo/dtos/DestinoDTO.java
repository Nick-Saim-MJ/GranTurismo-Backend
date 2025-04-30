package org.example.granturismo.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class DestinoDTO {

    private Long idDestino;
    private String nombre;
    private String descripcion;
    private String ubicacion;
    private String imagenUrl;
    private Double latitud;
    private Double longitud;
    private Integer popularidad;
    private BigDecimal preciomedio;
    private Float rating;



    public record DestinoCADTO(
            Long idDestino,
            String nombre,
            String descripcion,
            String ubicacion,
            String imagenUrl,
            String latitud,
            String longitud,
            Integer popularidad,
            BigDecimal preciomedio,
            Float rating
    ){}
}


