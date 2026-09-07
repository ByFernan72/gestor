package com.gestor.Service.Impl;

import com.gestorBackend.Model.Cartera;
import com.gestorBackend.Repository.ActivoRepository;
import com.gestorBackend.Repository.CarteraRepository;
import com.gestor.support.InMemoryRepositories;
import com.gestorBackend.Service.Impl.CarteraServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CarteraServiceImplTest {

    private Map<Long, Cartera> store;
    private AtomicLong sequence;
    private CarteraRepository repository;
    private CarteraServiceImpl service;

    @BeforeEach
    // Prepara un repositorio en memoria para aislar el servicio.
    void setUp() {
        store = new LinkedHashMap<>();
        sequence = new AtomicLong(0);
        repository = InMemoryRepositories.carteraRepository(store, sequence);
        ActivoRepository activoRepository = InMemoryRepositories.activoRepository(InMemoryRepositories.newActivoStore(), new AtomicLong(0));
        service = new CarteraServiceImpl(repository, activoRepository);
    }

    @Test
    // Verifica que listar devuelve todas las carteras.
    void listarCarterasDebeDevolverTodasLasCarteras() {
        store.put(1L, new Cartera(1L, "Principal", "Uno", LocalDateTime.now()));
        store.put(2L, new Cartera(2L, "Ahorro", "Dos", LocalDateTime.now()));

        List<Cartera> carteras = service.listarCarteras();

        assertThat(carteras).hasSize(2);
        assertThat(carteras).extracting(Cartera::getNombreCartera).containsExactly("Principal", "Ahorro");
    }

    @Test
    // Verifica que guardar persiste y asigna id.
    void guardarCarteraDebePersistirLaEntidad() {
        Cartera cartera = new Cartera(null, "Principal", "Cartera de pruebas", LocalDateTime.now());

        Cartera guardada = service.guardarCartera(cartera);

        assertThat(guardada.getIdCartera()).isEqualTo(1L);
        assertThat(store).hasSize(1);
        assertThat(store.get(1L).getDescripcion()).isEqualTo("Cartera de pruebas");
    }

    @Test
    // Verifica que eliminar borra una cartera existente.
    void eliminarCarteraDebeBorrarCuandoExiste() {
        store.put(1L, new Cartera(1L, "Principal", "Uno", LocalDateTime.now()));

        service.eliminarCartera(1L);

        assertThat(store).isEmpty();
    }

    @Test
    // Verifica que eliminar falla si no existe la cartera.
    void eliminarCarteraDebeFallarCuandoNoExiste() {
        assertThatThrownBy(() -> service.eliminarCartera(99L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("La cartera con id 99 no existe");
    }

    @Test
    // Verifica que modificar nombre actualiza la entidad.
    void modificarNombreCarteraDebeActualizarElNombre() {
        store.put(1L, new Cartera(1L, "Principal", "Uno", LocalDateTime.now()));

        service.modificarNombreCartera(1L, "NuevoNombre");

        assertThat(store.get(1L).getNombreCartera()).isEqualTo("NuevoNombre");
    }

    @Test
    // Verifica que modificar nombre falla si no existe la cartera.
    void modificarNombreCarteraDebeFallarCuandoNoExiste() {
        assertThatThrownBy(() -> service.modificarNombreCartera(99L, "NuevoNombre"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("La cartera con id 99 no existe");
    }

    @Test
    // Verifica que modificar descripción actualiza la entidad.
    void modificarDescripcionCarteraDebeActualizarLaDescripcion() {
        store.put(1L, new Cartera(1L, "Principal", "Uno", LocalDateTime.now()));

        service.modificarDescripcionCartera(1L, "Nueva descripcion");

        assertThat(store.get(1L).getDescripcion()).isEqualTo("Nueva descripcion");
    }

    @Test
    // Verifica que modificar descripción falla si no existe la cartera.
    void modificarDescripcionCarteraDebeFallarCuandoNoExiste() {
        assertThatThrownBy(() -> service.modificarDescripcionCartera(99L, "Nueva descripcion"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("La cartera con id 99 no existe");
    }
}
