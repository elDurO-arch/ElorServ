package com.ElorServ.ElorServ.model; 

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data //esto crea los Getters y Setters automáticamente (Lombok)
@Table(name = "users") 
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String username;

    private String password; 
    private String nombre;
    private String apellidos;
    private String dni;
    
    private String direccion;
    private String telefono1;
    private String telefono2;
    private String argazkia_url;

    //Relación con Tipo
    @ManyToOne
    @JoinColumn(name = "tipo_id")
    private Tipo tipo; 
    

    //JsonIgnore --> para que al pedir el usuario no se descargue lista infinita
    @OneToMany(mappedBy = "profesor")
    @JsonIgnore
    private List<Reunion> reunionesComoProfesor;
    @OneToMany(mappedBy = "profesor")
    @JsonIgnore
    private List<Reunion> reunionesComoAlumno;
}