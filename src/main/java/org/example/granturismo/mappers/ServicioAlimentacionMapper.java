package org.example.granturismo.mappers;


import org.example.granturismo.dtos.ServicioAlimentacionDTO;
import org.example.granturismo.modelo.ServicioAlimentacion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ServicioAlimentacionMapper extends GenericMapper<ServicioAlimentacionDTO, ServicioAlimentacion>{

    @Mapping(target = "servicio", ignore = true)
    ServicioAlimentacion toEntityFromCADTO(ServicioAlimentacionDTO.ServicioAlimentacionCADTO servicioalimentacionCrearDTO);
}
