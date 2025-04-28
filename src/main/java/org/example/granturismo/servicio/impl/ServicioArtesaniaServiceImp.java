package org.example.granturismo.servicio.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.granturismo.dtos.ServicioArtesaniaDTO;
import org.example.granturismo.dtos.ServicioHoteleriaDTO;
import org.example.granturismo.mappers.ServicioArtesaniaMapper;
import org.example.granturismo.mappers.ServicioHoteleriaMapper;
import org.example.granturismo.modelo.Servicio;
import org.example.granturismo.modelo.ServicioArtesania;
import org.example.granturismo.modelo.ServicioHoteleria;
import org.example.granturismo.repositorio.ICrudGenericoRepository;
import org.example.granturismo.repositorio.IServicioArtesaniaRepository;
import org.example.granturismo.repositorio.IServicioHoteleriaRepository;
import org.example.granturismo.repositorio.IServicioRepository;
import org.example.granturismo.servicio.IServicioArtesaniaService;
import org.example.granturismo.servicio.IServicioHoteleriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Service
@Transactional
@RequiredArgsConstructor
public class ServicioArtesaniaServiceImp extends CrudGenericoServiceImp<ServicioArtesania, Long> implements IServicioArtesaniaService {


    @Autowired
    private DataSource dataSource;

    private final IServicioArtesaniaRepository repo;
    private final ServicioArtesaniaMapper servicioArtesaniaMapper;
    private final IServicioRepository servicioRepository;

    @Override
    protected ICrudGenericoRepository<ServicioArtesania, Long> getRepo() {
        return repo;
    }

    @Override
    public ServicioArtesaniaDTO saveD(ServicioArtesaniaDTO.ServicioArtesaniaCADTO dto) {
        ServicioArtesania servicioArtesania = servicioArtesaniaMapper.toEntityFromCADTO(dto);

        Servicio servicio = servicioRepository.findById(dto.servicio())
                .orElseThrow(() -> new EntityNotFoundException("Servicio no encontrado"));

        servicioArtesania.setServicio(servicio);

        ServicioArtesania servicioartesaniaGuardado = repo.save(servicioArtesania);
        return servicioArtesaniaMapper.toDTO(servicioartesaniaGuardado);
    }

    @Override

    public ServicioArtesaniaDTO updateD(ServicioArtesaniaDTO.ServicioArtesaniaCADTO dto, Long id) {
        ServicioArtesania servicioArtesania = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Servicio de Artesania no encontrado"));

        Servicio servicio = servicioRepository.findById(dto.servicio())
                .orElseThrow(() -> new EntityNotFoundException("Servicio no encontrado"));

        ServicioArtesania servicioArtesaniaActualizado = servicioArtesaniaMapper.toEntityFromCADTO(dto);
        servicioArtesaniaActualizado.setIdArtesania(servicioArtesania.getIdArtesania());
        servicioArtesaniaActualizado.setServicio(servicio);

        ServicioArtesania servicioArtesaniaGuardado = repo.save(servicioArtesaniaActualizado);
        return servicioArtesaniaMapper.toDTO(servicioArtesaniaGuardado);
    }

    public Page<ServicioArtesania> listaPage(Pageable pageable){
        return repo.findAll(pageable);
    }
}
