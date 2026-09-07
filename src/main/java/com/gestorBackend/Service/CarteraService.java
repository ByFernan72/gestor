package com.gestorBackend.Service;

import com.gestorBackend.Dto.Response.CarteraListadoResponseDto;
import com.gestorBackend.Dto.Request.CrearCarteraRequestDto;
import com.gestorBackend.Dto.Request.ObtenerDatosCompletosCarteraRequestDto;
import com.gestorBackend.Model.Cartera;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface CarteraService {
    List<Cartera> listarCarteras();
    Cartera guardarCartera(@Valid Cartera cartera);
    Cartera guardarCarteraDto(@Valid CrearCarteraRequestDto crearCarteraRequestDto);
    ObtenerDatosCompletosCarteraRequestDto obtenerDatosCompletos(Long idCartera, Sort sort);
    void eliminarCartera(Long id);
    void modificarNombreCartera(Long id, String nuevoNombre);
    void modificarDescripcionCartera(Long id, String nuevaDescripcion);
    CarteraListadoResponseDto obtenerCarteras(Pageable pageable);

}
