package com.gestor.Repository;

import com.gestorBackend.Repository.HistorialRepository;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import static org.assertj.core.api.Assertions.assertThat;

class HistorialRepositoryTest {

    @Test
    void debeExtenderJpaRepository() {
        assertThat(JpaRepository.class.isAssignableFrom(HistorialRepository.class)).isTrue();
    }
}
