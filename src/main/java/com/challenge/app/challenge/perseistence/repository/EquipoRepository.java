package com.challenge.app.challenge.perseistence.repository;

import com.challenge.app.challenge.perseistence.entity.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EquipoRepository extends JpaRepository<Equipo, Long> {

    Page<Equipo> findByNombreContaining(String nombre, Pageable pageable);

}
