package com.ElorServ.ElorServ.repository;

import com.ElorServ.ElorServ.model.Reunion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReunionRepository extends JpaRepository<Reunion, Integer> {
    // Buscar todas las reuniones de un profesor concreto
    List<Reunion> findByProfesorId(int profesorId);
    List<Reunion> findByAlumnoId(int alumnoId);
}


// en --> List<Reunion> findByProfesorId(int profesorId); Spring Data JPA
//genera la consulta automáticamente, traduciendo el nombre del método en una consulta SQL
//si la consulta fuera muy compleja, se podría hacer de forma manual con @Query 

//asi seria la consulta manual HQL si hiciera falta
//@Query("SELECT r FROM Reunion r WHERE r.profesor.id = :idProfesor")
//List<Reunion> buscarReunionesDeProfeManual(@Param("idProfesor") int id);