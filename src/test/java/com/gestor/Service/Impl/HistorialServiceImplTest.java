package com.gestor.Service.Impl;

import com.gestorBackend.Model.Activo;
import com.gestorBackend.Model.Enum.TipoActivo;
import com.gestorBackend.Model.Historial;
import com.gestorBackend.Repository.ActivoRepository;
import com.gestorBackend.Repository.HistorialRepository;
import com.gestor.support.InMemoryRepositories;
import com.gestorBackend.Service.Impl.HistorialServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.Assertions.assertThat;

class HistorialServiceImplTest {

    private Map<Long, Activo> activoStore;
    private Map<Long, Historial> historialStore;
    private AtomicLong activoSequence;
    private AtomicLong historialSequence;
    private HistorialServiceImpl service;

    @BeforeEach
    void setUp() {
        activoStore = new LinkedHashMap<>();
        historialStore = new LinkedHashMap<>();
        activoSequence = new AtomicLong(0);
        historialSequence = new AtomicLong(0);

        ActivoRepository activoRepository = InMemoryRepositories.activoRepository(activoStore, activoSequence);
        HistorialRepository historialRepository = InMemoryRepositories.historialRepository(historialStore, historialSequence);
        service = new HistorialServiceImpl(historialRepository, activoRepository);
    }

    @Test
    void registrarHistorialActivosDebeGuardarUnSnapshotPorCadaActivo() {
        activoStore.put(1L, new Activo(1L, 10L, "BTC", TipoActivo.CRYPTO, new BigDecimal("100"), LocalDateTime.now()));
        activoStore.put(2L, new Activo(2L, 20L, "ETH", TipoActivo.ETF, new BigDecimal("200"), LocalDateTime.now()));

        LocalDateTime antes = LocalDateTime.now();
        service.registrarHistorialActivos();
        LocalDateTime despues = LocalDateTime.now();

        assertThat(historialStore).hasSize(2);
        assertThat(historialStore.values())
                .allSatisfy(historial -> {
                    assertThat(historial.getIdHistorial()).isNotNull();
                    assertThat(historial.getFechaHistorial()).isBetween(antes, despues);
                });
        assertThat(historialStore.values())
                .extracting(Historial::getIdActivo)
                .containsExactlyInAnyOrder(1L, 2L);
        assertThat(historialStore.values())
                .extracting(Historial::getIdCartera)
                .containsExactlyInAnyOrder(10L, 20L);
        assertThat(historialStore.values())
                .extracting(Historial::getBalance)
                .containsExactlyInAnyOrder(new BigDecimal("100"), new BigDecimal("200"));
    }

    @Test
    void registrarHistorialActivosNoDebeGuardarNadaSiNoHayActivos() {
        service.registrarHistorialActivos();

        assertThat(historialStore).isEmpty();
    }
}
