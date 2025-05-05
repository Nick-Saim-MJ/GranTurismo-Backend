package org.example.granturismo.mappers;

import org.example.granturismo.dtos.ActividadDetalleDTO;
import org.example.granturismo.modelo.ActividadDetalle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ActividadDetalleMapper extends GenericMapper<ActividadDetalleDTO, ActividadDetalle> {

    @Mapping(target = "paquete", ignore = true)
    @Mapping(target = "actividad", ignore = true)
    ActividadDetalle toEntityFromCADTO(ActividadDetalleDTO.ActividadDetalleCADTO actividadDetalleCrearDTO);
}
