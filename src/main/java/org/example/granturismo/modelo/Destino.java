package org.example.granturismo.modelo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "destino")

public class Destino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_destino")
    private Long idDestino ;

    @Column(name = "nombre")
    private String nombre ;

    @Column(name = "descripcion", nullable = false, columnDefinition = "TEXT")
    private String descripcion ;

    @Column(name = "ubicacion")
    private String ubicacion ;

    @Column(name = "imagen_url")
    private String imagenUrl ;

    @Column(name = "latitud")
    private Double latitud ;

    @Column(name = "longitud")
    private Double longitud ;

    @Column(name = "popularidad")
    private Integer popularidad ;

    @Column(name = "precio_medio")
    private BigDecimal preciomedio ;

    @Column(name = "rating")
    private Float rating ;
}
