package org.example.granturismo.servicio;

import org.example.granturismo.dtos.ResenaDTO;
import org.example.granturismo.dtos.TipoServicioDTO;
import org.example.granturismo.modelo.Resena;
import org.example.granturismo.modelo.TipoServicio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITipoServicioService extends ICrudGenericoService<TipoServicio, Long> {

    TipoServicioDTO saveD(TipoServicioDTO.TipoServicioCADTO dto);

    TipoServicioDTO updateD(TipoServicioDTO.TipoServicioCADTO dto, Long id);

    Page<TipoServicio> listaPage(Pageable pageable);
}
