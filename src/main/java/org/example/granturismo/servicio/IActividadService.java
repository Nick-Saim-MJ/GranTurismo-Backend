package org.example.granturismo.servicio;

import org.example.granturismo.dtos.ActividadDTO;
import org.example.granturismo.modelo.Actividad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IActividadService extends ICrudGenericoService<Actividad, Long> {

    ActividadDTO saveD(ActividadDTO.ActividadCADTO dto);

    ActividadDTO updateD(ActividadDTO.ActividadCADTO dto, Long id);

    Page<Actividad> listaPage(Pageable pageable);
}
