package org.example.granturismo.servicio;

import org.example.granturismo.dtos.ResenaDTO;
import org.example.granturismo.modelo.Resena;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IResenaService extends ICrudGenericoService<Resena, Long> {

    ResenaDTO saveD(ResenaDTO.ResenaCADTO dto);

    ResenaDTO updateD(ResenaDTO.ResenaCADTO dto, Long id);

    Page<Resena> listaPage(Pageable pageable);
}
