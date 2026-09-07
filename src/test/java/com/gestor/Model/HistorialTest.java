package com.gestor.Model;

import com.gestorBackend.Model.Historial;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class HistorialTest {

    @Test
    // Verifica que el constructor conserva los valores del historial.
    void debeConstruirseYLeerSusCampos() {
        LocalDateTime fecha = LocalDateTime.of(2026, 7, 7, 11, 6);
        Historial historial = new Historial(1L, 2L, 3L, new BigDecimal("55.10"), fecha);

        assertThat(historial.getIdHistorial()).isEqualTo(1L);
        assertThat(historial.getIdCartera()).isEqualTo(2L);
        assertThat(historial.getIdActivo()).isEqualTo(3L);
        assertThat(historial.getBalance()).isEqualByComparingTo("55.10");
        assertThat(historial.getFechaHistorial()).isEqualTo(fecha);
    }

    @Test
    // Verifica que el constructor sin argumentos deja los campos vacios.
    void debeCrearInstanciaVacia() {
        Historial historial = new Historial();

        assertThat(historial.getIdHistorial()).isNull();
        assertThat(historial.getIdCartera()).isNull();
        assertThat(historial.getIdActivo()).isNull();
        assertThat(historial.getBalance()).isNull();
        assertThat(historial.getFechaHistorial()).isNull();
    }

    @Test
    // Verifica que el objeto se puede modificar y comparar.
    void debePermitirModificarSusCampos() {
        Historial historial = new Historial();

        historial.setIdHistorial(11L);
        historial.setIdCartera(22L);
        historial.setIdActivo(33L);
        historial.setBalance(new BigDecimal("77.25"));
        historial.setFechaHistorial(LocalDateTime.of(2026, 2, 3, 4, 5));

        assertThat(historial).isEqualTo(new Historial(11L, 22L, 33L, new BigDecimal("77.25"), LocalDateTime.of(2026, 2, 3, 4, 5)));
        assertThat(historial.toString()).contains("77.25");
    }

    @Test
    // Verifica que equals y hashCode dependen de los mismos datos.
    void debeCompararIgualObjetosConLosMismosDatos() {
        LocalDateTime fecha = LocalDateTime.of(2026, 4, 5, 6, 7);
        Historial primero = new Historial(1L, 2L, 3L, new BigDecimal("10.00"), fecha);
        Historial segundo = new Historial(1L, 2L, 3L, new BigDecimal("10.00"), fecha);

        assertThat(primero).isEqualTo(segundo);
        assertThat(primero.hashCode()).isEqualTo(segundo.hashCode());
    }

    @Test
    // Verifica que toString expone la informacion principal.
    void toStringDebeContenerLosCamposPrincipales() {
        Historial historial = new Historial(1L, 2L, 3L, new BigDecimal("99.99"), LocalDateTime.of(2026, 5, 6, 7, 8));

        assertThat(historial.toString()).contains("1", "2", "3", "99.99");
    }
}
