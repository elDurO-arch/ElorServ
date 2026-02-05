package com.ElorServ.ElorServ.controller;

import com.ElorServ.ElorServ.model.User;
import com.ElorServ.ElorServ.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ProfesorController {

    @Autowired
    private UserRepository userRepository;

    /**
     * Endpoint para obtener todos los profesores
     * GET http://localhost:8080/api/profesores
     */
    @GetMapping("/profesores")
    public List<Map<String, Object>> getProfesores() {
        List<User> usuarios = userRepository.findAll();

        List<User> profesores = usuarios.stream()
                .filter(u -> u.getTipo() != null && "PROFESOR".equalsIgnoreCase(u.getTipo().getName()))
                .collect(Collectors.toList());

        return profesores.stream().map(p -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", p.getId());
            map.put("nombre", p.getNombre());
            map.put("apellido", p.getApellidos());
            map.put("mail", p.getEmail());
            map.put("tipo", p.getTipo());
            map.put("url", p.getArgazkia_url());
           
            return map;
        }).collect(Collectors.toList());
    }
}