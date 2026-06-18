package com.entrenadores.entrenadores.controller;

import com.entrenadores.entrenadores.dto.EntrenadorRequestDTO;
import com.entrenadores.entrenadores.dto.EntrenadorResponseDTO;
import com.entrenadores.entrenadores.dto.actualizarDTO;
import com.entrenadores.entrenadores.model.Entrenador;
import com.entrenadores.entrenadores.service.EntrenadorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "ENTRENADORES", description = "GESTIÓN DE ENTRENADORES DEL GYM")
public class EntrenadorController {
    private final EntrenadorService entrenadorService;

    @GetMapping("/listarentrenadores")
    @Operation(summary = "Listar entrenadores", description = "Listar a todos los entrenadores dentro del GYM")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entrenadores listados correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<Entrenador>> obtenerProductos(){
        return ResponseEntity.ok(entrenadorService.obtenerEntrenadores());
    }


    // Buscar entrenador por id
    @GetMapping("/busqueda/id/{idEntrenador}")
    @Operation(summary = "Buscar entrenadores", description = "Buscar a los entrenadores por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entrenador encontrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos"),
            @ApiResponse(responseCode = "404", description = "Entrenador no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> obtenerPorId(@PathVariable Long idEntrenador) {
        EntrenadorResponseDTO dto = entrenadorService.buscarporId(idEntrenador);
        return ResponseEntity.ok(dto);
    }

    // Buscar entrenador por nombre
    @GetMapping("/busqueda/run/{RUN}")
    @Operation(summary = "Buscar entrenadores por RUN", description = "Buscar a los entrenadores por su RUN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entrenador encontrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos"),
            @ApiResponse(responseCode = "404", description = "Entrenador no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<EntrenadorResponseDTO> buscarPorRun(@PathVariable String RUN){
        EntrenadorRequestDTO dto = new EntrenadorRequestDTO();
        dto.setRun(RUN);

        EntrenadorResponseDTO respuesta = entrenadorService.obtenerporRUN(dto);
        return ResponseEntity.ok(respuesta);
    }

    // Borrar entrenador
    @DeleteMapping("/borrar/{idEntrenador}")
    @Operation(summary = "Eliminar entrenadores", description = "Eliminar a los entrenadores por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entrenador eliminado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos"),
            @ApiResponse(responseCode = "404", description = "Entrenador no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
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
    @Operation(summary = "Modificar entrenadores", description = "Modificar a los entrenadores por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entrenador modificado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos"),
            @ApiResponse(responseCode = "404", description = "Entrenador no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
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
    @Operation(summary = "Añadir entrenadores", description = "Añadir entrenadores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Entrenador añadido correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos"),
            @ApiResponse(responseCode = "404", description = "Entrenador no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<EntrenadorResponseDTO> registrarUsuario(@Valid @RequestBody EntrenadorRequestDTO dto){
        EntrenadorResponseDTO nuevo = entrenadorService.agregarEntrenador(dto);
        return ResponseEntity.status(201).body(nuevo);
    }
}
