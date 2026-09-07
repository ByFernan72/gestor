package com.gestorBackend.Dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface TransferenciaDto {
    Long getIdMovimiento();
    String getCarteraOrigen();
    String getActivo();
    BigDecimal getEnviado();
    String getCarteraDestino();
    String getActivoRecibido();
    BigDecimal getRecibido();
    LocalDateTime getFechaMovimiento();
}
