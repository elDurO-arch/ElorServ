package com.ElorServ.ElorServ.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ElorServ.ElorServ.model.Matriculacion;
import com.ElorServ.ElorServ.repository.MatriculacionRepository;

@RestController
@RequestMapping("/api/matriculaciones")
public class MatriculacionController {
	
	@Autowired
	private MatriculacionRepository matriculacionRepository;
	
	//ver ciclos en los que esta matriculado un alumno
	// GET http://localhost:8080/api/matriculaciones/alumno/5
    @GetMapping("/alumno/{id}")
    public List<Matriculacion> getMatriculasAlumno(@PathVariable int id) {
        return matriculacionRepository.findByAlumnoId(id);
    }

    //  ver todos los alumnos de un ciclo (Para que el profe pase lista)
    // GET http://localhost:8080/api/matriculaciones/ciclo/1
    @GetMapping("/ciclo/{id}")
    public List<Matriculacion> getAlumnosPorCiclo(@PathVariable int id) {
        return matriculacionRepository.findByCicloId(id);
    }

}
