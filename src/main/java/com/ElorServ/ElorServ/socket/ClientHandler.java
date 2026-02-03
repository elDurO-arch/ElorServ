package com.ElorServ.ElorServ.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.ElorServ.ElorServ.Security.RSAService;
import com.ElorServ.ElorServ.model.*;
import com.ElorServ.ElorServ.repository.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ClientHandler extends Thread {

	private Socket clientSocket;

	private UserRepository userRepository;
	private HorarioRepository horarioRepository;
	private ReunionRepository reunionRepository;
	private MatriculacionRepository matriculaRepository;

	private Gson gson = new Gson();
	private RSAService rsaService;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public ClientHandler(Socket socket, UserRepository userRepository, HorarioRepository horarioRepository,
			ReunionRepository reunionRepository, MatriculacionRepository matriculaRepository, RSAService rsaService) {
		this.clientSocket = socket;
		this.userRepository = userRepository;
		this.horarioRepository = horarioRepository;
		this.reunionRepository = reunionRepository;
		this.matriculaRepository = matriculaRepository;
		this.rsaService = rsaService;
	}

	@Override
	public void run() {
		try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

			// 1. Enviar clave pública RSA
			String publicKey = rsaService.getPublicKeyAsString();
			out.println(gson.toJson(new SocketData("PUBLIC_KEY", publicKey)));

			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				System.out.println("Mensaje recibido: " + inputLine);

				try {
					SocketData request = gson.fromJson(inputLine, SocketData.class);
					SocketData response = procesarSolicitud(request);
					out.println(gson.toJson(response));

				} catch (Exception e) {
					e.printStackTrace();
					out.println(gson.toJson(new SocketData("ERROR", "Formato JSON incorrecto")));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				clientSocket.close();
			} catch (Exception e) {
			}
			System.out.println("Conexión cerrada: " + clientSocket.getInetAddress().getHostAddress());
		}
	}

	private SocketData procesarSolicitud(SocketData request) {
		String tipo = request.getType();
		Long id = null;
		if (request.getData() != null && (request.getData() instanceof Number || request.getData() instanceof String)) {
			try {
				id = Long.valueOf(String.valueOf(request.getData()).replace(".0", ""));
			} catch (Exception e) {
			}
		}

		switch (tipo) {
		case "LOGIN":
			return procesarLogin(request.getData());
		case "GET_HORARIO":
			return obtenerHorario(id);
		case "GET_ALUMNOS":
		    return obtenerAlumnos(id); 
		case "GET_REUNIONES":
			return obtenerReuniones(id);
		case "GET_PROFESORES":
			return obtenerProfesores();
		case "CREAR_REUNION":
			return procesarCrearReunion(request.getData());
		case "CAMBIAR_ESTADO_REUNION":
			return procesarCambiarEstado(request.getData());
		case "PING":
			return new SocketData("PONG", "Servidor activo");
		default:
			return new SocketData("ERROR", "Solicitud desconocida: " + tipo);
		}
	}

	private SocketData procesarLogin(Object data) {
		try {
			Map<String, String> credenciales = (Map<String, String>) data;
			String username = credenciales.get("username");
			String passwordPlana = rsaService.decrypt(credenciales.get("password"));
			User usuario = userRepository.findByUsernameAndPassword(username, passwordPlana).orElse(null);

			if (usuario != null) {
				if (usuario.getTipo().getId() == 4) {
					return new SocketData("LOGIN_FAIL", "Ikasleek ezin dute aplikazioa erabili");
				}
				usuario.setPassword(null);
				usuario.setReunionesComoProfesor(null);
				usuario.setReunionesComoAlumno(null);
				return new SocketData("LOGIN_OK", usuario);
			} else {
				return new SocketData("LOGIN_FAIL", "Usuario o contraseña incorrectos");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new SocketData("ERROR", "Error en login");
		}
	}

	private SocketData obtenerHorario(Long profesorId) {
		if (profesorId == null)
			return new SocketData("ERROR", "Falta ID");
		try {
			List<HorarioDTO> listaDTOs = new ArrayList<>();
			for (Horario h : horarioRepository.findByProfesorId(profesorId.intValue())) {
				listaDTOs.add(new HorarioDTO(h));
			}
			return new SocketData("HORARIO_OK", listaDTOs);
		} catch (Exception e) {
			e.printStackTrace();
			return new SocketData("ERROR", "No se pudieron cargar los horarios");
		}
	}

	// 🔥 Método corregido: devuelve lista completa de alumnos
	private SocketData obtenerAlumnos(Long profesorId) {
	    if (profesorId == null) return new SocketData("ERROR", "Falta ID");

	    try {
	        List<Matriculacion> matriculas = matriculaRepository.findMatriculasByProfesorId(profesorId.intValue());

	        Map<Integer, UserDTO> alumnosUnicos = new HashMap<>();

	        System.out.println("--- DEBUG SERVIDOR: Matriculas encontradas para profesor " + profesorId + " ---");
	        for (Matriculacion m : matriculas) {
	            User u = m.getAlumno();
	            if (u != null) {
	                System.out.println("Matricula: " + m.getId() + " -> Alumno: " + u.getUsername() + " (ID: " + u.getId() + ")");
	                if (!alumnosUnicos.containsKey(u.getId())) {
	                    UserDTO dto = new UserDTO();
	                    dto.setId(u.getId());
	                    dto.setUsername(u.getUsername());
	                    dto.setNombre(u.getNombre());
	                    dto.setApellido(u.getApellidos());
	                    dto.setDni(u.getDni());
	                    dto.setEmail(u.getEmail());

	                    alumnosUnicos.put(u.getId(), dto);
	                } else {
	                    System.out.println("Alumno duplicado saltado: " + u.getUsername());
	                }
	            } else {
	                System.out.println("Matricula sin alumno: " + m.getId());
	            }
	        }

	        List<UserDTO> listaAlumnos = new ArrayList<>(alumnosUnicos.values());

	        System.out.println("DEBUG SERVIDOR: Alumnos únicos encontrados: " + listaAlumnos.size());
	        for (UserDTO dto : listaAlumnos) {
	            System.out.println(" -> " + dto.getUsername() + " (ID: " + dto.getId() + ")");
	        }

	        return new SocketData("ALUMNOS_OK", listaAlumnos);

	    } catch (Exception e) {
	        e.printStackTrace();
	        return new SocketData("ERROR", "Fallo al leer alumnos");
	    }
	}


	private SocketData obtenerReuniones(Long id) {
		if (id == null)
			return new SocketData("ERROR", "Falta ID");
		try {
			List<Reunion> reuniones = reunionRepository.findByProfesorId(id.intValue());
			for (Reunion r : reuniones) {
				if (r.getProfesor() != null)
					r.setProfesor(null);
				if (r.getAlumno() != null) {
					User a = r.getAlumno();
					a.setPassword(null);
					a.setReunionesComoProfesor(null);
					a.setReunionesComoAlumno(null);
				}
			}
			return new SocketData("REUNIONES_OK", reuniones);
		} catch (Exception e) {
			e.printStackTrace();
			return new SocketData("ERROR", "Fallo al leer reuniones");
		}
	}

	private SocketData obtenerProfesores() {
		try {
			List<Map<String, Object>> profs = userRepository.findByTipoId(3).stream().map(u -> {
				Map<String, Object> m = new HashMap<>();
				m.put("id", u.getId());
				m.put("nombre", u.getNombre());
				m.put("apellido", u.getApellidos());
				return m;
			}).collect(Collectors.toList());
			return new SocketData("PROFESORES_OK", profs);
		} catch (Exception e) {
			e.printStackTrace();
			return new SocketData("ERROR", "No se pudieron obtener los profesores");
		}
	}

	private SocketData procesarCrearReunion(Object data) {
		try {
			Map<String, Object> map = (Map<String, Object>) data;
			int profesorId = getIntFromMap(map, "profesorId");
			int alumnoId = getIntFromMap(map, "alumnoId");
			String titulo = (String) map.get("titulo");
			String asunto = (String) map.get("asunto");
			String fechaStr = (String) map.get("fecha");
			String aula = (String) map.get("aula");
			String estado = map.get("estado") != null ? (String) map.get("estado") : "Pendiente";
			Date fecha = sdf.parse(fechaStr);

			Reunion reunion = new Reunion();
			reunion.setProfesor(userRepository.findById(profesorId).orElse(null));
			reunion.setAlumno(userRepository.findById(alumnoId).orElse(null));
			reunion.setTitulo(titulo);
			reunion.setAsunto(asunto);
			reunion.setFecha(fecha);
			reunion.setAula(aula);
			reunion.setEstado(estado);

			reunionRepository.save(reunion);
			return new SocketData("CREAR_REUNION_OK", "Reunión creada correctamente");
		} catch (Exception e) {
			e.printStackTrace();
			return new SocketData("ERROR", "Error al crear: " + e.getMessage());
		}
	}

	private SocketData procesarCambiarEstado(Object data) {
		try {
			Map<String, Object> map = (Map<String, Object>) data;
			int reunionId = getIntFromMap(map, "reunionId");
			String estado = (String) map.get("estado");

			Reunion reunion = reunionRepository.findById(reunionId).orElse(null);
			if (reunion == null)
				return new SocketData("ERROR", "Reunión no encontrada");

			reunion.setEstado(estado);
			reunionRepository.save(reunion);
			return new SocketData("CAMBIAR_ESTADO_REUNION_OK", "Estado cambiado correctamente");
		} catch (Exception e) {
			e.printStackTrace();
			return new SocketData("ERROR", "Error al cambiar estado");
		}
	}

	private int getIntFromMap(Map<String, Object> map, String key) {
		Object val = map.get(key);
		if (val instanceof Number)
			return ((Number) val).intValue();
		else if (val instanceof String)
			return Integer.parseInt((String) val);
		throw new IllegalArgumentException("Valor inválido para " + key);
	}
}
