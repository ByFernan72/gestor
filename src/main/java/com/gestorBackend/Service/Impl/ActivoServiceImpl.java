package com.gestorBackend.Service.Impl;

import com.gestorBackend.Model.Activo;
import com.gestorBackend.Repository.ActivoRepository;
import com.gestorBackend.Service.ActivoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Validated
@RequiredArgsConstructor
public class ActivoServiceImpl implements ActivoService {

    private final ActivoRepository activoRepository;

    @Override
    public Activo guardarActivo(Activo activo){
        activo.setFechaObtencionActivo(LocalDateTime.now());
        return activoRepository.save(activo);
    }

    @Override
    public void eliminarActivo(Long id) {
        if (!activoRepository.existsByIdActivo(id)) {
            throw new IllegalArgumentException("El activo con id " + id + " no existe");
        }
        activoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public BigDecimal aumentarValorActivo(Long id, BigDecimal balance) {
        if (!activoRepository.existsByIdActivo(id)) {
            throw new IllegalArgumentException("El activo con id " + id + " no existe");
        }
        Activo activo = activoRepository.getActivoByIdActivo(id);

        activo.setBalance(activo.getBalance().add(balance));
        activoRepository.save(activo);
        return activo.getBalance();
    }

    @Override
    @Transactional
    public BigDecimal disminuirValorActivo(Long id, BigDecimal balance) {
        if (!activoRepository.existsByIdActivo(id)) {
            throw new IllegalArgumentException("El activo con id " + id + " no existe");
        }
        Activo activo = activoRepository.getActivoByIdActivo(id);

        activo.setBalance(activo.getBalance().subtract(balance));
        activoRepository.save(activo);
        return activo.getBalance();
    }

    @Override
    @Transactional
    public Activo actualizarActivo(Long idActivo, Activo activoDetalles) {
        if (!activoRepository.existsByIdActivo(idActivo)) {
            throw new IllegalArgumentException("El activo con id " + idActivo + " no existe");
        }
        Activo activo = activoRepository.getActivoByIdActivo(idActivo);
        activo.setNombreActivo(activoDetalles.getNombreActivo());
        activo.setTipoActivo(activoDetalles.getTipoActivo());
        activo.setBalance(activoDetalles.getBalance());
        if (activoDetalles.getFechaObtencionActivo() != null) {
            activo.setFechaObtencionActivo(activoDetalles.getFechaObtencionActivo());
        }
        return activoRepository.save(activo);
    }
}
