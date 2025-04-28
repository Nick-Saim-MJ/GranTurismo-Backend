package org.example.granturismo.repositorio;

import org.example.granturismo.modelo.Paquete;
import org.example.granturismo.modelo.Rol;

import java.util.Optional;

public interface IPaqueteRepository extends ICrudGenericoRepository<Paquete, Long> {

    Optional<Paquete> findByEstado(Paquete.Estado estado);
}
