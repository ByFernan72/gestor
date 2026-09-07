package com.gestorBackend.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name ="cartera")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cartera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cartera")
    private Long idCartera;

    @NotBlank(message = "El nombre de la cartera no puede estar vacío")
    @Size(min = 1, max = 100, message = "El nombre de la cartera debe tener entre 1 y 100 caracteres")
    @Column(name = "nombre", length = 255)
    private String nombreCartera;

    @Size(max = 500, message = "La descripción no puede exceder los 500 caracteres")
    @Column(name = "descripcion", length = 500, nullable = true)
    private String descripcion;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
