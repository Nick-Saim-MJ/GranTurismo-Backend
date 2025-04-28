package org.example.granturismo.control;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.granturismo.dtos.DestinoDTO;
import org.example.granturismo.dtos.TipoServicioDTO;
import org.example.granturismo.mappers.DestinoMapper;
import org.example.granturismo.mappers.TipoServicioMapper;
import org.example.granturismo.modelo.Destino;
import org.example.granturismo.modelo.TipoServicio;
import org.example.granturismo.servicio.IDestinoService;
import org.example.granturismo.servicio.ITipoServicioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tipos")
public class TipoServicioController {

    private final ITipoServicioService iTipoServicioService;
    private final TipoServicioMapper tipoServicioMapper;

    @GetMapping
    public ResponseEntity<List<TipoServicioDTO>> findAll() {
        List<TipoServicioDTO> list = tipoServicioMapper.toDTOs(iTipoServicioService.findAll());
        return ResponseEntity.ok(list);
    }
    @GetMapping("/{id}")
    public ResponseEntity<TipoServicioDTO> findById(@PathVariable("id") Long id) {
        TipoServicio obj = iTipoServicioService.findById(id);
        return ResponseEntity.ok(tipoServicioMapper.toDTO(obj));
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody TipoServicioDTO.TipoServicioCADTO dto) {
        TipoServicioDTO obj = iTipoServicioService.saveD(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdTipo()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoServicioDTO> update(@Valid @RequestBody TipoServicioDTO.TipoServicioCADTO dto, @PathVariable("id") Long id) {
        TipoServicioDTO obj = iTipoServicioService.updateD(dto, id);
        return ResponseEntity.ok(obj);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        iTipoServicioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pageable")
    public ResponseEntity<org.springframework.data.domain.Page<TipoServicioDTO>> listPage(Pageable pageable){
        Page<TipoServicioDTO> page = iTipoServicioService.listaPage(pageable).map(e -> tipoServicioMapper.toDTO(e));
        return ResponseEntity.ok(page);
    }
}
