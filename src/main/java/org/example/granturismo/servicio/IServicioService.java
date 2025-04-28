package org.example.granturismo.servicio;

import org.eclipse.jdt.internal.compiler.env.IModule;
import org.example.granturismo.dtos.ServicioDTO;
import org.example.granturismo.modelo.Servicio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IServicioService extends ICrudGenericoService <Servicio, Long>{

    ServicioDTO saveD(ServicioDTO.ServicioCADTO dto);

    ServicioDTO updateD(ServicioDTO.ServicioCADTO dto, Long id);

    Page<Servicio> listaPage(Pageable pageable);
}
