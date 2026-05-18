package com.entrenadores.entrenadores.controller;

import com.entrenadores.entrenadores.dto.EntrenadorRequestDTO;
import com.entrenadores.entrenadores.dto.EntrenadorResponseDTO;
import com.entrenadores.entrenadores.dto.actualizarDTO;
import com.entrenadores.entrenadores.model.Entrenador;
import com.entrenadores.entrenadores.service.EntrenadorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("gym/entrenadores")
@RequiredArgsConstructor
public class EntrenadorController {
    private final EntrenadorService entrenadorService;

    @GetMapping("/listarentrenadores")
    public ResponseEntity<List<Entrenador>> obtenerProductos(){
        return ResponseEntity.ok(entrenadorService.obtenerEntrenadores());
    }


    // Buscar entrenador por id
    @GetMapping("/busqueda/id/{idEntrenador}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long idEntrenador) {
        EntrenadorResponseDTO dto = entrenadorService.buscarporId(idEntrenador);
        return ResponseEntity.ok(dto);
    }

    // Buscar entrenador por nombre
    @GetMapping("/busqueda/run/{RUN}")
    public ResponseEntity<EntrenadorResponseDTO> buscarPorRun(@PathVariable String RUN){
        EntrenadorRequestDTO dto = new EntrenadorRequestDTO();
        dto.setRun(RUN);

        EntrenadorResponseDTO respuesta = entrenadorService.obtenerporRUN(dto);
        return ResponseEntity.ok(respuesta);
    }

    // Borrar entrenador
    @DeleteMapping("/borrar/{idEntrenador}")
    public ResponseEntity<?> borrarUsuario(@PathVariable Long idEntrenador){
        if(entrenadorService.buscarId(idEntrenador).isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("Mensaje","No se encontró ningun ID asociado a algún producto."));
        }

        entrenadorService.eliminarEntrenador(idEntrenador);

        Map<String,String> respuesta = new HashMap<>();
        respuesta.put("Mensaje","Entrenador eliminado correctamente");
        respuesta.put("Id del entrenador: ",idEntrenador.toString());

        return ResponseEntity.ok(respuesta);
    }

    // Actualizar entrenador
    @PutMapping("/actualizar/{idEntrenador}")
    public ResponseEntity<?> actualizarProducto(@PathVariable Long idEntrenador, @Valid @RequestBody actualizarDTO dto){
        if(entrenadorService.buscarId(idEntrenador).isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("Mensaje","No se encontró ningun ID asociado a algún entrenador."));
        }

        entrenadorService.modificarEntrenador(idEntrenador, dto);

        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("Mensaje ","Entrenador actualizado con éxito");
        respuesta.put("IdEntrenador:",idEntrenador.toString());
        respuesta.put("Entrenador ",dto.getNombreEntrenador());

        return ResponseEntity.ok(respuesta);

    }

    // Agregar entrenador
    @PostMapping("/registrarentrenador")
    public ResponseEntity<EntrenadorResponseDTO> registrarUsuario(@Valid @RequestBody EntrenadorRequestDTO dto){
        EntrenadorResponseDTO nuevo = entrenadorService.agregarEntrenador(dto);
        return ResponseEntity.status(201).body(nuevo);
    }
}
