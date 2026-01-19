package com.ElorServ.ElorServ.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "modulos")
public class Modulo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String nombre;
	private String nombre_eus;
	private int horas;
	
	@ManyToOne
	@JoinColumn(name = "ciclo_id")
	private Ciclo ciclo;
	
	private int curso;
	
}
