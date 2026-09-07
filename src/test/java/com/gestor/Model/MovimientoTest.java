package com.gestor.Model;

import com.gestorBackend.Model.Movimiento;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class MovimientoTest {

    @Test
    // Verifica que el constructor asigna todos los valores.
    void debeConstruirseYLeerSusCampos() {
        LocalDateTime fecha = LocalDateTime.of(2026, 7, 7, 11, 6);
        Movimiento movimiento = new Movimiento(1L, 2L, 3L, 4L, 5L, new BigDecimal("10.5"), new BigDecimal("9.5"), "Origen", "Destino", "ActivoOrig", "ActivoDest", fecha);

        assertThat(movimiento.getIdMovimiento()).isEqualTo(1L);
        assertThat(movimiento.getIdCarteraOrigen()).isEqualTo(2L);
        assertThat(movimiento.getIdCarteraDestino()).isEqualTo(3L);
        assertThat(movimiento.getIdActivoOrigen()).isEqualTo(4L);
        assertThat(movimiento.getIdActivoDestino()).isEqualTo(5L);
        assertThat(movimiento.getCantidadOrigen()).isEqualByComparingTo("10.5");
        assertThat(movimiento.getCantidadDestino()).isEqualByComparingTo("9.5");
        assertThat(movimiento.getNombreCarteraOrigen()).isEqualTo("Origen");
        assertThat(movimiento.getNombreCarteraDestino()).isEqualTo("Destino");
        assertThat(movimiento.getNombreActivoOrigen()).isEqualTo("ActivoOrig");
        assertThat(movimiento.getNombreActivoDestino()).isEqualTo("ActivoDest");
        assertThat(movimiento.getFechaMovimiento()).isEqualTo(fecha);
    }

    @Test
    // Verifica que el modelo acepta cambios en sus propiedades.
    void debePermitirModificarSusCampos() {
        Movimiento movimiento = new Movimiento();

        movimiento.setIdMovimiento(10L);
        movimiento.setIdCarteraOrigen(20L);
        movimiento.setIdCarteraDestino(30L);
        movimiento.setIdActivoOrigen(40L);
        movimiento.setIdActivoDestino(50L);
        movimiento.setCantidadOrigen(new BigDecimal("100"));
        movimiento.setCantidadDestino(new BigDecimal("95"));
        movimiento.setNombreCarteraOrigen("C1");
        movimiento.setNombreCarteraDestino("C2");
        movimiento.setNombreActivoOrigen("A1");
        movimiento.setNombreActivoDestino("A2");
        movimiento.setFechaMovimiento(LocalDateTime.of(2026, 3, 4, 5, 6));

        assertThat(movimiento).isEqualTo(new Movimiento(10L, 20L, 30L, 40L, 50L, new BigDecimal("100"), new BigDecimal("95"), "C1", "C2", "A1", "A2", LocalDateTime.of(2026, 3, 4, 5, 6)));
        assertThat(movimiento.toString()).contains("100", "95");
    }
}
