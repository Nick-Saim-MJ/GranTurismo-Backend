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

@Table(name = "servicio_alimentacion")
public class ServicioAlimentacion {

    public enum Bebida { SI, NO}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alimentacion")
    private Long idAlimentacion;

    @Column(name = "tipo_comida", nullable = false)
    private String tipoComida;

    @Column(name = "estilo_gastronomico", nullable = false)
    private String estiloGastronomico;

    @Column(name = "incluye_bebidas", nullable = false)
    @Enumerated(EnumType.STRING)
    private Bebida incluyeBebidas;

    @ManyToOne
    @JoinColumn(name = "id_servicio", referencedColumnName = "id_servicio",
            nullable = false, foreignKey = @ForeignKey(name = "FK_SERVICIOHOTELERIA_SERVICIO"))
    private Servicio servicio;
}