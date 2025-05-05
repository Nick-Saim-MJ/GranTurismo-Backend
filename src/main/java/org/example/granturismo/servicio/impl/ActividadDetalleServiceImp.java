package org.example.granturismo.servicio.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.granturismo.dtos.ActividadDetalleDTO;
import org.example.granturismo.dtos.PaqueteDetalleDTO;
import org.example.granturismo.mappers.ActividadDetalleMapper;
import org.example.granturismo.mappers.PaqueteDetalleMapper;
import org.example.granturismo.modelo.*;
import org.example.granturismo.repositorio.*;
import org.example.granturismo.servicio.IActividadDetalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Service
@Transactional
@RequiredArgsConstructor
public class ActividadDetalleServiceImp extends CrudGenericoServiceImp<ActividadDetalle, Long> implements IActividadDetalleService {


    @Autowired
    private DataSource dataSource;

    private final IActividadDetalleRepository repo;
    private final ActividadDetalleMapper actividadDetalleMapper;
    private final IPaqueteRepository paqueteRepository;
    private final IActividadRepository actividadRepository;

    @Override
    protected ICrudGenericoRepository<ActividadDetalle, Long> getRepo() {
        return repo;
    }


    @Override
    public ActividadDetalleDTO saveD(ActividadDetalleDTO.ActividadDetalleCADTO dto) {
        ActividadDetalle actividadDetalle = actividadDetalleMapper.toEntityFromCADTO(dto);

        Paquete paquete = paqueteRepository.findById(dto.paquete())
                .orElseThrow(() -> new EntityNotFoundException("Paquete no encontrado"));
        Actividad actividad = actividadRepository.findById(dto.actividad())
                .orElseThrow(() -> new EntityNotFoundException("Actividad no encontrada"));

        actividadDetalle.setPaquete(paquete);
        actividadDetalle.setActividad(actividad);

        ActividadDetalle actividadDetalleGuardado = repo.save(actividadDetalle);
        return actividadDetalleMapper.toDTO(actividadDetalleGuardado);
    }

    @Override
    public ActividadDetalleDTO updateD(ActividadDetalleDTO.ActividadDetalleCADTO dto, Long id) {
        ActividadDetalle actividadDetalle = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Detalle de la Actividad no encontrado"));

        ActividadDetalle actividadDetallex = actividadDetalleMapper.toEntityFromCADTO(dto);
        actividadDetallex.setIdActividadDetalle(actividadDetalle.getIdActividadDetalle());

        Paquete paquete = paqueteRepository.findById(dto.paquete())
                .orElseThrow(() -> new EntityNotFoundException("Paquete no encontrado"));
        Actividad actividad = actividadRepository.findById(dto.actividad())
                .orElseThrow(() -> new EntityNotFoundException("Actividad no encontrada"));

        actividadDetallex.setPaquete(paquete);
        actividadDetallex.setActividad(actividad);

        ActividadDetalle actividadDetalleActualizado = repo.save(actividadDetallex);
        return actividadDetalleMapper.toDTO(actividadDetalleActualizado);
    }

    public Page<ActividadDetalle> listaPage(Pageable pageable){
        return repo.findAll(pageable);
    }
}
