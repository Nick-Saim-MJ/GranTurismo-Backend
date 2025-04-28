package org.example.granturismo.mappers;

import org.example.granturismo.dtos.ServicioDTO;
import org.example.granturismo.dtos.TipoServicioDTO;
import org.example.granturismo.modelo.Servicio;
import org.example.granturismo.modelo.TipoServicio;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ServicioMapper extends GenericMapper<ServicioDTO, Servicio> {

    @Mapping(target = "tipo", ignore = true)
    Servicio toEntityFromCADTO(ServicioDTO.ServicioCADTO servicioCrearDTO);

}
