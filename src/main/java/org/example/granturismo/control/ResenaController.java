package org.example.granturismo.control;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.granturismo.dtos.ResenaDTO;
import org.example.granturismo.mappers.ResenaMapper;
import org.example.granturismo.modelo.Resena;
import org.example.granturismo.servicio.IResenaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/resenas")
public class ResenaController {

    private final IResenaService resenaService;
    private final ResenaMapper resenaMapper;

    @GetMapping
    public ResponseEntity<List<ResenaDTO>> findAll() {
        List<ResenaDTO> list = resenaMapper.toDTOs(resenaService.findAll());
        return ResponseEntity.ok(list);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResenaDTO> findById(@PathVariable("id") Long id) {
        Resena obj = resenaService.findById(id);
        return ResponseEntity.ok(resenaMapper.toDTO(obj));
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody ResenaDTO.ResenaCADTO dto) {
        ResenaDTO obj = resenaService.saveD(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdResena()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResenaDTO> update(@Valid @RequestBody ResenaDTO.ResenaCADTO dto, @PathVariable("id") Long id) {
        ResenaDTO obj = resenaService.updateD(dto, id);
        return ResponseEntity.ok(obj);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        resenaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pageable")
    public ResponseEntity<org.springframework.data.domain.Page<ResenaDTO>> listPage(Pageable pageable){
        Page<ResenaDTO> page = resenaService.listaPage(pageable).map(e -> resenaMapper.toDTO(e));
        return ResponseEntity.ok(page);
    }
}
