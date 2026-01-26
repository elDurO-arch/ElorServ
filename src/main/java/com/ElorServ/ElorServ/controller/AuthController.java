package com.ElorServ.ElorServ.controller;

import com.ElorServ.ElorServ.model.User;
import com.ElorServ.ElorServ.repository.UserRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    //Peticion POST a http://IP:8080/api/login + body (json) con user y pass
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginData) {
        // Buscamos si existe alguien con ese user y pass
    	Optional<User> userOpt = userRepository.findByUsernameAndPassword(loginData.getUsername(), loginData.getPassword());
    	if (userOpt.isPresent()) {
            User usuarioEncontrado = userOpt.get();

            // --- l impieza de seguridad ---
            usuarioEncontrado.setPassword(null);
            usuarioEncontrado.setReunionesComoProfesor(null);
            
            
            if (usuarioEncontrado.getReunionesComoAlumno() != null) {
                usuarioEncontrado.setReunionesComoAlumno(null);
            }

            //devuelve un JSON de USUARIO (ResponseEntity<User>)
            return ResponseEntity.ok(usuarioEncontrado);

        } else {
            //devuelve un TEXTO de error (ResponseEntity<String>)
            return ResponseEntity.status(401).body("Credenciales incorrectas");
        }
    }
}
