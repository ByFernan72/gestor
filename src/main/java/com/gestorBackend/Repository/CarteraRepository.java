package com.gestorBackend.Repository;

import com.gestorBackend.Dto.Request.CarteraListadoRequestDto;
import com.gestorBackend.Model.Cartera;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CarteraRepository extends JpaRepository<Cartera, Long> {
    boolean existsByIdCartera(Long idCartera);
    Cartera getCarteraByIdCartera(Long id);

    @Query("Select new com.gestorBackend.Dto.Request.CarteraListadoRequestDto(" +
            "c.idCartera, " +
            "c.nombreCartera, " +
            "c.descripcion, " +
            "SUM(a.balance), " +
            "COUNT(a.idActivo)) " +
            "FROM Cartera c LEFT JOIN Activo a ON c.idCartera = a.idCartera " +
            "GROUP BY c.idCartera, c.nombreCartera, c.descripcion")
    Page<CarteraListadoRequestDto> listadoCarteras(Pageable pageable);


}