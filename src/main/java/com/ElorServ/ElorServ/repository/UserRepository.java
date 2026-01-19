package com.ElorServ.ElorServ.repository;

import com.ElorServ.ElorServ.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    // Método mágico para el Login: busca por usuario Y contraseña
    Optional<User> findByUsernameAndPassword(String username, String password);
}