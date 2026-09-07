package com.gestorBackend.Model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name ="historial")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Historial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historial")
    private Long idHistorial;

    @Column(name = "id_cartera")
    private Long idCartera;

    @Column(name = "id_activo")
    private Long idActivo;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "fecha_historial")
    private LocalDateTime fechaHistorial;
}
