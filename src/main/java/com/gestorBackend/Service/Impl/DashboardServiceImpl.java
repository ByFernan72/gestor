package com.gestorBackend.Service.Impl;

import com.gestorBackend.Dto.Request.CarteraTopRequestDto;
import com.gestorBackend.Dto.Response.DashboardResumenResponseDto;
import com.gestorBackend.Dto.TransferenciaDto;
import com.gestorBackend.Repository.ActivoRepository;
import com.gestorBackend.Repository.MovimientoRepository;
import com.gestorBackend.Service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final ActivoRepository activoRepository;
    private final MovimientoRepository movimientoRepository;

    @Override
    public DashboardResumenResponseDto obtenerResumenDashboard() {
        BigDecimal balanceTotal = activoRepository.balanceTotal();

        List<CarteraTopRequestDto> top = activoRepository.topCarteras(Pageable.ofSize(3));

        List<TransferenciaDto> movimientos = movimientoRepository.findUltimasTransferencias(Pageable.ofSize(5));

        return new DashboardResumenResponseDto(balanceTotal, top, movimientos);
    }
}
