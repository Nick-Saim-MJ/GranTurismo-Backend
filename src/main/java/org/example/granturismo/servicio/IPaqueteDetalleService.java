package org.example.granturismo.servicio;

import org.example.granturismo.dtos.PaqueteDetalleDTO;
import org.example.granturismo.modelo.PaqueteDetalle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPaqueteDetalleService extends ICrudGenericoService<PaqueteDetalle, Long> {

    PaqueteDetalleDTO saveD(PaqueteDetalleDTO.PaqueteDetalleCADTO dto);

    PaqueteDetalleDTO updateD(PaqueteDetalleDTO.PaqueteDetalleCADTO dto, Long id);

    Page<PaqueteDetalle> listaPage(Pageable pageable);
}