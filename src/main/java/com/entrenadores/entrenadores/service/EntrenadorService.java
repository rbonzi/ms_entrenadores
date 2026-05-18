package com.entrenadores.entrenadores.service;

import com.entrenadores.entrenadores.dto.EntrenadorRequestDTO;
import com.entrenadores.entrenadores.dto.EntrenadorResponseDTO;
import com.entrenadores.entrenadores.dto.actualizarDTO;
import com.entrenadores.entrenadores.model.Entrenador;
import com.entrenadores.entrenadores.repository.EntrenadorRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EntrenadorService {
    private final EntrenadorRepository entrenadorRepository;

    private EntrenadorResponseDTO mapToDto(Entrenador entrenador) {
        return new EntrenadorResponseDTO(
                entrenador.getIdEntrenador(),
                entrenador.getNombreEntrenador(),
                entrenador.getRun(),
                entrenador.getEspecialidad()
        );
    }

    // Listar todos los entrenadores
    public List<Entrenador> obtenerEntrenadores() {
        return entrenadorRepository.findAll();
    }

    // Buscar entrenador por id
    public EntrenadorResponseDTO buscarporId(Long idEntrenador){
        Entrenador entrenadorBuscado = entrenadorRepository.findById(idEntrenador)
                .orElseThrow(() -> new RuntimeException("No existe un entrenador asociado a ese id"));

        EntrenadorResponseDTO respuesta =  new EntrenadorResponseDTO();
        respuesta.setIdEntrenador(entrenadorBuscado.getIdEntrenador());
        respuesta.setNombreEntrenador(entrenadorBuscado.getNombreEntrenador());
        respuesta.setRun(entrenadorBuscado.getRun());
        respuesta.setEspecialidad(entrenadorBuscado.getEspecialidad());

        return respuesta;
    }

    // Buscar entrenador por run
    public EntrenadorResponseDTO obtenerporRUN(EntrenadorRequestDTO dto) {
        Entrenador entrenadorBuscado = entrenadorRepository.findByRun(dto.getRun())
                .orElseThrow(() -> new RuntimeException("No hay ningun entrenador asociado al run: "+dto.getRun()));

        EntrenadorResponseDTO respuesta = new EntrenadorResponseDTO();
        respuesta.setIdEntrenador(entrenadorBuscado.getIdEntrenador());
        respuesta.setNombreEntrenador(entrenadorBuscado.getNombreEntrenador());
        respuesta.setRun(entrenadorBuscado.getRun());
        respuesta.setEspecialidad(entrenadorBuscado.getEspecialidad());

        return respuesta;
    }

    // Buscar para borrar
    public Optional<EntrenadorResponseDTO> buscarId(Long idEntrenador){
        return entrenadorRepository.findById(idEntrenador).map(this::mapToDto);
    }

    @Transactional
    public void eliminarEntrenador(Long idEntrenador){
        entrenadorRepository.deleteById(idEntrenador);
    }

    // Modificar productos
    public Optional<EntrenadorResponseDTO> modificarEntrenador(Long idEntrenador, @Valid actualizarDTO dto){
        return entrenadorRepository.findById(idEntrenador).map(existe ->{
            existe.setNombreEntrenador(dto.getNombreEntrenador());
            existe.setEspecialidad(dto.getEspecialidad());

            return mapToDto(entrenadorRepository.save(existe));
        });
    }


    // Añadir productos
    public EntrenadorResponseDTO agregarEntrenador(EntrenadorRequestDTO dto){
        if(entrenadorRepository.existsByNombreEntrenadorIgnoreCase(dto.getNombreEntrenador())){
            throw new RuntimeException("ERROR: Ya existe el entrenador '" + dto.getNombreEntrenador()+"'");
        }

        Entrenador entrenadorNuevo = new Entrenador();
        entrenadorNuevo.setNombreEntrenador(dto.getNombreEntrenador());
        entrenadorNuevo.setEspecialidad(dto.getEspecialidad());
        entrenadorNuevo.setRun(dto.getRun());


        Entrenador entrenadorGuardado = entrenadorRepository.save(entrenadorNuevo);

        EntrenadorResponseDTO respuesta = new EntrenadorResponseDTO();
        respuesta.setIdEntrenador(entrenadorGuardado.getIdEntrenador());
        respuesta.setNombreEntrenador(entrenadorGuardado.getNombreEntrenador());
        respuesta.setRun(entrenadorGuardado.getRun());
        respuesta.setEspecialidad(entrenadorGuardado.getEspecialidad());

        return respuesta;
    }


}
