package com.sapeadita.canchitas.util;

import java.io.Serializable;

import org.jboss.aerogear.security.otp.Totp;

public class Utils implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static String obtenerCodigo(String usuario) {
		Totp generator = new Totp(usuario);
		return generator.now();
	}

}
