package com.gestorBackend.Repository;

import com.gestorBackend.Dto.Request.CarteraTopRequestDto;
import com.gestorBackend.Model.Activo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ActivoRepository extends JpaRepository<Activo, Long> {
    boolean existsByIdActivo(Long idActivo);
    Activo getActivoByIdActivo(Long id);
    @Query("Select COALESCE(SUM(a.balance), 0) FROM Activo a")
    BigDecimal balanceTotal();

    @Query("Select COALESCE(SUM(a.balance), 0) FROM Activo a WHERE a.idCartera = :idCartera")
    BigDecimal balanceTotalPorCartera(Long idCartera);

    @Query("SELECT new com.gestorBackend.Dto.Request.CarteraTopRequestDto(a.idCartera, c.nombreCartera, SUM(a.balance))" +
            " FROM Activo a JOIN Cartera c ON a.idCartera = c.idCartera " +
            "GROUP BY a.idCartera, c.nombreCartera " +
            "ORDER BY SUM(a.balance) DESC ")
    List<CarteraTopRequestDto> topCarteras(Pageable pageable);

    List<Activo> findAllByIdCartera(Long idCartera, Sort sort);
}