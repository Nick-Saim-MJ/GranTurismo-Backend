package org.example.granturismo.servicio;

import org.example.granturismo.dtos.ActividadDetalleDTO;
import org.example.granturismo.modelo.ActividadDetalle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IActividadDetalleService extends ICrudGenericoService<ActividadDetalle, Long> {

    ActividadDetalleDTO saveD(ActividadDetalleDTO.ActividadDetalleCADTO dto);

    ActividadDetalleDTO updateD(ActividadDetalleDTO.ActividadDetalleCADTO dto, Long id);

    Page<ActividadDetalle> listaPage(Pageable pageable);
}
