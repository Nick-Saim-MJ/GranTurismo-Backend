package org.example.granturismo.modelo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "paquetes")
public class Paquete {

    public enum Estado { DISPONIBLE, AGOTADO}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paquete")
    private Long idPaquete ;

    @Column(name = "titulo", nullable = false, length = 150)
    private String titulo;

    @Column(name = "descripcion", nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "imagen_url", nullable = false)
    private String imagenUrl;

    @Column(name = "precio_total", nullable = false)
    private BigDecimal precioTotal;

    @Column(name = "estado", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private Estado estado;

    @Column(name = "duracion_dias", nullable = false)
    private Integer duracionDias;

    @Column(name = "localidad", nullable = false)
    private String localidad;

    @Column(name = "tipo_actividad", nullable = false)
    private String tipoActividad;

    @Column(name = "cupos_maximos", nullable = false)
    private Integer cuposMaximos;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDateTime fechaInicio;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "fecha_fin", nullable = false)
    private LocalDateTime fechaFin;

    @ManyToOne
    @JoinColumn(name = "id_proveedor", referencedColumnName = "id_proveedor",
            nullable = false, foreignKey = @ForeignKey(name = "FK_PAQUETE_PROVEEDOR"))
    private Proveedor proveedor;

    @ManyToOne
    @JoinColumn(name = "id_destino", referencedColumnName = "id_destino",
            nullable = false, foreignKey = @ForeignKey(name = "FK_PAQUETE_DESTINO"))
    private Destino destino;

    @OneToMany(mappedBy = "paquete", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaqueteDetalle> detalles;

    @OneToMany(mappedBy = "paquete", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ActividadDetalle> actividadDetalles;

}