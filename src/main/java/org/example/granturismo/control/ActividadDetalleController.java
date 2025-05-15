package org.example.granturismo.control;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.granturismo.dtos.ActividadDetalleDTO;
import org.example.granturismo.mappers.ActividadDetalleMapper;
import org.example.granturismo.modelo.ActividadDetalle;
import org.example.granturismo.security.PermitRoles;
import org.example.granturismo.servicio.IActividadDetalleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/actividaddetalle")
public class ActividadDetalleController {
    private final IActividadDetalleService actividadDetalleService;
    private final ActividadDetalleMapper actividadDetalleMapper;

    @GetMapping
    @PermitRoles({"ADMIN", "USER", "PROV"})
    public ResponseEntity<List<ActividadDetalleDTO>> findAll() {
        List<ActividadDetalleDTO> list = actividadDetalleMapper.toDTOs(actividadDetalleService.findAll());
        return ResponseEntity.ok(list);
    }
    @GetMapping("/{id}")
    @PermitRoles({"ADMIN", "USER", "PROV"})
    public ResponseEntity<ActividadDetalleDTO> findById(@PathVariable("id") Long id) {
        ActividadDetalle obj = actividadDetalleService.findById(id);
        return ResponseEntity.ok(actividadDetalleMapper.toDTO(obj));
    }

    @PostMapping
    @PermitRoles({"ADMIN"})
    public ResponseEntity<Void> save(@Valid @RequestBody ActividadDetalleDTO.ActividadDetalleCADTO dto) {
        ActividadDetalleDTO obj = actividadDetalleService.saveD(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdActividadDetalle()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    @PermitRoles({"ADMIN,PROV"})
    public ResponseEntity<ActividadDetalleDTO> update(@Valid @RequestBody ActividadDetalleDTO.ActividadDetalleCADTO dto, @PathVariable("id") Long id) {
        ActividadDetalleDTO obj = actividadDetalleService.updateD(dto, id);
        return ResponseEntity.ok(obj);
    }

    @DeleteMapping("/{id}")
    @PermitRoles({"ADMIN"})
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        actividadDetalleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pageable")
    @PermitRoles({"ADMIN", "USER", "PROV"})
    public ResponseEntity<org.springframework.data.domain.Page<ActividadDetalleDTO>> listPage(Pageable pageable){
        Page<ActividadDetalleDTO> page = actividadDetalleService.listaPage(pageable).map(e -> actividadDetalleMapper.toDTO(e));
        return ResponseEntity.ok(page);
    }
}
