package com.gestorBackend.Controller;

import com.gestorBackend.Model.Activo;
import com.gestorBackend.Service.ActivoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
// @CrossOrigin(origins = "*") // @CrossOrigin(origins = "http://localhost:puerto")
@RequestMapping("/api/v1/activo")
@RequiredArgsConstructor
@Validated
public class ActivoController {

    private final ActivoService activoService;

    @PostMapping
    public ResponseEntity<Activo> crearActivo(@Valid @RequestBody Activo activo){
        Activo nuevoActivo = activoService.guardarActivo(activo);
        return ResponseEntity.ok(nuevoActivo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarActivo(@PathVariable @Positive(message = "El id debe ser positivo") Long id){
        activoService.eliminarActivo(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Activo> actualizarActivo(
            @PathVariable @Positive(message = "El id debe ser positivo") Long id, 
            @Valid @RequestBody Activo activo){
        Activo actualizado = activoService.actualizarActivo(id, activo);
        return ResponseEntity.ok(actualizado);
    }
}
