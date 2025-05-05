package org.example.granturismo.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "actividad_detalle")

public class ActividadDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_actividad_detalle")
    private Long idActividadDetalle ;

    @Column(name = "titulo", nullable = false)
    private String titulo ;

    @Column(name = "descripcion", nullable = false, columnDefinition = "TEXT")
    private String descripcion ;

    @Column(name = "imagen_url", nullable = false)
    private String imagenUrl ;

    @Column(name = "orden", nullable = false)
    private Integer orden ;

    @ManyToOne
    @JoinColumn(name = "id_paquete", referencedColumnName = "id_paquete",
            nullable = false, foreignKey = @ForeignKey(name = "FK_ACTIVIDADDETALLE_PAQUETE"))
    private Paquete paquete;

    @ManyToOne
    @JoinColumn(name = "id_actividad", referencedColumnName = "id_actividad",
            nullable = false, foreignKey = @ForeignKey(name = "FK_ACTIVIDADDETALLE_ACTIVIDAD"))
    private Actividad actividad;

}
