package com.gestorBackend.Repository;

import com.gestorBackend.Dto.TransferenciaDto;
import com.gestorBackend.Model.Movimiento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    @Query("SELECT " +
            "m.idMovimiento AS idMovimiento, " +
            "m.nombreCarteraOrigen AS carteraOrigen, " +
            "m.nombreActivoOrigen AS activo, " +
            "m.cantidadOrigen AS enviado, " +
            "m.nombreCarteraDestino AS carteraDestino, " +
            "m.nombreActivoDestino AS activoRecibido, " +
            "m.cantidadDestino AS recibido, " +
            "m.fechaMovimiento AS fechaMovimiento " +
            "FROM Movimiento m " +
            "ORDER BY m.fechaMovimiento DESC")
    List<TransferenciaDto> findUltimasTransferencias(Pageable pageable);

    @Query(value = "SELECT " +
            "m.idMovimiento AS idMovimiento, " +
            "m.nombreCarteraOrigen AS carteraOrigen, " +
            "m.nombreActivoOrigen AS activo, " +
            "m.cantidadOrigen AS enviado, " +
            "m.nombreCarteraDestino AS carteraDestino, " +
            "m.nombreActivoDestino AS activoRecibido, " +
            "m.cantidadDestino AS recibido, " +
            "m.fechaMovimiento AS fechaMovimiento " +
            "FROM Movimiento m",
            countQuery = "SELECT count(m) FROM Movimiento m"
    )
    Page<TransferenciaDto> findAllTransferencias(Pageable pageable);
}
