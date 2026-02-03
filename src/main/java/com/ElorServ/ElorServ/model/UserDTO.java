package com.ElorServ.ElorServ.model;

public class UserDTO {
	private int id;
	private String nombre;
	private String apellido;
	private String username;
	private String email;
	private String dni;
	private String foto;
	private TipoDTO tipo;

	public UserDTO() {
	}

	public UserDTO(int id, String nombre, String apellido, String username, String email, String dni, String foto,
			TipoDTO tipo) {
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.username = username;
		this.email = email;
		this.dni = dni;
		this.foto = foto;
		this.tipo = tipo;
	}

	// Getters y setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public TipoDTO getTipo() {
		return tipo;
	}

	public void setTipo(TipoDTO tipo) {
		this.tipo = tipo;
	}
	
	@Override
	public String toString() {
	    return nombre + " " + apellido;
	}
}
