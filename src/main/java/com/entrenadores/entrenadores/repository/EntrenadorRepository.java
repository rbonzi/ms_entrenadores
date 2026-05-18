package com.entrenadores.entrenadores.repository;

import com.entrenadores.entrenadores.model.Entrenador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EntrenadorRepository extends JpaRepository<Entrenador, Long> {
    Optional<Entrenador> findByRun(String run);

    boolean existsByNombreEntrenadorIgnoreCase(String nombreEntrenador);

}
