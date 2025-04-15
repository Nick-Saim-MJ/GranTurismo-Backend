package org.example.granturismo.servicio;

import org.example.granturismo.dtos.UsuarioDTO;
import org.example.granturismo.modelo.Usuario;

public interface IUsuarioService extends ICrudGenericoService<Usuario, Long> {
    public UsuarioDTO login(UsuarioDTO.CredencialesDto credentialsDto);
    public UsuarioDTO register(UsuarioDTO.UsuarioCrearDto userDto);
}