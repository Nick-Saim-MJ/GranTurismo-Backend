package org.example.granturismo.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.granturismo.modelo.Paquete;
import org.example.granturismo.modelo.Proveedor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaqueteDTO {
    private Long idPaquete;
    @NotNull(message = "El titulo no puede ser nulo")
    @Size(min = 2, max = 150, message = "El titulo debe tener entre 2 y 150 caracteres")
    private String titulo;

    @NotNull(message = "La descripción no puede ser nula")
    private String descripcion;

    private String imagenUrl;

    @NotNull(message = "El precio no puede ser nulo")
    @DecimalMin(value = "0.0", inclusive = true, message = "El precio debe ser mayor o igual a 0")
    private BigDecimal precioTotal;

    @NotNull(message = "El Estado no puede ser nulo")
    private Paquete.Estado estado;

    @NotNull(message = "La Duracion no puede ser nula")
    private Integer duracionDias;

    @NotNull(message = "La localidad no puede ser nula")
    private String localidad;

    @NotNull(message = "La Actividad no puede ser nula")
    private String tipoActividad;

    @NotNull(message = "Los Cupos no pueden ser nulos")
    private Integer cuposMaximos;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaInicio;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaFin;

    @NotNull(message = "El Proveedor no puede ser nulo")
    private ProveedorDTO proveedor;

    @NotNull(message = "El Destino no puede ser nulo")
    private DestinoDTO destino;


    public record PaqueteCADTO(
            Long idPaquete,
            @NotNull(message = "El Titulo no puede ser nulo")
            @Size(min = 2, max = 150, message = "El titulo debe tener entre 2 y 150 caracteres")
            String titulo,
            @NotNull(message = "La Descripción no puede ser nula")
            String descripcion,
            String imagenUrl,
            @NotNull(message = "El Precio no puede ser nulo")
            BigDecimal precioTotal,
            @NotNull
            Paquete.Estado estado,
            @NotNull
            Integer duracionDias,
            @NotNull(message = "La Localidad no puede ser nula")
            String localidad,
            @NotNull(message = "La Actividad no puede ser nula")
            String tipoActividad,
            @NotNull(message = "Los Cupos no pueden ser nulos")
            Integer cuposMaximos,
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            LocalDateTime fechaInicio,
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            LocalDateTime fechaFin,
            @NotNull(message = "El Proveedor no puede ser nulo")
            Long proveedor,
            @NotNull(message = "El Destino no puede ser nulo")
            Long destino

    ){}
}