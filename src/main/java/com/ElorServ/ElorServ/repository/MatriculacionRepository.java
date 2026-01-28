package com.ElorServ.ElorServ.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ElorServ.ElorServ.model.Matriculacion;

public interface MatriculacionRepository extends JpaRepository<Matriculacion, Integer> {

	// Buscar todas las matriculaciones de un alumno concreto
	List<Matriculacion> findByAlumnoId(int alumnoId);
	// Buscar todas los alumnos de un ciclo concreto
	List<Matriculacion> findByCicloId(int cicloId);
	
	@Query("SELECT DISTINCT m FROM Matriculacion m WHERE m.ciclo.id IN " +
	           "(SELECT h.modulo.ciclo.id FROM Horario h WHERE h.profesor.id = :profesorId)")
	List<Matriculacion> findMatriculasByProfesorId(@Param("profesorId") int profesorId);
}
