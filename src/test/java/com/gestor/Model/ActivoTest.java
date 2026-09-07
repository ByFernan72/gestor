package com.gestor.Model;

import com.gestorBackend.Model.Activo;
import com.gestorBackend.Model.Enum.TipoActivo;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ActivoTest {

    @Test
    // Verifica que el constructor guarda todos los datos recibidos.
    void debeConstruirseYLeerSusCampos() {
        LocalDateTime fecha = LocalDateTime.of(2026, 7, 7, 11, 6);
        Activo activo = new Activo(1L, 2L, "BTC", TipoActivo.CRYPTO, new BigDecimal("100.50"), fecha);

        assertThat(activo.getIdActivo()).isEqualTo(1L);
        assertThat(activo.getIdCartera()).isEqualTo(2L);
        assertThat(activo.getNombreActivo()).isEqualTo("BTC");
        assertThat(activo.getTipoActivo()).isEqualTo(TipoActivo.CRYPTO);
        assertThat(activo.getBalance()).isEqualByComparingTo("100.50");
        assertThat(activo.getFechaObtencionActivo()).isEqualTo(fecha);
    }

    @Test
    // Verifica que los setters, equals y toString siguen funcionando.
    void debePermitirModificarSusCampos() {
        Activo activo = new Activo();

        activo.setIdActivo(10L);
        activo.setIdCartera(20L);
        activo.setNombreActivo("ETH");
        activo.setTipoActivo(TipoActivo.ETF);
        activo.setBalance(new BigDecimal("12.75"));
        activo.setFechaObtencionActivo(LocalDateTime.of(2026, 1, 1, 0, 0));

        assertThat(activo).isEqualTo(new Activo(10L, 20L, "ETH", TipoActivo.ETF, new BigDecimal("12.75"), LocalDateTime.of(2026, 1, 1, 0, 0)));
        assertThat(activo.toString()).contains("ETH", "12.75", "ETF");
    }
}
