package org.example.granturismo.servicio;

import org.example.granturismo.dtos.DestinoDTO;
import org.example.granturismo.dtos.PaqueteDTO;
import org.example.granturismo.modelo.Destino;
import org.example.granturismo.modelo.Paquete;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IDestinoService extends ICrudGenericoService<Destino, Long> {

    DestinoDTO saveD(DestinoDTO.DestinoCADTO dto);

    DestinoDTO updateD(DestinoDTO.DestinoCADTO dto, Long id);

    Page<Destino> listaPage(Pageable pageable);
}
