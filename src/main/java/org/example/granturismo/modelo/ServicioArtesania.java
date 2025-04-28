package org.example.granturismo.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity

@Table(name = "servicio_artesania")
public class ServicioArtesania {

    public enum Nivel { PRINCIPIANTE, INTERMEDIO, AVANZADO}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_artesania")
    private Long idArtesania;

    @Column(name = "tipo_artesania", nullable = false)
    private String tipoArtesania;

    @Column(name = "nivel_dificultad", nullable = false)
    @Enumerated(EnumType.STRING)
    private Nivel nivelDificultad;

    @Column(name = "duracion_taller", nullable = false)
    private Integer duracionTaller;

    @Column(name = "incluye_material", nullable = false)
    private Boolean incluyeMaterial;

    @Column(name = "artesania", nullable = false, columnDefinition = "TEXT")
    private String artesania;

    @Column(name = "origen_cultural", nullable = false)
    private String origenCultural;

    @Column(name = "max_participantes", nullable = false)
    private Integer maxParticipantes;

    @Column(name = "visita_taller", nullable = false)
    private Boolean visitaTaller;

    @Column(name = "artesano", nullable = false)
    private String artesano;

    @ManyToOne
    @JoinColumn(name = "id_servicio", referencedColumnName = "id_servicio",
            nullable = false, foreignKey = @ForeignKey(name = "FK_SERVICIOHOTELERIA_SERVICIO"))
    private Servicio servicio;
}