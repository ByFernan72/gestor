package com.gestorBackend.Service.Impl;

import com.gestorBackend.Dto.Request.PerfilRequestDto;
import com.gestorBackend.Dto.Response.PerfilResponseDto;
import com.gestorBackend.Model.Perfil;
import com.gestorBackend.Repository.PerfilRepository;
import com.gestorBackend.Service.PerfilService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class PerfilServiceImpl implements PerfilService {

    private final PerfilRepository perfilRepository;


    @Override
    public PerfilResponseDto obtenerPerfil(Long id) {
        Perfil perfil;
        if (!perfilRepository.existsById(id)) {
            java.util.List<Perfil> todos = perfilRepository.findAll();
            if (!todos.isEmpty()) {
                perfil = todos.get(0);
            } else {
                Perfil defaultPerfil = new Perfil();
                defaultPerfil.setNombrePerfil("");
                defaultPerfil.setLinks(new java.util.ArrayList<>());
                defaultPerfil.setFechaCreacionPerfil(java.time.LocalDateTime.now());
                perfil = perfilRepository.save(defaultPerfil);
            }
        } else {
            perfil = perfilRepository.getPerfilByIdPerfil(id);
        }

        return new PerfilResponseDto(
            perfil.getIdPerfil(),
            perfil.getNombrePerfil(),
            perfil.getLinks(),
            perfil.getFechaCreacionPerfil()
        );
    }

    @Override
    @Transactional
    public void actualizarPerfil(Long id, PerfilRequestDto perfilRequestDto) {
        Perfil perfil;
        if (!perfilRepository.existsById(id)) {
            java.util.List<Perfil> todos = perfilRepository.findAll();
            if (!todos.isEmpty()) {
                perfil = todos.get(0);
            } else {
                perfil = new Perfil();
                perfil.setFechaCreacionPerfil(java.time.LocalDateTime.now());
            }
        } else {
            perfil = perfilRepository.getPerfilByIdPerfil(id);
        }

        perfil.setNombrePerfil(perfilRequestDto.getNombrePerfil());
        perfil.setLinks(perfilRequestDto.getLinks() != null ? perfilRequestDto.getLinks() : new java.util.ArrayList<>());

        perfilRepository.save(perfil);
    }

    @Override
    @Transactional
    public void resetearPerfil() {
        perfilRepository.deleteAll();
    }
}
