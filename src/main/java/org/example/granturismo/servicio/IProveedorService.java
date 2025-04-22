package org.example.granturismo.servicio;

import org.example.granturismo.dtos.ProveedorDTO;
import org.example.granturismo.modelo.Proveedor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProveedorService extends ICrudGenericoService<Proveedor, Long> {

    ProveedorDTO saveD(ProveedorDTO.ProveedorCADTO dto);

    ProveedorDTO updateD(ProveedorDTO.ProveedorCADTO dto, Long id);

    Page<Proveedor> listaPage(Pageable pageable);
}
