package com.ElorServ.ElorServ.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ElorServ.ElorServ.model.Tipo;

public interface TipoRepository extends JpaRepository<Tipo, Integer> {
	//buscar tipo por nombre
	Optional<Tipo> findByName(String name);

}
