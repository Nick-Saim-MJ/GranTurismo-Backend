package org.example.granturismo.servicio;

import net.sf.jasperreports.engine.JRException;
import org.example.granturismo.dtos.PaqueteDTO;
import org.example.granturismo.modelo.Paquete;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface IPaqueteService extends ICrudGenericoService<Paquete, Long> {


    PaqueteDTO saveD(PaqueteDTO.PaqueteCADTO dto);

    PaqueteDTO updateD(PaqueteDTO.PaqueteCADTO dto, Long id);

    Page<Paquete> listaPage(Pageable pageable);
}
