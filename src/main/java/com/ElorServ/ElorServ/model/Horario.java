package com.ElorServ.ElorServ.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "horarios")
public class Horario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String dia;
	private int hora;
	
	//Relacion con el profe(User)
	@ManyToOne
	@JoinColumn(name = "profe_id")
	private User profesor;
	
	//Relacion con la Asignatura (modulo)
	@ManyToOne
	@JoinColumn(name = "modulo_id")
	private Modulo modulo;
	
	private String aula;
	private String observaciones;
	
}
