package com.sapeadita.canchitas.dao;

import com.sapeadita.canchitas.bean.UsuarioBean;

public interface LoginDao {
	public abstract UsuarioBean verificarUsuario(UsuarioBean usuarioBean) throws Exception;
	public abstract UsuarioBean verificarUsuarioXUser(String usuario) throws Exception;
	public abstract UsuarioBean registrarUsuario(UsuarioBean usuario) throws Exception;
	public abstract UsuarioBean actualizarEstadoValidacionUsuario(UsuarioBean usuario) throws Exception;
	public abstract UsuarioBean verificarUsuarioXUserXCodigo(String usuario, String codigo) throws Exception;
	public abstract UsuarioBean verificarUsuarioXUserXEmail(String usuario, String email) throws Exception;
	public abstract UsuarioBean actualizarContraUsuario(UsuarioBean usuario) throws Exception;
}
