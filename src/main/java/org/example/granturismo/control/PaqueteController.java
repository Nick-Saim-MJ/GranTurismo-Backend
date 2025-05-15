package org.example.granturismo.control;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.granturismo.dtos.PaqueteDTO;
import org.example.granturismo.mappers.PaqueteMapper;
import org.example.granturismo.modelo.Paquete;
import org.example.granturismo.security.PermitRoles;
import org.example.granturismo.servicio.IPaqueteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/paquetes")
public class PaqueteController {


    private final IPaqueteService paqueteService;
    private final PaqueteMapper paqueteMapper;

    @GetMapping
    @PermitRoles({"ADMIN", "USER", "PROV"})
    public ResponseEntity<List<PaqueteDTO>> findAll() {
        List<PaqueteDTO> list = paqueteMapper.toDTOs(paqueteService.findAll());
        return ResponseEntity.ok(list);
    }
    @GetMapping("/{id}")
    @PermitRoles({"ADMIN", "USER", "PROV"})
    public ResponseEntity<PaqueteDTO> findById(@PathVariable("id") Long id) {
        Paquete obj = paqueteService.findById(id);
        return ResponseEntity.ok(paqueteMapper.toDTO(obj));
    }

    @PostMapping
    @PermitRoles({"ADMIN"})
    public ResponseEntity<Void> save(@Valid @RequestBody PaqueteDTO.PaqueteCADTO dto) {
        PaqueteDTO obj = paqueteService.saveD(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdPaquete()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    @PermitRoles({"ADMIN,PROV"})
    public ResponseEntity<PaqueteDTO> update(@Valid @RequestBody PaqueteDTO.PaqueteCADTO dto, @PathVariable("id") Long id) {
        PaqueteDTO obj = paqueteService.updateD(dto, id);
        return ResponseEntity.ok(obj);
    }

    @DeleteMapping("/{id}")
    @PermitRoles({"ADMIN"})
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        paqueteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pageable")
    @PermitRoles({"ADMIN", "USER", "PROV"})
    public ResponseEntity<org.springframework.data.domain.Page<PaqueteDTO>> listPage(Pageable pageable){
        Page<PaqueteDTO> page = paqueteService.listaPage(pageable).map(e -> paqueteMapper.toDTO(e));
        return ResponseEntity.ok(page);
    }
}
