package com.gestor.Model;

import com.gestorBackend.Model.Cartera;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class CarteraTest {

    @Test
    // Verifica que el constructor conserva los valores iniciales.
    void debeConstruirseYLeerSusCampos() {
        LocalDateTime fecha = LocalDateTime.of(2026, 7, 7, 11, 6);
        Cartera cartera = new Cartera(1L, "Principal", "Cartera de pruebas", fecha);

        assertThat(cartera.getIdCartera()).isEqualTo(1L);
        assertThat(cartera.getNombreCartera()).isEqualTo("Principal");
        assertThat(cartera.getDescripcion()).isEqualTo("Cartera de pruebas");
        assertThat(cartera.getCreatedAt()).isEqualTo(fecha);
    }

    @Test
    // Verifica que los setters, equals y toString funcionan bien.
    void debePermitirModificarSusCampos() {
        Cartera cartera = new Cartera();

        cartera.setIdCartera(9L);
        cartera.setNombreCartera("Ahorro");
        cartera.setDescripcion("Fondo principal");
        cartera.setCreatedAt(LocalDateTime.of(2026, 1, 2, 3, 4));

        assertThat(cartera).isEqualTo(new Cartera(9L, "Ahorro", "Fondo principal", LocalDateTime.of(2026, 1, 2, 3, 4)));
        assertThat(cartera.toString()).contains("Ahorro", "Fondo principal");
    }
}
