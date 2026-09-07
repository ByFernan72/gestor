package com.gestorBackend.Service;

import com.gestorBackend.Model.Activo;
import jakarta.validation.Valid;

import java.math.BigDecimal;

public interface ActivoService {
    Activo guardarActivo(@Valid Activo activo);
    void eliminarActivo(Long id);

    BigDecimal aumentarValorActivo(Long id, BigDecimal balance);
    BigDecimal disminuirValorActivo(Long id, BigDecimal balance);
    Activo actualizarActivo(Long idActivo, Activo activo);
}
