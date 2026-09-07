package com.gestorBackend.Controller;

import com.gestorBackend.Dto.Request.PerfilRequestDto;
import com.gestorBackend.Dto.Response.PerfilResponseDto;
import com.gestorBackend.Service.PerfilService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
// @CrossOrigin(origins = "*") // @CrossOrigin(origins = "http://localhost:puerto")
@RequestMapping("/api/v1/perfil")
@RequiredArgsConstructor
@Validated
public class PerfilController {

    private final PerfilService perfilService;

    @GetMapping("/{id}")
    public ResponseEntity<PerfilResponseDto> obtenerPerfil(
            @PathVariable @Positive(message = "El id debe ser positivo") Long id) {
        PerfilResponseDto perfil = perfilService.obtenerPerfil(id);
        return ResponseEntity.ok(perfil);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarPerfil(
            @PathVariable @Positive(message = "El id debe ser positivo") Long id, 
            @Valid @RequestBody PerfilRequestDto dto) {
        perfilService.actualizarPerfil(id, dto);
        return ResponseEntity.noContent().build();
    }
}
