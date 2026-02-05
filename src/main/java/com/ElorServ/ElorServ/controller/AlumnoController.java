package com.ElorServ.ElorServ.controller;

import com.ElorServ.ElorServ.model.User;
import com.google.gson.Gson;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AlumnoController {

    @PersistenceContext
    private EntityManager entityManager;

    private Gson gson = new Gson();

    @GetMapping("/alumnos")
    public String obtenerAlumnos() {

        String hql = "FROM User u WHERE u.tipo.id = 4";
        List<User> alumnos = entityManager.createQuery(hql, User.class)
                                         .getResultList();

        for (User alumno : alumnos) {
            alumno.setPassword(null);
            alumno.setReunionesComoProfesor(null);
            alumno.setReunionesComoAlumno(null);
        }

        return gson.toJson(alumnos);
    }
}
