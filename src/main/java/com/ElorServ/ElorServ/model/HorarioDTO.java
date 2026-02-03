package com.ElorServ.ElorServ.model;

import com.ElorServ.ElorServ.model.Horario;
import lombok.Data; 

@Data
public class HorarioDTO {

    private int id;
    private String dia;
    private int hora;       
    private String modulo;  
    private String aula;
    private String observaciones;

    public HorarioDTO() {}

    // Constructor que convierte la Entidad del Server al DTO plano
    public HorarioDTO(Horario h) {
        this.id = h.getId();
        this.dia = h.getDia();
        this.hora = h.getHora();
        this.aula = h.getAula();
        this.observaciones = h.getObservaciones();
        
        // Aquí convertimos el objeto Modulo a texto simple
        if (h.getModulo() != null) {
            this.modulo = h.getModulo().getNombre();
        } else {
            this.modulo = "Libre / Guardia";
        }
    }
}