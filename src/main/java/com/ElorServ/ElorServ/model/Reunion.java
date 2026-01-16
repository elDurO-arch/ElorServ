package com.ElorServ.ElorServ.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Data
@Table(name = "reuniones")
public class Reunion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reunion") // IMPORTANTE: En tu SQL la PK se llama id_reunion
    private Long id;

    private String estado; // 'pendiente', 'aceptada', etc.
    private String titulo;
    private String asunto;
    private String aula;
    private Date fecha;

    // Relación con el Profesor
    @ManyToOne
    @JoinColumn(name = "profesor_id")
    private User profesor;

    // Relación con el Alumno
    @ManyToOne
    @JoinColumn(name = "alumno_id")
    private User alumno;
}