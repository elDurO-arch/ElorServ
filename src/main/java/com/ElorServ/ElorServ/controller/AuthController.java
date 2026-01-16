package com.ElorServ.ElorServ.controller;

import com.ElorServ.ElorServ.model.User;
import com.ElorServ.ElorServ.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    //Peticion POST a http://IP:8080/api/login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginData) {
        // Buscamos si existe alguien con ese user y pass
        return userRepository.findByUsernameAndPassword(loginData.getUsername(), loginData.getPassword())
                .map(usuarioEncontrado -> {
                    // Verificamos que sea profesor (tipo 3 en tu SQL) si el reto lo pide
                    if(usuarioEncontrado.getTipo().getId() == 3 || usuarioEncontrado.getTipo().getId() == 2) {
                         return ResponseEntity.ok(usuarioEncontrado);
                    } else {
                         return ResponseEntity.status(403).body("Solo acceso a profesores/admin");
                    }
                })
                .orElse(ResponseEntity.status(401).body("Credenciales incorrectas"));
    }
}
