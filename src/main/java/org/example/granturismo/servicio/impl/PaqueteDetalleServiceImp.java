package org.example.granturismo.servicio.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.granturismo.dtos.PaqueteDetalleDTO;
import org.example.granturismo.mappers.PaqueteDetalleMapper;
import org.example.granturismo.modelo.*;
import org.example.granturismo.repositorio.*;
import org.example.granturismo.servicio.IPaqueteDetalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PaqueteDetalleServiceImp extends CrudGenericoServiceImp<PaqueteDetalle, Long> implements IPaqueteDetalleService {

    @Autowired
    private DataSource dataSource;

    private final IPaqueteDetalleRepository repo;
    private final PaqueteDetalleMapper paqueteDetalleMapper;
    private final IPaqueteRepository paqueteRepository;
    private final IServicioRepository servicioRepository;

    @Override
    protected ICrudGenericoRepository<PaqueteDetalle, Long> getRepo() {
        return repo;
    }


    @Override
    public PaqueteDetalleDTO saveD(PaqueteDetalleDTO.PaqueteDetalleCADTO dto) {
        PaqueteDetalle paqueteDetalle = paqueteDetalleMapper.toEntityFromCADTO(dto);

        Paquete paquete = paqueteRepository.findById(dto.paquete())
                .orElseThrow(() -> new EntityNotFoundException("Paquete no encontrado"));
        Servicio servicio = servicioRepository.findById(dto.servicio())
                .orElseThrow(() -> new EntityNotFoundException("Servicio no encontrado"));

        paqueteDetalle.setPaquete(paquete);
        paqueteDetalle.setServicio(servicio);

        PaqueteDetalle paqueteDetalleGuardado = repo.save(paqueteDetalle);
        return paqueteDetalleMapper.toDTO(paqueteDetalleGuardado);
    }

    @Override
    public PaqueteDetalleDTO updateD(PaqueteDetalleDTO.PaqueteDetalleCADTO dto, Long id) {
        PaqueteDetalle paqueteDetalle = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Detalle del Paquete no encontrado"));

        PaqueteDetalle paqueteDetallex = paqueteDetalleMapper.toEntityFromCADTO(dto);
        paqueteDetallex.setIdPaqueteDetalle(paqueteDetalle.getIdPaqueteDetalle());

        Paquete paquete = paqueteRepository.findById(dto.paquete())
                .orElseThrow(() -> new EntityNotFoundException("Paquete no encontrado"));
        Servicio servicio = servicioRepository.findById(dto.servicio())
                .orElseThrow(() -> new EntityNotFoundException("Servicio no encontrado"));

        paqueteDetallex.setPaquete(paquete);
        paqueteDetallex.setServicio(servicio);

        PaqueteDetalle paqueteDetalleActualizado = repo.save(paqueteDetallex);
        return paqueteDetalleMapper.toDTO(paqueteDetalleActualizado);
    }

    public Page<PaqueteDetalle> listaPage(Pageable pageable){
        return repo.findAll(pageable);
    }
}
