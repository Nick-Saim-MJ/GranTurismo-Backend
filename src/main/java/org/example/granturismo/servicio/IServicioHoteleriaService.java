package org.example.granturismo.servicio;

import org.example.granturismo.dtos.ServicioHoteleriaDTO;
import org.example.granturismo.modelo.ServicioHoteleria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IServicioHoteleriaService extends ICrudGenericoService<ServicioHoteleria, Long>{

    ServicioHoteleriaDTO saveD(ServicioHoteleriaDTO.ServicioHoteleriaCADTO dto);

    ServicioHoteleriaDTO updateD(ServicioHoteleriaDTO.ServicioHoteleriaCADTO dto, Long id);

    Page<ServicioHoteleria> listaPage(Pageable pageable);
}
