package org.example.granturismo.servicio.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.granturismo.dtos.TipoServicioDTO;
import org.example.granturismo.mappers.TipoServicioMapper;
import org.example.granturismo.modelo.TipoServicio;
import org.example.granturismo.repositorio.*;
import org.example.granturismo.servicio.ITipoServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;


@Service
@Transactional
@RequiredArgsConstructor

public class TipoServicioServiceImp extends CrudGenericoServiceImp<TipoServicio, Long> implements ITipoServicioService {
    @Autowired
    private DataSource dataSource;

    private final ITipoServicioRepository repo;
    private final TipoServicioMapper tipoServicioMapper;

    @Override
    protected ICrudGenericoRepository<TipoServicio, Long> getRepo() {
        return repo;
    }

    @Override
    public TipoServicioDTO saveD(TipoServicioDTO.TipoServicioCADTO dto) {
        TipoServicio tipo = tipoServicioMapper.toEntityFromCADTO(dto);

        TipoServicio tipoServicioGuardado = repo.save(tipo);
        return tipoServicioMapper.toDTO(tipoServicioGuardado);
    }

    @Override
    public TipoServicioDTO updateD(TipoServicioDTO.TipoServicioCADTO dto, Long id) {
        TipoServicio tipo = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tipo de Servicio no encontrado"));

        TipoServicio tipoServiciox = tipoServicioMapper.toEntityFromCADTO(dto);
        tipoServiciox.setIdTipo(tipo.getIdTipo());

        TipoServicio tipoServicioActualizado = repo.save(tipoServiciox);
        return tipoServicioMapper.toDTO(tipoServicioActualizado);
    }



    public Page<TipoServicio> listaPage(Pageable pageable){
        return repo.findAll(pageable);
    }
}
