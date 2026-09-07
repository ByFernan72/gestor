package com.gestor.Service.Impl;

import com.gestorBackend.Dto.Request.CarteraTopRequestDto;
import com.gestorBackend.Dto.Response.DashboardResumenResponseDto;
import com.gestorBackend.Dto.TransferenciaDto;
import com.gestorBackend.Repository.ActivoRepository;
import com.gestorBackend.Repository.MovimientoRepository;
import com.gestorBackend.Service.Impl.DashboardServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DashboardServiceImplTest {

    @Mock
    private ActivoRepository activoRepository;

    @Mock
    private MovimientoRepository movimientoRepository;

    @InjectMocks
    private DashboardServiceImpl dashboardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void obtenerResumenDashboard_debeDevolverElDashboardCompleto() {
        // Arrange
        BigDecimal balanceSimulado = new BigDecimal("15000.50");
        List<CarteraTopRequestDto> topCarterasSimuladas = List.of(
                new CarteraTopRequestDto(1L, "Cartera A", new BigDecimal("10000.00")),
                new CarteraTopRequestDto(2L, "Cartera B", new BigDecimal("5000.50"))
        );
        
        // Simular interface DTO anónimamente
        TransferenciaDto transferenciaDto = new TransferenciaDto() {
            @Override public Long getIdMovimiento() { return 1L; }
            @Override public String getCarteraOrigen() { return "Cartera A"; }
            @Override public String getActivo() { return "BTC"; }
            @Override public BigDecimal getEnviado() { return new BigDecimal("100"); }
            @Override public String getCarteraDestino() { return "Cartera B"; }
            @Override public String getActivoRecibido() { return "ETH"; }
            @Override public BigDecimal getRecibido() { return new BigDecimal("10"); }
            @Override public LocalDateTime getFechaMovimiento() { return LocalDateTime.now(); }
        };
        
        List<TransferenciaDto> transferenciasSimuladas = List.of(transferenciaDto);

        when(activoRepository.balanceTotal()).thenReturn(balanceSimulado);
        when(activoRepository.topCarteras(any(Pageable.class))).thenReturn(topCarterasSimuladas);
        when(movimientoRepository.findUltimasTransferencias(any(Pageable.class))).thenReturn(transferenciasSimuladas);

        // Act
        DashboardResumenResponseDto respuesta = dashboardService.obtenerResumenDashboard();

        // Assert
        assertThat(respuesta).isNotNull();
        assertThat(respuesta.getBalanceTotal()).isEqualTo(balanceSimulado);
        assertThat(respuesta.getCarteras()).hasSize(2).isEqualTo(topCarterasSimuladas);
        assertThat(respuesta.getMovimientos()).hasSize(1).isEqualTo(transferenciasSimuladas);

        verify(activoRepository, times(1)).balanceTotal();
        verify(activoRepository, times(1)).topCarteras(any(Pageable.class));
        verify(movimientoRepository, times(1)).findUltimasTransferencias(any(Pageable.class));
    }
}