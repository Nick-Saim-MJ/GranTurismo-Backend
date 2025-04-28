package org.example.granturismo.servicio.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.granturismo.dtos.DestinoDTO;
import org.example.granturismo.dtos.ServicioDTO;
import org.example.granturismo.mappers.DestinoMapper;
import org.example.granturismo.mappers.ServicioMapper;
import org.example.granturismo.modelo.Destino;
import org.example.granturismo.modelo.Servicio;
import org.example.granturismo.modelo.ServicioHoteleria;
import org.example.granturismo.modelo.TipoServicio;
import org.example.granturismo.repositorio.ICrudGenericoRepository;
import org.example.granturismo.repositorio.IDestinoRepository;
import org.example.granturismo.repositorio.IServicioRepository;
import org.example.granturismo.repositorio.ITipoServicioRepository;
import org.example.granturismo.servicio.IServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Service
@Transactional
@RequiredArgsConstructor
public class ServicioServiceImp extends CrudGenericoServiceImp<Servicio, Long> implements IServicioService {

    @Autowired
    private DataSource dataSource;

    private final IServicioRepository repo;
    private final ServicioMapper servicioMapper;
    private final ITipoServicioRepository tipoServicioRepository;

    @Override
    protected ICrudGenericoRepository<Servicio, Long> getRepo() {
        return repo;
    }

    @Override
    public ServicioDTO saveD(ServicioDTO.ServicioCADTO dto) {
        Servicio servicio = servicioMapper.toEntityFromCADTO(dto);

        TipoServicio tipo = tipoServicioRepository.findById(dto.tipo())
                .orElseThrow(() -> new EntityNotFoundException("Tipo de Servicio no encontrado"));

        servicio.setTipo(tipo);

        Servicio servicioGuardado = repo.save(servicio);
        return servicioMapper.toDTO(servicioGuardado);
    }

    @Override
    public ServicioDTO updateD(ServicioDTO.ServicioCADTO dto, Long id) {
        Servicio servicio = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Servicio no encontrado"));

        Servicio servicioupdate = servicioMapper.toEntityFromCADTO(dto);
        servicioupdate.setIdServicio(servicio.getIdServicio());

        TipoServicio tipo = tipoServicioRepository.findById(dto.tipo())
                .orElseThrow(() -> new EntityNotFoundException("Tipo de Servicio no encontrado"));

        servicioupdate.setTipo(tipo); // CORREGIDO ✅

        Servicio servicioActualizado = repo.save(servicioupdate);
        return servicioMapper.toDTO(servicioActualizado);
    }

    public Page<Servicio> listaPage(Pageable pageable){
        return repo.findAll(pageable);
    }
}
