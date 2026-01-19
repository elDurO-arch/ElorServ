package com.ElorServ.ElorServ.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ElorServ.ElorServ.repository.UserRepository;

@Component
public class SocketServer implements CommandLineRunner{

	private static final int PORT = 9000;
	
	@Autowired
	private UserRepository userRepository; // Inyecta el repositorio para poder buscar ususarios
	
	@Override
	public void run(String... args)throws Exception{
		
		//lanzamos el servidor en un hilo separado para que no bloquee el arranque de Spring Boot
		
		new Thread(() -> {
			try (ServerSocket serverSocket = new java.net.ServerSocket(PORT)) {
				System.out.println("Socket zerbitzaria martxan portuan: " + PORT);
				
				while (true) {
					//se queda congelado el server esperando conexiones
					Socket clientSocket = serverSocket.accept();
					
					//cuando llega una conexion, se crea un nuevo hilo para manejarla
					new ClientHandler(clientSocket, userRepository).start();
				}
			} catch (IOException e) {
				System.out.println("Errorea socket zerbitzaria abiaraztean: " + e.getMessage() + ", Portuan: "+ PORT);
				e.printStackTrace();
			}
		}).start();
	}
	
}
