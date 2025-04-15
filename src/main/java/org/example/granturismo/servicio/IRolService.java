package org.example.granturismo.servicio;

import org.example.granturismo.modelo.Rol;

import java.util.Optional;

public interface IRolService extends ICrudGenericoService<Rol, Long> {
    public Optional<Rol> getByNombre(Rol.RolNombre rolNombre);
}
