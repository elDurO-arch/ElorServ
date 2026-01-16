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

    // GET http://IP:8080/api/reuniones/profesor/3  (Saca las reuniones del profe con ID 3)
    @GetMapping("/profesor/{id}")
    public List<Reunion> getReunionesPorProfesor(@PathVariable Long id) {
        return reunionRepository.findByProfesorId(id);
    }
    
    // PUT http://IP:8080/api/reuniones/aceptar/1 (Acepta la reunión con ID 1)
    @PutMapping("/aceptar/{id}")
    public Reunion aceptarReunion(@PathVariable Long id) {
        Reunion r = reunionRepository.findById(id).orElseThrow();
        r.setEstado("aceptada");
        return reunionRepository.save(r);
    }
}