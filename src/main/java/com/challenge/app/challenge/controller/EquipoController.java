package com.challenge.app.challenge.controller;

import com.challenge.app.challenge.dto.EquipoDto;
import com.challenge.app.challenge.perseistence.entity.Equipo;
import com.challenge.app.challenge.perseistence.repository.EquipoRepository;
import com.challenge.app.challenge.service.EquipoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@RestController
@RequestMapping("/equipos")
@Tag(name = "Equipos", description = "Endpoints para la gestión de equipos")
public class EquipoController {

    @Autowired
    private EquipoRepository equipoRepository;

    @Autowired
    private EquipoService equipoService;

    @Operation(
            summary = "Obtener todos los equipos",
            description = "Obtiene una lista paginada de todos los equipos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Equipo.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "No se encontraron Equipos.", content = {@Content(schema = @Schema())})
    })
    @GetMapping
    public ResponseEntity<Page<Equipo>> findAll(Pageable pageable) {
        Page<Equipo> equipos = equipoRepository.findAll(pageable);
        if (equipos.hasContent()) {
            return ResponseEntity.ok(equipos);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Obtener un equipo",
            description = "Obtener un equipos por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Equipo.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Equipo no encontrado.", content = {@Content(schema = @Schema())})
    })
    @GetMapping("/{id}")
    public ResponseEntity<Equipo> findOneById(@PathVariable Long id) {
        Equipo equipo = equipoService.findOneById(id);
        return ResponseEntity.ok(equipo);
    }

    @Operation(
            summary = "Obtener un equipo",
            description = "Obtener un equipos por Nombre")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Equipo.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Equipo no encontrado.", content = {@Content(schema = @Schema())})
    })
    @GetMapping("/buscar")
    public ResponseEntity<Page<Equipo>> findAllByName(@RequestParam String nombre, Pageable pageable) {
        Page<Equipo> equipos = equipoRepository.findByNombreContaining(nombre, pageable);
        if (equipos.hasContent()) {
            return ResponseEntity.ok(equipos);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Crear un equipo",
            description = "Crear un equipo nuevo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Equipo.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "La solicitud es inválida.", content = {@Content(schema = @Schema())})
    })
    @PostMapping
    public ResponseEntity<Object> createOne(@RequestBody @Valid EquipoDto dto) {
        Equipo equipo = equipoService.createOne(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(equipo);
    }

    @Operation(
            summary = "Actualizar un equipo",
            description = "Actualizar un equipo existente mediante el ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Equipo.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "La solicitud es inválida.", content = {@Content(schema = @Schema())})
    })
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateOneById(@PathVariable Long id, @RequestBody @Valid EquipoDto dto) {
        Equipo equipo = equipoService.updateOneById(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(equipo);
    }

    @Operation(
            summary = "Eliminar un equipo",
            description = "Eliminar un equipo existente mediante el ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Equipo.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Equipo no encontrado.", content = {@Content(schema = @Schema())})
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOne(@PathVariable Long id) {
        equipoService.deleteOneById(id);
        return ResponseEntity.noContent().build();
    }

}
