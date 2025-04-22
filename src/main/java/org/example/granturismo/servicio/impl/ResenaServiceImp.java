package org.example.granturismo.servicio.impl;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.granturismo.dtos.ResenaDTO;
import org.example.granturismo.mappers.ResenaMapper;
import org.example.granturismo.modelo.Paquete;
import org.example.granturismo.modelo.Resena;
import org.example.granturismo.modelo.Usuario;
import org.example.granturismo.repositorio.*;
import org.example.granturismo.servicio.IResenaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Service
@Transactional
@RequiredArgsConstructor

public class ResenaServiceImp extends CrudGenericoServiceImp <Resena, Long> implements IResenaService {

    @Autowired
    private DataSource dataSource;

    private final IResenaRepository repo;
    private final ResenaMapper resenaMapper;
    private final IUsuarioRepository usuarioRepository;
    private final IPaqueteRepository paqueteRepository;

    @Override
    protected ICrudGenericoRepository<Resena, Long> getRepo() {
        return repo;
    }

    @Override
    public ResenaDTO saveD(ResenaDTO.ResenaCADTO dto) {
        Resena resena = resenaMapper.toEntityFromCADTO(dto);

        Usuario usuario = usuarioRepository.findById(dto.usuario())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        Paquete paquete = paqueteRepository.findById(dto.paquete())
                .orElseThrow(() -> new EntityNotFoundException("Paquete no encontrado"));

        resena.setUsuario(usuario);
        resena.setPaquete(paquete);

        Resena resenaGuardado = repo.save(resena);
        return resenaMapper.toDTO(resenaGuardado);
    }

    @Override
    public ResenaDTO updateD(ResenaDTO.ResenaCADTO dto, Long id) {
        Resena resena = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reseña no encontrada"));

        Resena resenax = resenaMapper.toEntityFromCADTO(dto);
        resenax.setIdResena(resena.getIdResena());

        Usuario usuario = usuarioRepository.findById(dto.usuario())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        Paquete paquete = paqueteRepository.findById(dto.paquete())
                .orElseThrow(() -> new EntityNotFoundException("Paquete no encontrado"));

        resenax.setUsuario(usuario);
        resenax.setPaquete(paquete);

        Resena resenaActualizado = repo.save(resenax);
        return resenaMapper.toDTO(resenaActualizado);
    }

    public Page<Resena> listaPage(Pageable pageable){
        return repo.findAll(pageable);
    }
}
