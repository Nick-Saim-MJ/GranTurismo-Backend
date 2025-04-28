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

@Table(name = "servicio_hotelera")
public class ServicioHoteleria {

    public enum Desayuno { Si, No}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_hoteleria")
    private Long idHoteleria;

    @Column(name = "tipo_habitacion", nullable = false)
    private String tipoHabitacion;

    @Column(name = "estrellas", nullable = false)
    private Integer estrellas;

    @Column(name = "incluye_desayuno", nullable = false)
    @Enumerated(EnumType.STRING)
    private Desayuno incluyeDesayuno;

    @Column(name = "max_personas", nullable = false)
    private Integer maxPersonas;

    @ManyToOne
    @JoinColumn(name = "id_servicio", referencedColumnName = "id_servicio",
            nullable = false, foreignKey = @ForeignKey(name = "FK_SERVICIOHOTELERIA_SERVICIO"))
    private Servicio servicio;
}
