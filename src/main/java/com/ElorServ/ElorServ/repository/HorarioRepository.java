package com.ElorServ.ElorServ.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ElorServ.ElorServ.model.Horario;

public interface HorarioRepository extends JpaRepository<Horario, Integer> {

	//busca los  horarios por el ID del profe
	List<Horario> findByProfesorId(int profesorId);
	
	List<Horario> findByModuloCicloId(int cicloId);
}
