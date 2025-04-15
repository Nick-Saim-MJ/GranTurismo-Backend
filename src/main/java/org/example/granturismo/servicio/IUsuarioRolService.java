package org.example.granturismo.servicio;

import org.example.granturismo.modelo.UsuarioRol;

import java.util.List;

public interface IUsuarioRolService {
    List<UsuarioRol> findOneByUsuarioUser(String user);
    UsuarioRol save(UsuarioRol ur);
    //Usuario login(String usuario, String password);

}
