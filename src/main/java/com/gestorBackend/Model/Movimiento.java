package com.gestorBackend.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name ="movimiento")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimiento")
    private Long idMovimiento;

    @NotNull(message = "El id de cartera origen es obligatorio")
    @Positive(message = "El id de cartera origen debe ser positivo")
    @Column(name = "id_cartera_origen")
    private Long idCarteraOrigen;

    @NotNull(message = "El id de cartera destino es obligatorio")
    @Positive(message = "El id de cartera destino debe ser positivo")
    @Column(name = "id_cartera_destino")
    private Long idCarteraDestino;

    @NotNull(message = "El id de activo origen es obligatorio")
    @Positive(message = "El id de activo origen debe ser positivo")
    @Column(name = "id_activo_origen")
    private Long idActivoOrigen;

    @NotNull(message = "El id de activo destino es obligatorio")
    @Positive(message = "El id de activo destino debe ser positivo")
    @Column(name = "id_activo_destino")
    private Long idActivoDestino;

    @NotNull(message = "La cantidad origen es obligatoria")
    @Positive(message = "La cantidad origen debe ser mayor a 0")
    @Column(name = "cantidad_origen")
    private BigDecimal cantidadOrigen;

    @NotNull(message = "La cantidad destino es obligatoria")
    @Positive(message = "La cantidad destino debe ser mayor a 0")
    @Column(name = "cantidad_destino")
    private BigDecimal cantidadDestino;

    @Size(max = 100, message = "El nombre de la cartera origen no puede exceder los 100 caracteres")
    @Column(name = "nombre_cartera_origen", length = 255)
    private String nombreCarteraOrigen;

    @Size(max = 100, message = "El nombre de la cartera destino no puede exceder los 100 caracteres")
    @Column(name = "nombre_cartera_destino", length = 255)
    private String nombreCarteraDestino;

    @Size(max = 100, message = "El nombre del activo origen no puede exceder los 100 caracteres")
    @Column(name = "nombre_activo_origen", length = 255)
    private String nombreActivoOrigen;

    @Size(max = 100, message = "El nombre del activo destino no puede exceder los 100 caracteres")
    @Column(name = "nombre_activo_destino", length = 255)
    private String nombreActivoDestino;

    @Column(name = "fecha")
    private LocalDateTime fechaMovimiento;
}
