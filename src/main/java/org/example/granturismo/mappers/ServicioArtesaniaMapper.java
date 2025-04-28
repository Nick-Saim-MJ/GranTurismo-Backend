package org.example.granturismo.mappers;

import org.example.granturismo.dtos.ServicioArtesaniaDTO;
import org.example.granturismo.modelo.ServicioArtesania;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ServicioArtesaniaMapper extends GenericMapper<ServicioArtesaniaDTO, ServicioArtesania>{

    @Mapping(target = "servicio", ignore = true)
    ServicioArtesania toEntityFromCADTO(ServicioArtesaniaDTO.ServicioArtesaniaCADTO servicioartesaniaCrearDTO);
}
