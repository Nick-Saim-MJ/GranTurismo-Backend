package org.example.granturismo.control;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.granturismo.dtos.ServicioArtesaniaDTO;
import org.example.granturismo.dtos.ServicioHoteleriaDTO;
import org.example.granturismo.mappers.ServicioArtesaniaMapper;
import org.example.granturismo.modelo.ServicioArtesania;
import org.example.granturismo.modelo.ServicioHoteleria;
import org.example.granturismo.servicio.IServicioArtesaniaService;
import org.example.granturismo.servicio.impl.ServicioArtesaniaServiceImp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/servicioartesania")

public class ServicioArtesaniaController {

    private final IServicioArtesaniaService servicioArtesaniaService;
    private final ServicioArtesaniaMapper servicioArtesaniaMapper;

    @GetMapping
    public ResponseEntity<List<ServicioArtesaniaDTO>> findAll() {
        List<ServicioArtesaniaDTO> list = servicioArtesaniaMapper.toDTOs(servicioArtesaniaService.findAll());
        return ResponseEntity.ok(list);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ServicioArtesaniaDTO> findById(@PathVariable("id") Long id) {
        ServicioArtesania obj = servicioArtesaniaService.findById(id);
        return ResponseEntity.ok(servicioArtesaniaMapper.toDTO(obj));
    }


    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody ServicioArtesaniaDTO.ServicioArtesaniaCADTO dto) {
        ServicioArtesaniaDTO obj = servicioArtesaniaService.saveD(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdArtesania()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicioArtesaniaDTO> update(@Valid @RequestBody ServicioArtesaniaDTO.ServicioArtesaniaCADTO dto, @PathVariable("id") Long id) {
        ServicioArtesaniaDTO obj = servicioArtesaniaService.updateD(dto, id);
        return ResponseEntity.ok(obj);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        servicioArtesaniaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pageable")
    public ResponseEntity<org.springframework.data.domain.Page<ServicioArtesaniaDTO>> listPage(Pageable pageable){
        Page<ServicioArtesaniaDTO> page = servicioArtesaniaService.listaPage(pageable).map(e -> servicioArtesaniaMapper.toDTO(e));
        return ResponseEntity.ok(page);
    }
}
