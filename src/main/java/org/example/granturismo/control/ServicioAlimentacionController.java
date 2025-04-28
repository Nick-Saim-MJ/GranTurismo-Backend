package org.example.granturismo.control;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.granturismo.dtos.ServicioAlimentacionDTO;
import org.example.granturismo.dtos.ServicioArtesaniaDTO;
import org.example.granturismo.mappers.ServicioAlimentacionMapper;
import org.example.granturismo.mappers.ServicioArtesaniaMapper;
import org.example.granturismo.modelo.ServicioAlimentacion;
import org.example.granturismo.modelo.ServicioArtesania;
import org.example.granturismo.servicio.IServicioAlimentacionService;
import org.example.granturismo.servicio.IServicioArtesaniaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/servicioalimento")
public class ServicioAlimentacionController {

    private final IServicioAlimentacionService servicioAlimentacionService;
    private final ServicioAlimentacionMapper servicioAlimentacionMapper;

    @GetMapping
    public ResponseEntity<List<ServicioAlimentacionDTO>> findAll() {
        List<ServicioAlimentacionDTO> list = servicioAlimentacionMapper.toDTOs(servicioAlimentacionService.findAll());
        return ResponseEntity.ok(list);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ServicioAlimentacionDTO> findById(@PathVariable("id") Long id) {
        ServicioAlimentacion obj = servicioAlimentacionService.findById(id);
        return ResponseEntity.ok(servicioAlimentacionMapper.toDTO(obj));
    }


    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody ServicioAlimentacionDTO.ServicioAlimentacionCADTO dto) {
        ServicioAlimentacionDTO obj = servicioAlimentacionService.saveD(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdAlimentacion()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicioAlimentacionDTO> update(@Valid @RequestBody ServicioAlimentacionDTO.ServicioAlimentacionCADTO dto, @PathVariable("id") Long id) {
        ServicioAlimentacionDTO obj = servicioAlimentacionService.updateD(dto, id);
        return ResponseEntity.ok(obj);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        servicioAlimentacionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pageable")
    public ResponseEntity<org.springframework.data.domain.Page<ServicioAlimentacionDTO>> listPage(Pageable pageable){
        Page<ServicioAlimentacionDTO> page = servicioAlimentacionService.listaPage(pageable).map(e -> servicioAlimentacionMapper.toDTO(e));
        return ResponseEntity.ok(page);
    }
}
