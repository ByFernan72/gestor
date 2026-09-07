package com.gestor.Service;

import com.gestorBackend.Model.Enum.TipoActivo;
import com.gestorBackend.Service.MovimientoService;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class MovimientoServiceTest {

    @Test
    // Verifica que la interfaz expone la transferencia de balance.
    void debeDeclararLaTransferenciaDeBalance() throws NoSuchMethodException {
        Method transferir = MovimientoService.class.getMethod(
                "transferirBalance",
                Long.class,
                Long.class,
                Long.class,
                Long.class,
                BigDecimal.class,
                BigDecimal.class,
                String.class,
                TipoActivo.class
        );

        assertThat(transferir.getReturnType()).isEqualTo(void.class);
    }
}
