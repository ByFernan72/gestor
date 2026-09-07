package com.gestor.Service;

import com.gestorBackend.Service.HistorialService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HistorialServiceTest {

    @Test
    void debeDeclararLaOperacionDeRegistro() throws NoSuchMethodException {
        assertThat(HistorialService.class.getMethod("registrarHistorialActivos").getReturnType()).isEqualTo(void.class);
    }
}
