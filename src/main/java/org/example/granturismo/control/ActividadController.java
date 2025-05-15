package org.example.granturismo.control;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.granturismo.dtos.ActividadDTO;
import org.example.granturismo.dtos.DestinoDTO;
import org.example.granturismo.mappers.ActividadMapper;
import org.example.granturismo.mappers.DestinoMapper;
import org.example.granturismo.modelo.Actividad;
import org.example.granturismo.modelo.Destino;
import org.example.granturismo.security.PermitRoles;
import org.example.granturismo.servicio.IActividadService;
import org.example.granturismo.servicio.IDestinoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/actividad")
public class ActividadController {


    private final IActividadService actividadService;
    private final ActividadMapper actividadMapper;

    @GetMapping
    @PermitRoles({"ADMIN", "USER", "PROV"})
    public ResponseEntity<List<ActividadDTO>> findAll() {
        List<ActividadDTO> list = actividadMapper.toDTOs(actividadService.findAll());
        return ResponseEntity.ok(list);
    }
    @GetMapping("/{id}")
    @PermitRoles({"ADMIN", "USER", "PROV"})
    public ResponseEntity<ActividadDTO> findById(@PathVariable("id") Long id) {
        Actividad obj = actividadService.findById(id);
        return ResponseEntity.ok(actividadMapper.toDTO(obj));
    }

    @PostMapping
    @PermitRoles({"ADMIN"})
    public ResponseEntity<Void> save(@Valid @RequestBody ActividadDTO.ActividadCADTO dto) {
        ActividadDTO obj = actividadService.saveD(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdActividad()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    @PermitRoles({"ADMIN,PROV"})
    public ResponseEntity<ActividadDTO> update(@Valid @RequestBody ActividadDTO.ActividadCADTO dto, @PathVariable("id") Long id) {
        ActividadDTO obj = actividadService.updateD(dto, id);
        return ResponseEntity.ok(obj);
    }

    @DeleteMapping("/{id}")
    @PermitRoles({"ADMIN"})
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        actividadService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pageable")
    @PermitRoles({"ADMIN", "USER", "PROV"})
    public ResponseEntity<org.springframework.data.domain.Page<ActividadDTO>> listPage(Pageable pageable){
        Page<ActividadDTO> page = actividadService.listaPage(pageable).map(e -> actividadMapper.toDTO(e));
        return ResponseEntity.ok(page);
    }
}
