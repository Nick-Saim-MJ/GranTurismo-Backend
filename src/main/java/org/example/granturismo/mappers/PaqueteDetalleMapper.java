package org.example.granturismo.mappers;

import org.example.granturismo.dtos.PaqueteDetalleDTO;
import org.example.granturismo.modelo.PaqueteDetalle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaqueteDetalleMapper extends GenericMapper<PaqueteDetalleDTO, PaqueteDetalle>{

    @Mapping(target = "paquete", ignore = true)
    @Mapping(target = "servicio", ignore = true)

    PaqueteDetalle toEntityFromCADTO(PaqueteDetalleDTO.PaqueteDetalleCADTO paqueteDetalleCrearDTO);

}
