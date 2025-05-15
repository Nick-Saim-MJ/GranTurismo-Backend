package org.example.granturismo.mappers;

import org.example.granturismo.dtos.UsuarioDTO;
import org.example.granturismo.modelo.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper extends GenericMapper<UsuarioDTO, Usuario> {

    @Mapping(target = "clave", ignore = true)
    Usuario toEntityFromCADTO(UsuarioDTO.UsuarioCrearDto usuarioCrearDto);

    @Mapping(target = "clave", ignore = true)
    Usuario toEntityFromAdminDTO(UsuarioDTO.UsuarioCrearConRolDto dto);

    @Mapping(target = "verificado", expression = "java(usuario.isVerificado() ? \"SI\" : \"NO\")")
    UsuarioDTO toDTO(Usuario usuario);

}
