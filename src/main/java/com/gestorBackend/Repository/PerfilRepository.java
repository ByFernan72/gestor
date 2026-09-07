package com.gestorBackend.Repository;

import com.gestorBackend.Model.Movimiento;
import com.gestorBackend.Model.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {

    Perfil getPerfilByIdPerfil(Long idPerfil);
}
