package com.ElorServ.ElorServ.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "ciclos")
public class Ciclo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String nombre;
	
	
}
