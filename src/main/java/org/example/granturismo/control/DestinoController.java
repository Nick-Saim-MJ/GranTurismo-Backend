package org.example.granturismo.control;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.granturismo.dtos.DestinoDTO;
import org.example.granturismo.dtos.PaqueteDTO;
import org.example.granturismo.mappers.DestinoMapper;
import org.example.granturismo.mappers.PaqueteMapper;
import org.example.granturismo.modelo.Destino;
import org.example.granturismo.modelo.Paquete;
import org.example.granturismo.servicio.IDestinoService;
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
@RequestMapping("/destinos")
public class DestinoController {

    private final IDestinoService destinoService;
    private final DestinoMapper destinoMapper;

    @GetMapping
    public ResponseEntity<List<DestinoDTO>> findAll() {
        List<DestinoDTO> list = destinoMapper.toDTOs(destinoService.findAll());
        return ResponseEntity.ok(list);
    }
    @GetMapping("/{id}")
    public ResponseEntity<DestinoDTO> findById(@PathVariable("id") Long id) {
        Destino obj = destinoService.findById(id);
        return ResponseEntity.ok(destinoMapper.toDTO(obj));
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody DestinoDTO.DestinoCADTO dto) {
        DestinoDTO obj = destinoService.saveD(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdDestino()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<DestinoDTO> update(@Valid @RequestBody DestinoDTO.DestinoCADTO dto, @PathVariable("id") Long id) {
        DestinoDTO obj = destinoService.updateD(dto, id);
        return ResponseEntity.ok(obj);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        destinoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pageable")
    public ResponseEntity<org.springframework.data.domain.Page<DestinoDTO>> listPage(Pageable pageable){
        Page<DestinoDTO> page = destinoService.listaPage(pageable).map(e -> destinoMapper.toDTO(e));
        return ResponseEntity.ok(page);
    }
}
