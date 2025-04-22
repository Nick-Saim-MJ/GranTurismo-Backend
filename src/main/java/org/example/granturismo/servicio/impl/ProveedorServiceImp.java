package org.example.granturismo.servicio.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.granturismo.dtos.ProveedorDTO;
import org.example.granturismo.mappers.ProveedorMapper;
import org.example.granturismo.modelo.Proveedor;
import org.example.granturismo.modelo.Usuario;
import org.example.granturismo.repositorio.ICrudGenericoRepository;
import org.example.granturismo.repositorio.IProveedorRepository;
import org.example.granturismo.repositorio.IUsuarioRepository;
import org.example.granturismo.servicio.IProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Service
@Transactional
@RequiredArgsConstructor


public class ProveedorServiceImp extends CrudGenericoServiceImp <Proveedor, Long> implements IProveedorService {

    @Autowired
    private DataSource dataSource;

    private final IProveedorRepository repo;
    private final ProveedorMapper proveedorMapper;
    private final IUsuarioRepository usuarioRepository;

    @Override
    protected ICrudGenericoRepository<Proveedor, Long> getRepo() {
        return repo;
    }

    @Override
    public ProveedorDTO saveD(ProveedorDTO.ProveedorCADTO dto) {
        Proveedor proveedor = proveedorMapper.toEntityFromCADTO(dto);

        Usuario usuario = usuarioRepository.findById(dto.usuario())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        proveedor.setUsuario(usuario);
        Proveedor proveedorGuardado = repo.save(proveedor);
        return proveedorMapper.toDTO(proveedorGuardado);
    }

    @Override
    public ProveedorDTO updateD(ProveedorDTO.ProveedorCADTO dto, Long id) {
        Proveedor proveedor = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado"));

        Proveedor proveedorx = proveedorMapper.toEntityFromCADTO(dto);
        proveedorx.setIdProveedor(proveedor.getIdProveedor());

        Usuario usuario = usuarioRepository.findById(dto.usuario())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        proveedorx.setUsuario(usuario);

        Proveedor proveedorActualizado = repo.save(proveedorx);
        return proveedorMapper.toDTO(proveedorActualizado);
    }



    public Page<Proveedor> listaPage(Pageable pageable){
        return repo.findAll(pageable);
    }
}
