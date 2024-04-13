package com.sapeadita.canchitas.service;

import com.sapeadita.canchitas.bean.UsuarioBean;

public interface EmailService {
	public void enviarEmailVerificacion(UsuarioBean usuario) throws Exception;
	public void enviarEmailConfirmacion(UsuarioBean usuario) throws Exception;
	public void enviarEmailRecuperacion(UsuarioBean usuario) throws Exception;
}
