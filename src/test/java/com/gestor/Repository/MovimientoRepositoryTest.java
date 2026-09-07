package com.gestor.Repository;

import com.gestorBackend.Repository.MovimientoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import static org.assertj.core.api.Assertions.assertThat;

class MovimientoRepositoryTest {

    @Test
    // Verifica que el repositorio hereda de JpaRepository.
    void debeExtenderJpaRepository() {
        assertThat(JpaRepository.class.isAssignableFrom(MovimientoRepository.class)).isTrue();
    }
}
