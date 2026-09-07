package com.gestorBackend.Dto.Response;

import com.gestorBackend.Dto.Request.CarteraTopRequestDto;
import com.gestorBackend.Dto.TransferenciaDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResumenResponseDto {
    @NotNull private BigDecimal balanceTotal;
    @NotNull private List<CarteraTopRequestDto> carteras;
    @NotNull private List<TransferenciaDto> movimientos;
}
