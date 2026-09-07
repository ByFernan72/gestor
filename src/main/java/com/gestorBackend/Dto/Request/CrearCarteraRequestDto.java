package com.gestorBackend.Dto.Request;

import com.gestorBackend.Model.Enum.TipoActivo;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrearCarteraRequestDto {
    @NotBlank(message = "El nombre de la cartera no puede estar vacío")
    @Size(min = 1, max = 100, message = "El nombre de la cartera debe tener entre 1 y 100 caracteres")
    private String nombreCartera;

    @Size(max = 500, message = "La descripción no puede exceder los 500 caracteres")
    private String descripcion;

    @Valid
    private List<crearActivoRequestDto> activos;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class crearActivoRequestDto {
        @NotBlank(message = "El nombre del activo no puede estar vacío")
        @Size(min = 1, max = 100, message = "El nombre del activo debe tener entre 1 y 100 caracteres")
        private String nombreActivo;

        @NotNull(message = "El tipo de activo es obligatorio")
        @Enumerated(EnumType.STRING)
        private TipoActivo tipo;

        @NotNull(message = "El balance no puede ser nulo")
        @PositiveOrZero(message = "El balance debe ser mayor o igual a 0")
        private BigDecimal balance;
    }
}
