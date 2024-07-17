package com.challenge.app.challenge.service.impl;

import com.challenge.app.challenge.dto.EquipoDto;
import com.challenge.app.challenge.exception.TeamNotFoundException;
import com.challenge.app.challenge.exception.TeamNotSavedException;
import com.challenge.app.challenge.perseistence.entity.Equipo;
import com.challenge.app.challenge.perseistence.repository.EquipoRepository;
import com.challenge.app.challenge.service.EquipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EquipoServiceImpl implements EquipoService {

    @Autowired
    private EquipoRepository equipoRepository;

    @Override
    public Equipo createOne(EquipoDto dto) {
        Equipo equipo = new Equipo();
        return teamMapper(dto, equipo);
    }

    @Override
    public Equipo findOneById(Long id) {
        Equipo equipo = equipoRepository.findById(id).orElseThrow(() -> new TeamNotFoundException("Equipo no encontrado"));
        return equipo;
    }

    @Override
    public Equipo updateOneById(Long id, EquipoDto dto) {
        Equipo equipo = findOneById(id);
        return teamMapper(dto, equipo);
    }

    private Equipo teamMapper(EquipoDto dto, Equipo equipo) {
        equipo.setNombre(dto.getNombre());
        equipo.setLiga(dto.getLiga());
        equipo.setPais(dto.getPais());
        try {
            equipoRepository.save(equipo);
        } catch (Exception e) {
            throw new TeamNotSavedException("La solicitud es inválida");
        }
        return equipo;
    }

    @Override
    public void deleteOneById(Long id) {
        Equipo equipo = findOneById(id);
        equipoRepository.delete(equipo);
    }


}
