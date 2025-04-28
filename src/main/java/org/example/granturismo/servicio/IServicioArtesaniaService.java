package org.example.granturismo.servicio;


import org.example.granturismo.dtos.ServicioArtesaniaDTO;
import org.example.granturismo.modelo.ServicioArtesania;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IServicioArtesaniaService extends ICrudGenericoService<ServicioArtesania, Long> {

    ServicioArtesaniaDTO saveD(ServicioArtesaniaDTO.ServicioArtesaniaCADTO dto);

    ServicioArtesaniaDTO updateD(ServicioArtesaniaDTO.ServicioArtesaniaCADTO dto, Long id);

    Page<ServicioArtesania> listaPage(Pageable pageable);
}
