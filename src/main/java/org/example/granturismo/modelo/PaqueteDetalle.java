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
@Table(name = "paquete_detalle")
public class PaqueteDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paquete_detalle")
    private Long idPaqueteDetalle;

    @JoinColumn(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "precio_especial", precision = 38, scale = 2)
    private BigDecimal precioEspecial;

    @ManyToOne
    @JoinColumn(name = "id_paquete", referencedColumnName = "id_paquete",
            nullable = false, foreignKey = @ForeignKey(name = "FK_PAQUETEDETALLE_PAQUETE"))
    private Paquete paquete;

    @ManyToOne
    @JoinColumn(name = "id_servicio", referencedColumnName = "id_servicio",
            nullable = false, foreignKey = @ForeignKey(name = "FK_PAQUETEDETALLE_SERVICIO"))
    private Servicio servicio;
}

