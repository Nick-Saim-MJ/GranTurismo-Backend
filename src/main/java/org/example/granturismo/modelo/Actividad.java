package org.example.granturismo.modelo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "actividades")

public class Actividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_actividad")
    private Long idActividad ;

    @Column(name = "titulo", nullable = false)
    private String titulo ;

    @Column(name = "descripcion", nullable = false, columnDefinition = "TEXT")
    private String descripcion ;

    @Column(name = "tipo", nullable = false)
    private String tipo ;

    @Column(name = "duracion_horas", nullable = false)
    private Integer duracionHoras ;

    @Column(name = "imagen_url", nullable = false)
    private String imagenUrl ;

    @Column(name = "precio_base", nullable = false)
    private BigDecimal precioBase ;

    @OneToMany(mappedBy = "actividad", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ActividadDetalle> actividadDetalles = new ArrayList<>();;

}
