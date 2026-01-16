package com.ElorServ.ElorServ.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread {

	private Socket clientSocket;
	
	public ClientHandler(Socket socket) {
		this.clientSocket = socket;
	}
	
	@Override
	public void run() {
		try {
			
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			
			System.out.println("Profesor conectado desde: " + clientSocket.getInetAddress().getHostAddress());
			
			//aviso de conexion
			out.println("Kaixo, ElorServ Socket zerbitzaria prest.");
			
			String inputLine;
			
			//bucle de comunicacion
			while ((inputLine = in.readLine()) != null) {
				System.out.println("Jaso den mezua: " + inputLine);
				
				//temporal para terminal conexion
				if("AGUR".equalsIgnoreCase(inputLine.trim())) {
					out.println("Agur, konexioa itxi da.");
					break;
				}
				
				//el servidor le responde al cliente
				out.println("Zure mezua jaso da: " + inputLine);
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
}
