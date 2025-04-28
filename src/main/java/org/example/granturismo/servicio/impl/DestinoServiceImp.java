package org.example.granturismo.servicio.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.granturismo.dtos.DestinoDTO;
import org.example.granturismo.dtos.PaqueteDTO;
import org.example.granturismo.mappers.DestinoMapper;
import org.example.granturismo.mappers.PaqueteMapper;
import org.example.granturismo.modelo.Destino;
import org.example.granturismo.modelo.Paquete;
import org.example.granturismo.modelo.Proveedor;
import org.example.granturismo.repositorio.ICrudGenericoRepository;
import org.example.granturismo.repositorio.IDestinoRepository;
import org.example.granturismo.repositorio.IPaqueteRepository;
import org.example.granturismo.repositorio.IProveedorRepository;
import org.example.granturismo.servicio.IDestinoService;
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

public class DestinoServiceImp extends CrudGenericoServiceImp<Destino, Long> implements IDestinoService {

    @Autowired
    private DataSource dataSource;

    private final IDestinoRepository repo;
    private final DestinoMapper destinoMapper;

    @Override
    protected ICrudGenericoRepository<Destino, Long> getRepo() {
        return repo;
    }

    @Override
    public DestinoDTO saveD(DestinoDTO.DestinoCADTO dto) {
        Destino destino = destinoMapper.toEntityFromCADTO(dto);

        Destino destinoGuardado = repo.save(destino);
        return destinoMapper.toDTO(destinoGuardado);
    }

    @Override
    public DestinoDTO updateD(DestinoDTO.DestinoCADTO dto, Long id) {
        Destino destino = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Destino no encontrado"));

        Destino destinox = destinoMapper.toEntityFromCADTO(dto);
        destinox.setIdDestino(destino.getIdDestino());

        Destino destinoActualizado = repo.save(destinox);
        return destinoMapper.toDTO(destinoActualizado);
    }
    public Page<Destino> listaPage(Pageable pageable){
        return repo.findAll(pageable);
    }
}
