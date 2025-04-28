package org.example.granturismo.servicio.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.granturismo.dtos.ServicioDTO;
import org.example.granturismo.dtos.ServicioHoteleriaDTO;
import org.example.granturismo.mappers.ServicioHoteleriaMapper;
import org.example.granturismo.mappers.ServicioMapper;
import org.example.granturismo.modelo.Destino;
import org.example.granturismo.modelo.Proveedor;
import org.example.granturismo.modelo.Servicio;
import org.example.granturismo.modelo.ServicioHoteleria;
import org.example.granturismo.repositorio.ICrudGenericoRepository;
import org.example.granturismo.repositorio.IProveedorRepository;
import org.example.granturismo.repositorio.IServicioHoteleriaRepository;
import org.example.granturismo.repositorio.IServicioRepository;
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
public class ServicioHoteleriaServiceImp extends CrudGenericoServiceImp<ServicioHoteleria, Long> implements IServicioHoteleriaService {

    @Autowired
    private DataSource dataSource;

    private final IServicioHoteleriaRepository repo;
    private final ServicioHoteleriaMapper servicioHoteleriaMapper;
    private final IServicioRepository servicioRepository;

    @Override
    protected ICrudGenericoRepository<ServicioHoteleria, Long> getRepo() {
        return repo;
    }

    @Override
    public ServicioHoteleriaDTO saveD(ServicioHoteleriaDTO.ServicioHoteleriaCADTO dto) {
        ServicioHoteleria servicioHoteleria = servicioHoteleriaMapper.toEntityFromCADTO(dto);

        Servicio servicio = servicioRepository.findById(dto.servicio())
                .orElseThrow(() -> new EntityNotFoundException("Servicio no encontrado"));

        servicioHoteleria.setServicio(servicio);

        ServicioHoteleria servicioHoteleriaGuardado = repo.save(servicioHoteleria);
        return servicioHoteleriaMapper.toDTO(servicioHoteleriaGuardado);
    }

    @Override

    public ServicioHoteleriaDTO updateD(ServicioHoteleriaDTO.ServicioHoteleriaCADTO dto, Long id) {
        ServicioHoteleria servicioHoteleriaExistente = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Servicio de Hoteleria no encontrado"));

        Servicio servicio = servicioRepository.findById(dto.servicio())
                .orElseThrow(() -> new EntityNotFoundException("Servicio no encontrado"));

        ServicioHoteleria servicioHoteleriaActualizado = servicioHoteleriaMapper.toEntityFromCADTO(dto);
        servicioHoteleriaActualizado.setIdHoteleria(servicioHoteleriaExistente.getIdHoteleria());
        servicioHoteleriaActualizado.setServicio(servicio); // Aquí es donde debe ir

        ServicioHoteleria servicioHoteleriaGuardado = repo.save(servicioHoteleriaActualizado);
        return servicioHoteleriaMapper.toDTO(servicioHoteleriaGuardado);
    }

    public Page<ServicioHoteleria> listaPage(Pageable pageable){
        return repo.findAll(pageable);
    }
}
