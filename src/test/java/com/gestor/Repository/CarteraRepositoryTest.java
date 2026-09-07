package com.gestor.Repository;

import com.gestorBackend.Model.Cartera;
import com.gestorBackend.Repository.CarteraRepository;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import static org.assertj.core.api.Assertions.assertThat;

class CarteraRepositoryTest {

    @Test
    // Verifica que el repositorio declara sus métodos derivados.
    void debeDeclararLasConsultasPersonalizadas() throws NoSuchMethodException {
        assertThat(JpaRepository.class.isAssignableFrom(CarteraRepository.class)).isTrue();
        assertThat(CarteraRepository.class.getMethod("existsByIdCartera", Long.class).getReturnType()).isEqualTo(boolean.class);
        assertThat(CarteraRepository.class.getMethod("getCarteraByIdCartera", Long.class).getReturnType()).isEqualTo(Cartera.class);
    }
}
