package com.ElorServ.ElorServ.repository;

import com.ElorServ.ElorServ.model.Reunion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReunionRepository extends JpaRepository<Reunion, Integer> {
    // Buscar todas las reuniones de un profesor concreto
    List<Reunion> findByProfesorId(Long profesorId);
}