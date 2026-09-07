package com.gestor.Service;

import com.gestorBackend.Dto.Response.DashboardResumenResponseDto;
import com.gestorBackend.Service.DashboardService;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

class DashboardServiceTest {

    @Test
    // Verifica que la interfaz expone la operación principal de dashboard.
    void debeDeclararLasOperacionesEsperadas() throws NoSuchMethodException {
        Method obtenerResumen = DashboardService.class.getMethod("obtenerResumenDashboard");

        assertThat(obtenerResumen.getReturnType()).isEqualTo(DashboardResumenResponseDto.class);
    }
}