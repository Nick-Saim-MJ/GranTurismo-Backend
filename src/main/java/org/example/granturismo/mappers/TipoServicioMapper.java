package org.example.granturismo.mappers;

import org.example.granturismo.dtos.TipoServicioDTO;

import org.example.granturismo.modelo.TipoServicio;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TipoServicioMapper extends GenericMapper<TipoServicioDTO, TipoServicio> {

    TipoServicio toEntityFromCADTO(TipoServicioDTO.TipoServicioCADTO tipoServicioCrearDTO);
}
