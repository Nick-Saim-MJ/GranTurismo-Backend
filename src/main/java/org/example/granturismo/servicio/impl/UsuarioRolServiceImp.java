package org.example.granturismo.servicio.impl;

import lombok.RequiredArgsConstructor;
import org.example.granturismo.modelo.UsuarioRol;
import org.example.granturismo.repositorio.IUsuarioRolRepository;
import org.example.granturismo.servicio.IUsuarioRolService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioRolServiceImp implements IUsuarioRolService {
    private final IUsuarioRolRepository repo;
    @Override
    public List<UsuarioRol> findOneByUsuarioUser(String user) {
        return repo.findOneByUsuarioUser(user);
    }
    @Override
    public UsuarioRol save(UsuarioRol ur) {
        return repo.save(ur);
    }
}
