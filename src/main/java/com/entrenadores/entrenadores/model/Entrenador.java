package com.entrenadores.entrenadores.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Entrenador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEntrenador;

    @Column(nullable = false)
    private String nombreEntrenador;

    @Column(nullable = false,length = 9)
    private String run;

    @Column(nullable = false)
    private String especialidad;


}
