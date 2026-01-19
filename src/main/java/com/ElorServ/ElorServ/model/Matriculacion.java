package com.ElorServ.ElorServ.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "matriculaciones")
public class Matriculacion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	//Alumno matriculado
	@ManyToOne
	@JoinColumn(name = "alumno_id")
	private User alumno;
	
	//Ciclo en la que esta matriculado
	@ManyToOne
	@JoinColumn(name = "ciclo_id")
	private Ciclo ciclo;
	
	private String curso; 
}
