package com.gestor.Repository;

import com.gestorBackend.Model.Activo;
import com.gestorBackend.Repository.ActivoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import static org.assertj.core.api.Assertions.assertThat;

class ActivoRepositoryTest {

    @Test
    // Verifica que el repositorio declara sus métodos derivados.
    void debeDeclararLasConsultasPersonalizadas() throws NoSuchMethodException {
        assertThat(JpaRepository.class.isAssignableFrom(ActivoRepository.class)).isTrue();
        assertThat(ActivoRepository.class.getMethod("existsByIdActivo", Long.class).getReturnType()).isEqualTo(boolean.class);
        assertThat(ActivoRepository.class.getMethod("getActivoByIdActivo", Long.class).getReturnType()).isEqualTo(Activo.class);
    }
}
