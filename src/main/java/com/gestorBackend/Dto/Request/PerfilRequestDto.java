package com.gestorBackend.Dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerfilRequestDto {
    @NotBlank(message = "El nombre de perfil no puede estar en blanco")
    @Size(min = 1, max = 100, message = "El nombre de perfil debe tener entre 1 y 100 caracteres")
    private String nombrePerfil;

    @Size(max = 50, message = "No se pueden registrar más de 50 enlaces")
    private List<@NotBlank(message = "El enlace no puede estar vacío") @Size(max = 255, message = "El enlace no puede superar los 255 caracteres") String> links;
}
