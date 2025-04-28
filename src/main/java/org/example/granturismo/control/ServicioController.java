package org.example.granturismo.control;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.granturismo.dtos.ServicioDTO;
import org.example.granturismo.mappers.ServicioMapper;
import org.example.granturismo.modelo.Servicio;
import org.example.granturismo.servicio.IServicioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/servicios")
public class ServicioController {

    private final IServicioService servicioService;
    private final ServicioMapper servicioMapper;

    @GetMapping
    public ResponseEntity<List<ServicioDTO>> findAll() {
        List<ServicioDTO> list = servicioMapper.toDTOs(servicioService.findAll());
        return ResponseEntity.ok(list);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ServicioDTO> findById(@PathVariable("id") Long id) {
        Servicio obj = servicioService.findById(id);
        return ResponseEntity.ok(servicioMapper.toDTO(obj));
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody ServicioDTO.ServicioCADTO dto) {
        ServicioDTO obj = servicioService.saveD(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdServicio()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicioDTO> update(@Valid @RequestBody ServicioDTO.ServicioCADTO dto, @PathVariable("id") Long id) {
        ServicioDTO obj = servicioService.updateD(dto, id);
        return ResponseEntity.ok(obj);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        servicioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pageable")
    public ResponseEntity<org.springframework.data.domain.Page<ServicioDTO>> listPage(Pageable pageable){
        Page<ServicioDTO> page = servicioService.listaPage(pageable).map(e -> servicioMapper.toDTO(e));
        return ResponseEntity.ok(page);
    }
}
