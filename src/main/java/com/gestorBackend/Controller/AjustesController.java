package com.gestorBackend.Controller;

import com.gestorBackend.Service.AjustesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
// @CrossOrigin(origins = "*") // @CrossOrigin(origins = "http://localhost:puerto")
@RequestMapping("/api/v1/ajustes")
@RequiredArgsConstructor
public class AjustesController {
    private final AjustesService ajustesService;

    @PostMapping("/reset")
    public ResponseEntity<Void> resetearDatos(){
        ajustesService.resetearDatos();
        return ResponseEntity.noContent().build();
    }
}
