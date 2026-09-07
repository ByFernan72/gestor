package com.gestorBackend.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="perfil")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Perfil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_perfil")
    private Long idPerfil;

    @Column(name = "nombre_perfil")
    private String nombrePerfil;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "perfil_links", joinColumns = @JoinColumn(name = "id_perfil"))
    @Column(name = "url")
    private List<String> links = new ArrayList<>();

    @Column(name = "fecha_creacion_perfil")
    private LocalDateTime fechaCreacionPerfil;
}
