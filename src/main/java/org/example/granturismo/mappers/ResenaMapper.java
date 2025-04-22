package org.example.granturismo.mappers;

import org.example.granturismo.dtos.ProveedorDTO;
import org.example.granturismo.dtos.ResenaDTO;
import org.example.granturismo.modelo.Proveedor;
import org.example.granturismo.modelo.Resena;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ResenaMapper extends GenericMapper<ResenaDTO, Resena> {

    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "paquete", ignore = true)
    Resena toEntityFromCADTO(ResenaDTO.ResenaCADTO resenaCrearDTO);
}
