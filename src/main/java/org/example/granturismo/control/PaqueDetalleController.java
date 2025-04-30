package org.example.granturismo.control;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.granturismo.dtos.PaqueteDTO;
import org.example.granturismo.dtos.PaqueteDetalleDTO;
import org.example.granturismo.mappers.PaqueteDetalleMapper;
import org.example.granturismo.modelo.Paquete;
import org.example.granturismo.modelo.PaqueteDetalle;
import org.example.granturismo.servicio.IPaqueteDetalleService;
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
@RequestMapping("/paquetedetalle")
public class PaqueDetalleController {

    private final IPaqueteDetalleService paqueteDetalleService;
    private final PaqueteDetalleMapper paqueteDetalleMapper;

    @GetMapping
    public ResponseEntity<List<PaqueteDetalleDTO>> findAll() {
        List<PaqueteDetalleDTO> list = paqueteDetalleMapper.toDTOs(paqueteDetalleService.findAll());
        return ResponseEntity.ok(list);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PaqueteDetalleDTO> findById(@PathVariable("id") Long id) {
        PaqueteDetalle obj = paqueteDetalleService.findById(id);
        return ResponseEntity.ok(paqueteDetalleMapper.toDTO(obj));
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody PaqueteDetalleDTO.PaqueteDetalleCADTO dto) {
        PaqueteDetalleDTO obj = paqueteDetalleService.saveD(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdPaqueteDetalle()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaqueteDetalleDTO> update(@Valid @RequestBody PaqueteDetalleDTO.PaqueteDetalleCADTO dto, @PathVariable("id") Long id) {
        PaqueteDetalleDTO obj = paqueteDetalleService.updateD(dto, id);
        return ResponseEntity.ok(obj);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        paqueteDetalleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pageable")
    public ResponseEntity<org.springframework.data.domain.Page<PaqueteDetalleDTO>> listPage(Pageable pageable){
        Page<PaqueteDetalleDTO> page = paqueteDetalleService.listaPage(pageable).map(e -> paqueteDetalleMapper.toDTO(e));
        return ResponseEntity.ok(page);
    }
}
