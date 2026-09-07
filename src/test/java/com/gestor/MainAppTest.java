package com.gestor;

import com.gestorBackend.MainApp;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;

class MainAppTest {

    @Test
    // Verifica que el arranque principal no falle.
    void mainNoDebeLanzarErrores() {
        assertThatCode(() -> MainApp.main(new String[]{"--server.port=0"})).doesNotThrowAnyException();
    }
}
