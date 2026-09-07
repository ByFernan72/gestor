package com.gestorBackend.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerfilResponseDto {
    Long idPerfil;
    String nombrePerfil;
    List<String> links;
    LocalDateTime fechaCreacionPerfil;
}
