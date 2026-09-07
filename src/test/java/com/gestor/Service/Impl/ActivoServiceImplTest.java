package com.gestor.Service.Impl;

import com.gestorBackend.Model.Activo;
import com.gestorBackend.Model.Enum.TipoActivo;
import com.gestorBackend.Repository.ActivoRepository;
import com.gestor.support.InMemoryRepositories;
import com.gestorBackend.Service.Impl.ActivoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ActivoServiceImplTest {

    private Map<Long, Activo> store;
    private AtomicLong sequence;
    private ActivoRepository repository;
    private ActivoServiceImpl service;

    @BeforeEach
    // Prepara un repositorio en memoria para aislar el servicio.
    void setUp() {
        store = new LinkedHashMap<>();
        sequence = new AtomicLong(0);
        repository = InMemoryRepositories.activoRepository(store, sequence);
        service = new ActivoServiceImpl(repository);
    }

    @Test
    // Verifica que guardar persiste y asigna id.
    void guardarActivoDebePersistirElRegistro() {
        Activo activo = new Activo(null, 1L, "BTC", TipoActivo.CRYPTO, new BigDecimal("100"), LocalDateTime.now());

        Activo guardado = service.guardarActivo(activo);

        assertThat(guardado.getIdActivo()).isEqualTo(1L);
        assertThat(store).hasSize(1);
        assertThat(store.get(1L).getNombreActivo()).isEqualTo("BTC");
    }

    @Test
    // Verifica que eliminar borra un registro existente.
    void eliminarActivoDebeBorrarCuandoExiste() {
        Activo activo = new Activo(1L, 1L, "BTC", TipoActivo.CRYPTO, new BigDecimal("100"), LocalDateTime.now());
        store.put(1L, activo);

        service.eliminarActivo(1L);

        assertThat(store).isEmpty();
    }

    @Test
    // Verifica que eliminar falla si no existe el activo.
    void eliminarActivoDebeFallarCuandoNoExiste() {
        assertThatThrownBy(() -> service.eliminarActivo(99L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El activo con id 99 no existe");
    }

    @Test
    // Verifica que aumentar suma el balance actual.
    void aumentarValorActivoDebeSumarElBalance() {
        Activo activo = new Activo(1L, 1L, "BTC", TipoActivo.CRYPTO, new BigDecimal("100"), LocalDateTime.now());
        store.put(1L, activo);

        BigDecimal balance = service.aumentarValorActivo(1L, new BigDecimal("25.50"));

        assertThat(balance).isEqualByComparingTo("125.50");
        assertThat(store.get(1L).getBalance()).isEqualByComparingTo("125.50");
    }

    @Test
    // Verifica que aumentar falla si el activo no existe.
    void aumentarValorActivoDebeFallarCuandoNoExiste() {
        assertThatThrownBy(() -> service.aumentarValorActivo(99L, new BigDecimal("1")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El activo con id 99 no existe");
    }

    @Test
    // Verifica que disminuir resta el balance actual.
    void disminuirValorActivoDebeRestarElBalance() {
        Activo activo = new Activo(1L, 1L, "BTC", TipoActivo.CRYPTO, new BigDecimal("100"), LocalDateTime.now());
        store.put(1L, activo);

        BigDecimal balance = service.disminuirValorActivo(1L, new BigDecimal("40"));

        assertThat(balance).isEqualByComparingTo("60");
        assertThat(store.get(1L).getBalance()).isEqualByComparingTo("60");
    }

    @Test
    // Verifica que disminuir falla si el activo no existe.
    void disminuirValorActivoDebeFallarCuandoNoExiste() {
        assertThatThrownBy(() -> service.disminuirValorActivo(99L, new BigDecimal("1")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El activo con id 99 no existe");
    }
}
