package com.sapeadita.canchitas.bean;

import java.io.Serializable;

public class MensajeBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private String mensaje1;
	private String mensaje2;
	private String error1;
	private String error2;
	private String advertencia1;
	private String advertencia2;
	public String getMensaje1() {
		return mensaje1;
	}
	public void setMensaje1(String mensaje1) {
		this.mensaje1 = mensaje1;
	}
	public String getMensaje2() {
		return mensaje2;
	}
	public void setMensaje2(String mensaje2) {
		this.mensaje2 = mensaje2;
	}
	public String getError1() {
		return error1;
	}
	public void setError1(String error1) {
		this.error1 = error1;
	}
	public String getError2() {
		return error2;
	}
	public void setError2(String error2) {
		this.error2 = error2;
	}
	public String getAdvertencia1() {
		return advertencia1;
	}
	public void setAdvertencia1(String advertencia1) {
		this.advertencia1 = advertencia1;
	}
	public String getAdvertencia2() {
		return advertencia2;
	}
	public void setAdvertencia2(String advertencia2) {
		this.advertencia2 = advertencia2;
	}
	

}
