package org.example.granturismo.servicio.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.granturismo.dtos.ActividadDTO;
import org.example.granturismo.mappers.ActividadMapper;
import org.example.granturismo.modelo.Actividad;
import org.example.granturismo.repositorio.IActividadRepository;
import org.example.granturismo.repositorio.ICrudGenericoRepository;
import org.example.granturismo.servicio.IActividadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Service
@Transactional
@RequiredArgsConstructor
public class ActividadServiceImp extends CrudGenericoServiceImp<Actividad, Long> implements IActividadService {

    @Autowired
    private DataSource dataSource;

    private final IActividadRepository repo;
    private final ActividadMapper actividadMapper;

    @Override
    protected ICrudGenericoRepository<Actividad, Long> getRepo() {
        return repo;
    }

    @Override
    public ActividadDTO saveD(ActividadDTO.ActividadCADTO dto) {
        Actividad actividad = actividadMapper.toEntityFromCADTO(dto);

        Actividad actividadGuardado = repo.save(actividad);
        return actividadMapper.toDTO(actividadGuardado);
    }

    @Override
    public ActividadDTO updateD(ActividadDTO.ActividadCADTO dto, Long id) {
        Actividad actividad = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Actividad no encontrada"));

        Actividad actividax = actividadMapper.toEntityFromCADTO(dto);
        actividax.setIdActividad(actividad.getIdActividad());

        Actividad actividadActualizada = repo.save(actividax);
        return actividadMapper.toDTO(actividadActualizada);
    }
    public Page<Actividad> listaPage(Pageable pageable){
        return repo.findAll(pageable);
    }
}
