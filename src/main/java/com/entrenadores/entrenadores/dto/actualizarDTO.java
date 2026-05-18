package com.entrenadores.entrenadores.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class actualizarDTO {
    @NotBlank(message = "El entrenador debe tener un nombre")
    private String nombreEntrenador;

    @NotBlank(message = "El entrenador debe tener una especialidad")
    private String especialidad;
}
