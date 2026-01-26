package com.ElorServ.ElorServ.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

import com.ElorServ.ElorServ.Security.RSAService;
import com.ElorServ.ElorServ.model.SocketData;
import com.ElorServ.ElorServ.model.User;
import com.ElorServ.ElorServ.repository.UserRepository;
import com.google.gson.Gson;

public class ClientHandler extends Thread {

	private Socket clientSocket;
	private UserRepository userRepository;
	private Gson gson = new Gson();
	private RSAService rsaService;
	
	public ClientHandler(Socket socket, UserRepository userRepository ,RSAService rsaService) {
		this.clientSocket = socket;
		this.userRepository = userRepository;
		this.rsaService = new RSAService();
	}
	
	@Override
	public void run() {
		try {
			
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			
			
			
			
			
			//AL CONECTARSE: se envia la clave pública al cliente
            String publicKey = rsaService.getPublicKeyAsString();
            SocketData keyMsg = new SocketData("PUBLIC_KEY", publicKey);
            out.println(gson.toJson(keyMsg));
			
			String inputLine;
			
			//bucle de comunicacion
			while ((inputLine = in.readLine()) != null) {
				System.out.println("Jaso den mezua: " + inputLine);
				
				try {
					SocketData request = gson.fromJson(inputLine, SocketData.class);
					
					//procesar la solicitud
					SocketData response = procesarSolicitud(request);
					
					//enviar la respuesta convertido a JSON
					String jsonResponse = gson.toJson(response);
					out.println(jsonResponse);
					
					
				}catch (Exception e) {
					e.printStackTrace();
					out.println(gson.toJson(new SocketData("ERROR", "no es ormato Json ")));
				}
			}
			
			//cerrar recursos
			in.close();
			out.close();
			clientSocket.close();
			
			System.out.println("Conexion cerrada: " + clientSocket.getInetAddress().getHostAddress());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Logica
	private SocketData procesarSolicitud(SocketData request) {
		String tipo = request.getType();
		
		switch(tipo) {
		case "LOGIN":
			return procesarLogin(request.getData());
		
		case "PING":
			return new SocketData("PONG", "el server te escucha ;)");
			
		default:
			return new SocketData("ERROR", "Tipo de solicitud desconocido: " + tipo);
		}	
	}
	
	//Procesar login
	private SocketData procesarLogin(Object data) {
		try {
			Map<String, String> credenciales = (Map<String, String>) data;
			String username = credenciales.get("username");
			String passwordCifrada = credenciales.get("password");
			
			//descifra la contraseña que viene encriptada desde el cliente
            String passwordPlana = rsaService.decrypt(passwordCifrada);
			
            System.out.println("Intento de login: " + username);
            // System.out.println("Pass descifrada: " + passwordPlana); // Solo para debug
            
			User usuarioEncontrado = userRepository.findByUsernameAndPassword(username, passwordPlana).orElse(null);
			
			if (usuarioEncontrado != null ) {
                // Login correcto, Devolvemos los datos del usuario (sin el password por seguridad)
                usuarioEncontrado.setPassword(null); 
                usuarioEncontrado.setReunionesComoProfesor(null); 
                
                return new SocketData("LOGIN_OK", usuarioEncontrado);
            } else {
                return new SocketData("LOGIN_FAIL", "Usuario o contraseña incorrectos");
            }
			
		}catch (Exception e) {
			return new SocketData("ERROR", "Datos del login mal formateados");
		}
	}
}
