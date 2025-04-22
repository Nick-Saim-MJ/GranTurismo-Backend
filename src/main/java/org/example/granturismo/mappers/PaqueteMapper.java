package org.example.granturismo.mappers;

import org.example.granturismo.dtos.PaqueteDTO;
import org.example.granturismo.modelo.Paquete;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaqueteMapper extends GenericMapper<PaqueteDTO, Paquete> {

    @Mapping(target = "proveedor", ignore = true)
    Paquete toEntityFromCADTO(PaqueteDTO.PaqueteCADTO paqueteCrearDTO);

}
