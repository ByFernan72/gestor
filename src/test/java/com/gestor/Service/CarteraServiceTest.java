package com.gestor.Service;

import com.gestorBackend.Dto.Response.CarteraListadoResponseDto;
import com.gestorBackend.Model.Cartera;
import com.gestorBackend.Service.CarteraService;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CarteraServiceTest {

    @Test
    // Verifica que la interfaz expone las operaciones del servicio.
    void debeDeclararLasOperacionesEsperadas() throws NoSuchMethodException {
        Method listar = CarteraService.class.getMethod("listarCarteras");
        Method guardar = CarteraService.class.getMethod("guardarCartera", Cartera.class);
        Method eliminar = CarteraService.class.getMethod("eliminarCartera", Long.class);
        Method modificarNombre = CarteraService.class.getMethod("modificarNombreCartera", Long.class, String.class);
        Method modificarDescripcion = CarteraService.class.getMethod("modificarDescripcionCartera", Long.class, String.class);
        Method obtenerCarteras = CarteraService.class.getMethod("obtenerCarteras", org.springframework.data.domain.Pageable.class);

        assertThat(listar.getReturnType()).isEqualTo(List.class);
        assertThat(guardar.getReturnType()).isEqualTo(Cartera.class);
        assertThat(eliminar.getReturnType()).isEqualTo(void.class);
        assertThat(modificarNombre.getReturnType()).isEqualTo(void.class);
        assertThat(modificarDescripcion.getReturnType()).isEqualTo(void.class);
        assertThat(obtenerCarteras.getReturnType()).isEqualTo(CarteraListadoResponseDto.class);
    }
}