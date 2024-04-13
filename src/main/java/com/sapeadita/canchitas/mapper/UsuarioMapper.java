package com.sapeadita.canchitas.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sapeadita.canchitas.bean.UsuarioBean;

public class UsuarioMapper implements RowMapper<UsuarioBean>{
	@Override
	public UsuarioBean mapRow(ResultSet rs, int rowNum) throws SQLException {
		UsuarioBean usuarioBean = new UsuarioBean();
		usuarioBean.setIdUsuario(rs.getInt("id"));
		usuarioBean.setUsername(rs.getString("username"));
		usuarioBean.setNombre(rs.getString("nombre"));
		usuarioBean.setEmail(rs.getString("email"));
		usuarioBean.setPassword(rs.getString("password"));
		usuarioBean.setCodigoValidacion(rs.getString("codigovalidacion"));
		usuarioBean.setEstadoValidacion(rs.getString("estadovalidacion"));
		return usuarioBean;
	}
}
