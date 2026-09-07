package com.gestorBackend.Service.Impl;

import com.gestorBackend.Repository.ActivoRepository;
import com.gestorBackend.Repository.CarteraRepository;
import com.gestorBackend.Repository.HistorialRepository;
import com.gestorBackend.Repository.MovimientoRepository;
import com.gestorBackend.Service.AjustesService;
import com.gestorBackend.Service.PerfilService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class AjustesServiceImpl implements AjustesService {

    private final CarteraRepository carteraRepository;
    private final ActivoRepository activoRepository;
    private final MovimientoRepository movimientoRepository;
    private final HistorialRepository historialRepository;
    private final PerfilService perfilService;

    @Override
    @Transactional
    public void resetearDatos() {
        movimientoRepository.deleteAllInBatch();
        historialRepository.deleteAllInBatch();
        activoRepository.deleteAllInBatch();
        carteraRepository.deleteAllInBatch();
        perfilService.resetearPerfil();
    }
}
