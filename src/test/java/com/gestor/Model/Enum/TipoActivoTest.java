package com.gestor.Model.Enum;

import com.gestorBackend.Model.Enum.TipoActivo;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TipoActivoTest {

    @Test
    // Comprueba que el enum expone todos sus valores.
    void debeExponerLosValoresEsperados() {
        assertThat(TipoActivo.values()).containsExactly(
                TipoActivo.FIAT,
                TipoActivo.CRYPTO,
                TipoActivo.FONDO_INDEXADO,
                TipoActivo.BONOS_TESORO,
                TipoActivo.ETF,
                TipoActivo.ACCION,
                TipoActivo.OTHER
        );
    }

    @Test
    // Valida que el acceso por nombre resuelve el valor correcto.
    void valueOfDebeResolverElEnum() {
        assertThat(TipoActivo.valueOf("CRYPTO")).isEqualTo(TipoActivo.CRYPTO);
    }
}
