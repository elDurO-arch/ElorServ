package com.ElorServ.ElorServ.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "tipos")
public class Tipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name; // 'profesor', 'alumno', etc.
}