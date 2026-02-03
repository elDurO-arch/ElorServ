package com.ElorServ.ElorServ.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;

public class ReunionDTO implements Serializable {
    
    private int id;
    private String titulo;
    private String asunto;
    private String aula;
    private String estado;
    private String fecha; 
    private String nombreProfesor;
    private String nombreAlumno;

    public ReunionDTO(Reunion r) {
        this.id = r.getId();
        
        // --- LIMPIEZA DE NULOS ---
        this.titulo = (r.getTitulo() != null) ? r.getTitulo() : "Sin Título";
        this.asunto = (r.getAsunto() != null) ? r.getAsunto() : "";
        this.aula   = (r.getAula() != null)   ? r.getAula()   : "";
        this.estado = (r.getEstado() != null) ? r.getEstado() : "Pendiente";
        
        // Formateo seguro de fecha
        if (r.getFecha() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            this.fecha = sdf.format(r.getFecha());
        } else {
            this.fecha = "---";
        }
        
        
        if (r.getAlumno() != null) {
           
            String nombre = r.getAlumno().getNombre();
            String apellido = r.getAlumno().getApellidos();
            String username = r.getAlumno().getUsername();

            String completo = ((nombre != null ? nombre : "") + " " + (apellido != null ? apellido : "")).trim();
            
            if (completo.isEmpty()) {
                this.nombreAlumno = (username != null) ? username : "Sin Nombre";
            } else {
                this.nombreAlumno = completo;
            }
        } else {
            this.nombreAlumno = "Sin Alumno";
        }
    }
}