package com.ElorServ.ElorServ.controller;

import com.ElorServ.ElorServ.model.Reunion;
import com.ElorServ.ElorServ.repository.ReunionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reuniones")
public class ReunionController {

    @Autowired
    private ReunionRepository reunionRepository;

    //estp es para listar por profesor
    // GET http://IP:8080/api/reuniones/profesor/3  (Saca las reuniones del profe con ID 3)
    @GetMapping("/profesor/{id}")
    public List<Reunion> getReunionesPorProfesor(@PathVariable int id) {
        return reunionRepository.findByProfesorId(id);
    }
    
    //esto es para listar por alumno
    @GetMapping("/alumno/{id}")
    public List<Reunion> getReunionesPorAlumno(@PathVariable int id) {
		return reunionRepository.findByAlumnoId(id);
	}
    
    
    //crear reunion
    @PostMapping("/crear")
    public Reunion crearReunion(@RequestBody Reunion reunion) {
    	
    	if(reunion.getEstado() == null) {
    		reunion.setEstado("pendiente"); // Estado inicial
    	}
		
		return reunionRepository.save(reunion);
	}
    
    
    //Aceptar Reunion
    // PUT http://IP:8080/api/reuniones/aceptar/1 (Acepta la reunión con ID 1)
    @PutMapping("/actualizar")
    public Reunion actualizarReunion(@RequestBody Reunion reunion) {
       
        return reunionRepository.save(reunion);
    }
}