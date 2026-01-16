package com.ElorServ.ElorServ;

import com.ElorServ.ElorServ.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class PruebaConexion implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("------------------------------------------------");
        System.out.println("INTENTANDO CONECTAR A LA BASE DE DATOS...");
        
        try {
            // Intentamos contar cuántos usuarios hay en la tabla 'users'
            long cantidadUsuarios = userRepository.count();
            
            System.out.println("¡ÉXITO! CONEXIÓN CORRECTA.");
            System.out.println("Usuarios encontrados en la BD: " + cantidadUsuarios);
            
        } catch (Exception e) {
            System.out.println("¡ERROR GRAVE! NO SE PUDO CONECTAR.");
            e.printStackTrace();
        }
        System.out.println("------------------------------------------------");
    }
}