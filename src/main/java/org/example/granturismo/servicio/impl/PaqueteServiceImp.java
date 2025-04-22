package org.example.granturismo.servicio.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import org.example.granturismo.dtos.PaqueteDTO;
import org.example.granturismo.mappers.PaqueteMapper;
import org.example.granturismo.modelo.Paquete;
import org.example.granturismo.modelo.Proveedor;
import org.example.granturismo.repositorio.ICrudGenericoRepository;
import org.example.granturismo.repositorio.IPaqueteRepository;
import org.example.granturismo.repositorio.IProveedorRepository;
import org.example.granturismo.servicio.IPaqueteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.example.granturismo.repositorio.*;

import javax.sql.DataSource;

@Service
@Transactional
@RequiredArgsConstructor

public class PaqueteServiceImp extends CrudGenericoServiceImp<Paquete, Long> implements IPaqueteService {

    @Autowired
    private DataSource dataSource;

    private final IPaqueteRepository repo;
    private final PaqueteMapper paqueteMapper;
    private final IProveedorRepository proveedorRepository;

    @Override
    protected ICrudGenericoRepository<Paquete, Long> getRepo() {
        return repo;
    }


    @Override
    public PaqueteDTO saveD(PaqueteDTO.PaqueteCADTO dto) {
        Paquete paquete = paqueteMapper.toEntityFromCADTO(dto);

        Proveedor proveedor = proveedorRepository.findById(dto.proveedor())
                .orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado"));

        paquete.setProveedor(proveedor);

        Paquete paqueteGuardado = repo.save(paquete);
        return paqueteMapper.toDTO(paqueteGuardado);
    }

    @Override
    public PaqueteDTO updateD(PaqueteDTO.PaqueteCADTO dto, Long id) {
        Paquete paquete = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Paquete no encontrado"));

        Paquete paquetex = paqueteMapper.toEntityFromCADTO(dto);
        paquetex.setIdPaquete(paquete.getIdPaquete());

        Proveedor proveedor = proveedorRepository.findById(dto.proveedor())
                .orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado"));

        paquetex.setProveedor(proveedor);

        Paquete paqueteActualizado = repo.save(paquetex);
        return paqueteMapper.toDTO(paqueteActualizado);
    }



    public Page<Paquete> listaPage(Pageable pageable){
        return repo.findAll(pageable);
    }
}

