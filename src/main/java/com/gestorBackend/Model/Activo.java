package com.gestorBackend.Model;

import com.gestorBackend.Model.Enum.TipoActivo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name ="activo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Activo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_activo")
    private Long idActivo;

    @Column(name = "id_cartera")
    private Long idCartera;

    @NotBlank(message = "El nombre del activo no puede estar vacío")
    @Size(min = 1, max = 100, message = "El nombre del activo debe tener entre 1 y 100 caracteres")
    @Column(name = "nombre", length = 255)
    private String nombreActivo;

    @NotNull(message = "El tipo de activo es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", length = 50)
    private TipoActivo tipoActivo;

    @NotNull(message = "El balance del activo no puede ser nulo")
    @PositiveOrZero(message = "El balance del activo no puede ser negativo")
    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "fecha_obtencion_activo")
    private LocalDateTime fechaObtencionActivo;
}
