package org.example.granturismo.repositorio;

import org.example.granturismo.modelo.Usuario;

import java.util.Optional;

public interface IUsuarioRepository extends ICrudGenericoRepository<Usuario, Long>{

    Optional<Usuario> findOneByUser(String user);
}
