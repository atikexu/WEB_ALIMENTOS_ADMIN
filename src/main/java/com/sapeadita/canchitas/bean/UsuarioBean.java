package com.sapeadita.canchitas.bean;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

public class UsuarioBean implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	private Integer idUsuario;
	private String username;
	private String cargo;
	private String nombre;
	private String password;
	private String email;
	private String codigoValidacion;
	private String estadoValidacion;
	
	
	public Integer getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCodigoValidacion() {
		return codigoValidacion;
	}
	public void setCodigoValidacion(String codigoValidacion) {
		this.codigoValidacion = codigoValidacion;
	}
	public String getEstadoValidacion() {
		return estadoValidacion;
	}
	public void setEstadoValidacion(String estadoValidacion) {
		this.estadoValidacion = estadoValidacion;
	}

}
