package com.challenge.app.challenge.service.impl;

import com.challenge.app.challenge.dto.EquipoDto;
import com.challenge.app.challenge.exception.TeamNotFoundException;
import com.challenge.app.challenge.exception.TeamNotSavedException;
import com.challenge.app.challenge.perseistence.entity.Equipo;
import com.challenge.app.challenge.perseistence.repository.EquipoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EquipoServiceImplTest {

    @Mock
    private EquipoRepository equipoRepository;

    @InjectMocks
    private EquipoServiceImpl equipoService;

    @Test
    void shouldCreateANewEquipo_createOne() {
        EquipoDto dto = new EquipoDto();
        dto.setNombre("Test");
        dto.setLiga("Test");
        dto.setPais("Test");

        Equipo equipoGuardado = new Equipo();
        equipoGuardado.setNombre("Test");
        equipoGuardado.setLiga("Test");
        equipoGuardado.setPais("Test");

        when(equipoRepository.save(any(Equipo.class))).thenAnswer(invocation -> {
            Equipo equipo = invocation.getArgument(0);
            equipo.setId(1L);
            return equipo;
        });

        Equipo result = equipoService.createOne(dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test", result.getNombre());

        verify(equipoRepository, times(1)).save(any(Equipo.class));
    }

    @Test
    void shouldThrowTeamNotSavedException_createOne() {
        EquipoDto dto = new EquipoDto();
        dto.setNombre("Test");
        dto.setLiga("Test");
        dto.setPais("Test");

        when(equipoRepository.save(any(Equipo.class))).thenThrow(new RuntimeException("La operación es inválida"));
        assertThrows(TeamNotSavedException.class, () -> equipoService.createOne(dto));
    }

    @Test
    void shouldUpdateAEquipo_updateOneExistentEquipo() {
        Long id = 1L;

        EquipoDto dto = new EquipoDto();
        dto.setNombre("Nuevo Nombre");
        dto.setLiga("Nueva Liga");
        dto.setPais("Nuevo País");

        Equipo equipoFromBD = new Equipo();
        equipoFromBD.setId(id);
        equipoFromBD.setNombre("Viejo Nombre");
        equipoFromBD.setLiga("Vieja Liga");
        equipoFromBD.setPais("Viejo País");

        when(equipoRepository.findById(id)).thenReturn(Optional.of(equipoFromBD));
        when(equipoRepository.save(any(Equipo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Equipo result = equipoService.updateOneById(id, dto);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Nuevo Nombre", result.getNombre());
        assertEquals("Nueva Liga", result.getLiga());
        assertEquals("Nuevo País", result.getPais());

        verify(equipoRepository, times(1)).findById(id);
        verify(equipoRepository, times(1)).save(any(Equipo.class));
    }

    @Test
    void shouldThrowTeamNotFoundException_updateOneNonExistentEquipo() {
        Long id = 1L;
        EquipoDto dto = new EquipoDto();

        when(equipoRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(TeamNotFoundException.class, () -> equipoService.updateOneById(id, dto));
    }


    @Test
    void shouldThrowTeamNotFoundException_deleteOneNonExistentEquipo() {
        Long id = 1L;
        when(equipoRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(TeamNotFoundException.class, () -> equipoService.deleteOneById(id));
        verify(equipoRepository, never()).deleteById(id);
    }

}