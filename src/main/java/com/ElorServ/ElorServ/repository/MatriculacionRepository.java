package com.ElorServ.ElorServ.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ElorServ.ElorServ.model.Matriculacion;

public interface MatriculacionRepository extends JpaRepository<Matriculacion, Integer> {

	// Buscar todas las matriculaciones de un alumno concreto
	List<Matriculacion> findByAlumnoId(int alumnoId);
	// Buscar todas los alumnos de un ciclo concreto
	List<Matriculacion> findByCicloId(int cicloId);
}
