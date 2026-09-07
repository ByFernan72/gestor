package com.gestorBackend.Controller;

import com.gestorBackend.Dto.Request.MovimientoRequestDto;
import com.gestorBackend.Dto.TransferenciaDto;
import com.gestorBackend.Service.MovimientoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
// @CrossOrigin(origins = "*") // @CrossOrigin(origins = "http://localhost:puerto")
@RequestMapping("/api/v1/movimiento")
@RequiredArgsConstructor
@Validated
public class MovimientoController {

    private final MovimientoService movimientoService;

    @PostMapping("/transferencia")
    public ResponseEntity<String> transferirBalance(@Valid @RequestBody MovimientoRequestDto dto){
        movimientoService.transferirBalance(
                dto.getIdCarteraOrigen(),
                dto.getIdCarteraDestino(),
                dto.getIdActivoOrigen(),
                dto.getIdActivoDestino(),
                dto.getCantidadOrigen(),
                dto.getCantidadDestino(),
                dto.getNombreNuevoActivo(),
                dto.getTipoNuevoActivo()
        );
        return ResponseEntity.ok("Transferencia realizada con éxito");
    }

    @GetMapping("/historial/transferencias")
    public ResponseEntity<Page<TransferenciaDto>> historialTransferencias(@Valid @PageableDefault(size = 10, sort = "fechaMovimiento", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(movimientoService.listarTransferencias(pageable));
    }
}
