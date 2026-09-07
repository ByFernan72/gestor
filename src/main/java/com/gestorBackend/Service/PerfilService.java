package com.gestorBackend.Service;

import com.gestorBackend.Dto.Request.PerfilRequestDto;
import com.gestorBackend.Dto.Response.PerfilResponseDto;

public interface PerfilService {
    PerfilResponseDto obtenerPerfil(Long id);
    void actualizarPerfil(Long id, PerfilRequestDto perfilRequestDto);
    void resetearPerfil();
}
