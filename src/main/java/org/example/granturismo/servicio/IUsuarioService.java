package org.example.granturismo.servicio;

import org.example.granturismo.dtos.UsuarioDTO;
import org.example.granturismo.modelo.Usuario;

import java.util.Optional;

public interface IUsuarioService extends ICrudGenericoService<Usuario, Long> {
    public UsuarioDTO login(UsuarioDTO.CredencialesDto credentialsDto);

    public UsuarioDTO registerUser(UsuarioDTO.UsuarioCrearDto userDto);
    public UsuarioDTO registerByAdmin(UsuarioDTO.UsuarioCrearConRolDto userDto);
    public Usuario verificarCuenta(String token);
    Optional<UsuarioDTO> findByUser(String username);


}