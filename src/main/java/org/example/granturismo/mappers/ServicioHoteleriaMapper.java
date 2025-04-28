package org.example.granturismo.mappers;


import org.example.granturismo.dtos.ServicioHoteleriaDTO;
import org.example.granturismo.modelo.ServicioHoteleria;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ServicioHoteleriaMapper extends GenericMapper<ServicioHoteleriaDTO, ServicioHoteleria>{

    @Mapping(target = "servicio", ignore = true)

    ServicioHoteleria toEntityFromCADTO(ServicioHoteleriaDTO.ServicioHoteleriaCADTO serviciohoteleraCrearDTO);
}
