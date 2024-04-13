package com.sapeadita.canchitas.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sapeadita.canchitas.bean.UsuarioBean;
import com.sapeadita.canchitas.dao.LoginDao;
import com.sapeadita.canchitas.service.LoginService;
import com.sapeadita.canchitas.util.Utils;

@Service
public class LoginServiceImpl implements LoginService{
	
	@Autowired
	LoginDao loginDao;
	
	Utils utils;

	@Override
	public UsuarioBean verificarUsuario(UsuarioBean usuario) throws Exception {
		// TODO Auto-generated method stub
		return loginDao.verificarUsuario(usuario);
	}

	@Override
	public UsuarioBean registrarUsuario(UsuarioBean usuario) throws Exception {
		// TODO Auto-generated method stub
		usuario.setCodigoValidacion(Utils.obtenerCodigo(usuario.getUsername()));
		return loginDao.registrarUsuario(usuario);
	}

	@Override
	public UsuarioBean verificarUsuarioXUser(String usuario) throws Exception {
		// TODO Auto-generated method stub
		return loginDao.verificarUsuarioXUser(usuario);
	}

	@Override
	public UsuarioBean actualizarEstadoValidacionUsuario(UsuarioBean usuario) throws Exception {
		// TODO Auto-generated method stub
		return loginDao.actualizarEstadoValidacionUsuario(usuario);
	}

	@Override
	public UsuarioBean verificarUsuarioXUserXCodigo(String usuario, String codigo) throws Exception {
		// TODO Auto-generated method stub
		return loginDao.verificarUsuarioXUserXCodigo(usuario, codigo);
	}
	
	@Override
	public UsuarioBean verificarUsuarioXUserXEmail(String usuario, String email) throws Exception {
		// TODO Auto-generated method stub
		return loginDao.verificarUsuarioXUserXEmail(usuario, email);
	}
	
	@Override
	public UsuarioBean actualizarContraUsuario(UsuarioBean usuario) throws Exception {
		// TODO Auto-generated method stub
		usuario.setPassword(Utils.obtenerCodigo("recup"));
		return loginDao.actualizarContraUsuario(usuario);
	}

}
