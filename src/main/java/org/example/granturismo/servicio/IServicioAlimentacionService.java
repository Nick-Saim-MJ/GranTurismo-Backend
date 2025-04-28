package org.example.granturismo.servicio;

import org.example.granturismo.dtos.ServicioAlimentacionDTO;
import org.example.granturismo.modelo.ServicioAlimentacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IServicioAlimentacionService extends ICrudGenericoService<ServicioAlimentacion, Long> {

    ServicioAlimentacionDTO saveD(ServicioAlimentacionDTO.ServicioAlimentacionCADTO dto);

    ServicioAlimentacionDTO updateD(ServicioAlimentacionDTO.ServicioAlimentacionCADTO dto, Long id);

    Page<ServicioAlimentacion> listaPage(Pageable pageable);
}
