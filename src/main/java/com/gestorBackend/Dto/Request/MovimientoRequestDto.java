package com.gestorBackend.Dto.Request;

import com.gestorBackend.Model.Enum.TipoActivo;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoRequestDto {
    @NotNull(message = "El id de cartera origen es obligatorio")
    @Positive(message = "El id de cartera origen debe ser positivo")
    private Long idCarteraOrigen;

    @NotNull(message = "El id de cartera destino es obligatorio")
    @Positive(message = "El id de cartera destino debe ser positivo")
    private Long idCarteraDestino;

    @NotNull(message = "El id de activo origen es obligatorio")
    @Positive(message = "El id de activo origen debe ser positivo")
    private Long idActivoOrigen;

    @Positive(message = "El id de activo destino debe ser positivo")
    private Long idActivoDestino;

    @NotNull(message = "La cantidad de origen es obligatoria")
    @Positive(message = "La cantidad de origen debe ser mayor a 0")
    private BigDecimal cantidadOrigen;

    @NotNull(message = "La cantidad de destino es obligatoria")
    @Positive(message = "La cantidad de destino debe ser mayor a 0")
    private BigDecimal cantidadDestino;

    @Size(max = 100, message = "El nombre del nuevo activo no puede exceder los 100 caracteres")
    private String nombreNuevoActivo; // Puede ser null si el activo destino ya existe

    private TipoActivo tipoNuevoActivo; // Puede ser null si el activo destino ya existe
}
