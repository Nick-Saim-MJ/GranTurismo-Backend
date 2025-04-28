package org.example.granturismo.mappers;

import org.example.granturismo.dtos.DestinoDTO;
import org.example.granturismo.dtos.ProveedorDTO;
import org.example.granturismo.modelo.Destino;
import org.example.granturismo.modelo.Proveedor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DestinoMapper extends GenericMapper<DestinoDTO, Destino> {
    Destino toEntityFromCADTO(DestinoDTO.DestinoCADTO destinoCrearDTO);
}
