package org.example.granturismo.control;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.example.granturismo.dtos.ProveedorDTO;
import org.example.granturismo.mappers.ProveedorMapper;
import org.example.granturismo.modelo.Proveedor;
import org.example.granturismo.servicio.IProveedorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/proveedores")
public class ProveedorController {

    private final IProveedorService proveedorService;
    private final ProveedorMapper proveedorMapper;

    @GetMapping
    public ResponseEntity<List<ProveedorDTO>> findAll() {
        List<ProveedorDTO> list = proveedorMapper.toDTOs(proveedorService.findAll());
        return ResponseEntity.ok(list);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProveedorDTO> findById(@PathVariable("id") Long id) {
        Proveedor obj = proveedorService.findById(id);
        return ResponseEntity.ok(proveedorMapper.toDTO(obj));
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody ProveedorDTO.ProveedorCADTO dto) {
        ProveedorDTO obj = proveedorService.saveD(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdProveedor()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProveedorDTO> update(@Valid @RequestBody ProveedorDTO.ProveedorCADTO dto, @PathVariable("id") Long id) {
        ProveedorDTO obj = proveedorService.updateD(dto, id);
        return ResponseEntity.ok(obj);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        proveedorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pageable")
    public ResponseEntity<org.springframework.data.domain.Page<ProveedorDTO>> listPage(Pageable pageable){
        Page<ProveedorDTO> page = proveedorService.listaPage(pageable).map(e -> proveedorMapper.toDTO(e));
        return ResponseEntity.ok(page);
    }
}
