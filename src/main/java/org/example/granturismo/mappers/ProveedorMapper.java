package org.example.granturismo.mappers;

import org.example.granturismo.dtos.PaqueteDTO;
import org.example.granturismo.dtos.ProveedorDTO;
import org.example.granturismo.modelo.Proveedor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProveedorMapper extends GenericMapper<ProveedorDTO, Proveedor> {

    @Mapping(target = "usuario", ignore = true)
    Proveedor toEntityFromCADTO(ProveedorDTO.ProveedorCADTO proveedorCrearDTO);
}
