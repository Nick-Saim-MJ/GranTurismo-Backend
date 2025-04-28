package org.example.granturismo.servicio.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.granturismo.dtos.ServicioAlimentacionDTO;
import org.example.granturismo.dtos.ServicioArtesaniaDTO;
import org.example.granturismo.mappers.ServicioAlimentacionMapper;
import org.example.granturismo.mappers.ServicioArtesaniaMapper;
import org.example.granturismo.modelo.Servicio;
import org.example.granturismo.modelo.ServicioAlimentacion;
import org.example.granturismo.modelo.ServicioArtesania;
import org.example.granturismo.repositorio.ICrudGenericoRepository;
import org.example.granturismo.repositorio.IServicioAlimentacionRepository;
import org.example.granturismo.repositorio.IServicioArtesaniaRepository;
import org.example.granturismo.repositorio.IServicioRepository;
import org.example.granturismo.servicio.IServicioAlimentacionService;
import org.example.granturismo.servicio.IServicioArtesaniaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Service
@Transactional
@RequiredArgsConstructor
public class ServicioAlimentacionServiceImp extends CrudGenericoServiceImp<ServicioAlimentacion, Long> implements IServicioAlimentacionService {


    @Autowired
    private DataSource dataSource;

    private final IServicioAlimentacionRepository repo;
    private final ServicioAlimentacionMapper servicioAlimentacionMapper;
    private final IServicioRepository servicioRepository;

    @Override
    protected ICrudGenericoRepository<ServicioAlimentacion, Long> getRepo() {
        return repo;
    }

    @Override
    public ServicioAlimentacionDTO saveD(ServicioAlimentacionDTO.ServicioAlimentacionCADTO dto) {
        ServicioAlimentacion servicioAlimentacion = servicioAlimentacionMapper.toEntityFromCADTO(dto);

        Servicio servicio = servicioRepository.findById(dto.servicio())
                .orElseThrow(() -> new EntityNotFoundException("Servicio no encontrado"));

        servicioAlimentacion.setServicio(servicio);

        ServicioAlimentacion servicioalimentacionGuardado = repo.save(servicioAlimentacion);
        return servicioAlimentacionMapper.toDTO(servicioalimentacionGuardado);
    }

    @Override
    public ServicioAlimentacionDTO updateD(ServicioAlimentacionDTO.ServicioAlimentacionCADTO dto, Long id) {
        ServicioAlimentacion servicioAlimentacion = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Servicio de Alimentacion no encontrado"));

        Servicio servicio = servicioRepository.findById(dto.servicio())
                .orElseThrow(() -> new EntityNotFoundException("Servicio no encontrado"));

        ServicioAlimentacion servicioAlimentacionActualizado = servicioAlimentacionMapper.toEntityFromCADTO(dto);
        servicioAlimentacionActualizado.setIdAlimentacion(servicioAlimentacion.getIdAlimentacion());
        servicioAlimentacionActualizado.setServicio(servicio);

        ServicioAlimentacion servicioAlimentacionGuardado = repo.save(servicioAlimentacionActualizado);
        return servicioAlimentacionMapper.toDTO(servicioAlimentacionGuardado);
    }

    public Page<ServicioAlimentacion> listaPage(Pageable pageable){
        return repo.findAll(pageable);
    }
}
