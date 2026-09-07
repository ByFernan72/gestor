package com.gestorBackend.Controller;

import com.gestorBackend.Dto.Response.CarteraListadoResponseDto;
import com.gestorBackend.Dto.Request.CrearCarteraRequestDto;
import com.gestorBackend.Dto.Request.ObtenerDatosCompletosCarteraRequestDto;
import com.gestorBackend.Model.Cartera;
import com.gestorBackend.Service.CarteraService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
// @CrossOrigin(origins = "*") // @CrossOrigin(origins = "http://localhost:puerto")
@RequestMapping("/api/v1/cartera")
@RequiredArgsConstructor
@Validated
public class CarteraController {

    private final CarteraService carteraService;

    @GetMapping
    public ResponseEntity<List<Cartera>> listarCarteras(){
        List<Cartera> listadoCarteras = carteraService.listarCarteras();
        return ResponseEntity.ok(listadoCarteras);
    }

    @GetMapping("/listado")
    public ResponseEntity<CarteraListadoResponseDto> listarTodasCarteras(
            @PageableDefault(size = 10, sort = "nombreCartera", direction = Sort.Direction.ASC) Pageable pageable) {
            
        return ResponseEntity.ok(carteraService.obtenerCarteras(pageable));
    }

    @PatchMapping(value = {"/{id}/nombre", "/{id}/{nuevoNombre}"})
    public ResponseEntity<Void> modificarNombreCartera(
            @PathVariable @Positive(message = "El id debe ser positivo") Long id,
            @PathVariable(required = false) String nuevoNombre,
            @RequestParam(required = false) String nuevoNombreParam){

        String nombreFinal = (nuevoNombreParam != null && !nuevoNombreParam.isBlank()) 
                ? nuevoNombreParam 
                : nuevoNombre;

        if (nombreFinal == null || nombreFinal.isBlank()) {
            throw new IllegalArgumentException("El nuevo nombre de la cartera no puede estar vacío");
        }
        if (nombreFinal.length() > 100) {
            throw new IllegalArgumentException("El nombre de la cartera no puede superar los 100 caracteres");
        }

        carteraService.modificarNombreCartera(id, nombreFinal.trim());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/descripcion")
    public ResponseEntity<Void> modificarDescripcion(
            @PathVariable @Positive(message = "El id debe ser positivo") Long id,
            @RequestParam(required = false, defaultValue = "") @Size(max = 500, message = "La descripción no puede superar los 500 caracteres") String nuevaDescripcion) {

        carteraService.modificarDescripcionCartera(id, nuevaDescripcion);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObtenerDatosCompletosCarteraRequestDto> obtenerDatosCompletos(
            @PathVariable @Positive(message = "El id debe ser positivo") Long id, 
            Sort sort) {
        ObtenerDatosCompletosCarteraRequestDto datos = carteraService.obtenerDatosCompletos(id, sort);
        return ResponseEntity.ok(datos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCartera(
            @PathVariable @Positive(message = "El id debe ser positivo") Long id){
        carteraService.eliminarCartera(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping({"", "/crearCartera"})
    public ResponseEntity<Cartera> crearCartera(@Valid @RequestBody CrearCarteraRequestDto carteraDto){
        Cartera nueva = carteraService.guardarCarteraDto(carteraDto);
        return ResponseEntity.ok(nueva);
    }
}
