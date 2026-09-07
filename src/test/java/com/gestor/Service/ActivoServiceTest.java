package com.gestor.Service;

import com.gestorBackend.Model.Activo;
import com.gestorBackend.Service.ActivoService;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ActivoServiceTest {

    @Test
    // Verifica que la interfaz expone las operaciones del servicio.
    void debeDeclararLasOperacionesEsperadas() throws NoSuchMethodException {
        Method guardar = ActivoService.class.getMethod("guardarActivo", Activo.class);
        Method eliminar = ActivoService.class.getMethod("eliminarActivo", Long.class);
        Method aumentar = ActivoService.class.getMethod("aumentarValorActivo", Long.class, BigDecimal.class);
        Method disminuir = ActivoService.class.getMethod("disminuirValorActivo", Long.class, BigDecimal.class);

        assertThat(guardar.getReturnType()).isEqualTo(Activo.class);
        assertThat(eliminar.getReturnType()).isEqualTo(void.class);
        assertThat(aumentar.getReturnType()).isEqualTo(BigDecimal.class);
        assertThat(disminuir.getReturnType()).isEqualTo(BigDecimal.class);
    }
}
