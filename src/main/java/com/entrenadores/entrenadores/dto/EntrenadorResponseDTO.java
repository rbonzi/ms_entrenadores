package com.entrenadores.entrenadores.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntrenadorResponseDTO {
    private Long idEntrenador;
    private String nombreEntrenador;
    private String run;
    private String especialidad;
}
