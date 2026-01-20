package com.ElorServ.ElorServ.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ElorServ.ElorServ.model.Ciclo;
import com.ElorServ.ElorServ.model.Horario;
import com.ElorServ.ElorServ.model.Matriculacion;
import com.ElorServ.ElorServ.repository.HorarioRepository;
import com.ElorServ.ElorServ.repository.MatriculacionRepository;

@RestController
@RequestMapping("/api/horarios")
public class HorarioController {

	@Autowired
	private HorarioRepository horarioRepository;
	
	@Autowired
	private MatriculacionRepository matriculacionRepository;
	
	// GET http://localhost:8080/api/horarios/profesor/5
    @GetMapping("/profesor/{id}")
    public List<Horario> getHorarioProfesor(@PathVariable int id) {
        return horarioRepository.findByProfesorId(id);
    }
    
 // GET http://localhost:8080/api/horarios/alumno/10
    @GetMapping("/alumno/{id}")
    public List<Horario> getHorarioAlumno(@PathVariable Integer id) {
        //Buscamos la matrícula del alumno para saber su ciclo
        List<Matriculacion> matriculas = matriculacionRepository.findByAlumnoId(id);
        
        if (matriculas.isEmpty()) {
            return new ArrayList<>(); // Si no está matriculado, devolvemos lista vacía
        }
        
        // Asumimos que el alumno está en un solo ciclo (cogemos el primero)
        Ciclo cicloDelAlumno = matriculas.get(0).getCiclo();
        
        //Buscamos el horario de ese ciclo completo
        return horarioRepository.findByModuloCicloId(cicloDelAlumno.getId());
    }
}
