package com.sapeadita.canchitas.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.sapeadita.canchitas.bean.UsuarioBean;
import com.sapeadita.canchitas.dao.LoginDao;
import com.sapeadita.canchitas.mapper.UsuarioMapper;

@Repository
public class LoginDaoImpl implements LoginDao{

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@Override
	public UsuarioBean verificarUsuario(UsuarioBean usuarioBean) throws Exception {
		UsuarioBean usuario = new UsuarioBean();

		String sql="select * from alimentos.usuarios where username=? and password=?";
		List<UsuarioBean> a= jdbcTemplate.query(sql,new UsuarioMapper(),usuarioBean.getUsername(),usuarioBean.getPassword());
		
		usuario = a.get(0);
		return usuario;
		
	}
	
	@Override
	public UsuarioBean verificarUsuarioXUser(String usuarioBean) throws Exception {
		UsuarioBean usuario = new UsuarioBean();
		String sql="select * from alimentos.usuarios where username=? and estadovalidacion='1'";
		List<UsuarioBean> a= jdbcTemplate.query(sql,new UsuarioMapper(),usuarioBean);
		if(a.isEmpty()) {
			usuario = null;
		}else {
			usuario = a.get(0);
		}
		
		return usuario;
	}
	
	@Override
	public UsuarioBean registrarUsuario(UsuarioBean usuarioBean) throws Exception {
		String sql = "insert into alimentos.usuarios(username,nombre,email,password,codigovalidacion,estadovalidacion) values(?,?,?,?,?,?)";
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, usuarioBean.getUsername());
                ps.setString(2, usuarioBean.getNombre());
                ps.setString(3, usuarioBean.getEmail());
                ps.setString(4, passwordEncoder.encode(usuarioBean.getPassword()));
                ps.setString(5, usuarioBean.getCodigoValidacion());
                ps.setString(6, "0");
                return ps;
            }
        }, holder);
        if (holder.getKeys().size() > 1) {
        	Long newUserId2 = (Long) holder.getKeys().get("id");
        	usuarioBean.setIdUsuario(newUserId2.intValue());
        }
//        int newUserId = holder.getKey().intValue();
//        usuarioBean.setIdUsuario(newUserId);
        return usuarioBean;
	}
	
	@Override
	public UsuarioBean actualizarEstadoValidacionUsuario(UsuarioBean usuarioBean) throws Exception {
		String sql = "update alimentos.usuarios set estadovalidacion=? where username=? and codigovalidacion=?";
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, "1");
                ps.setString(2, usuarioBean.getUsername());
                ps.setString(3, usuarioBean.getCodigoValidacion());
                return ps;
            }
        }, holder);
//        int newUserId=0;
//        if (holder.getKeys().size() > 1) {
//        	Long newUserId2 = (Long) holder.getKeys().get("id");
//        	usuarioBean.setIdUsuario(newUserId2.intValue());
//        }
//        int newUserId = holder.getKey().intValue();
        usuarioBean.setEstadoValidacion("1");
        return usuarioBean;
	}
	
	@Override
	public UsuarioBean verificarUsuarioXUserXCodigo(String usuario, String codigo) throws Exception {
		UsuarioBean usuarioEncontrado = new UsuarioBean();
		String sql="select * from alimentos.usuarios where username=? and codigovalidacion=? and estadovalidacion='0'";
		List<UsuarioBean> a= jdbcTemplate.query(sql,new UsuarioMapper(),usuario,codigo);
		if(a.isEmpty()) {
			usuarioEncontrado = null;
		}else {
			usuarioEncontrado = a.get(0);
		}
		
		return usuarioEncontrado;
	}
	
	@Override
	public UsuarioBean verificarUsuarioXUserXEmail(String usuario, String email) throws Exception {
		UsuarioBean usuarioEncontrado = new UsuarioBean();
		String sql="select * from alimentos.usuarios where (username=? or email=?) and estadovalidacion='1'";
		List<UsuarioBean> a= jdbcTemplate.query(sql,new UsuarioMapper(),usuario,email);
		if(a.isEmpty()) {
			usuarioEncontrado = null;
		}else {
			usuarioEncontrado = a.get(0);
		}
		
		return usuarioEncontrado;
	}
	
	@Override
	public UsuarioBean actualizarContraUsuario(UsuarioBean usuarioBean) throws Exception {
		String sql = "update alimentos.usuarios set password=? where email=? and username=? and estadovalidacion='1'";
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, passwordEncoder.encode(usuarioBean.getPassword()));
                ps.setString(2, usuarioBean.getEmail());
                ps.setString(3, usuarioBean.getUsername());
                return ps;
            }
        }, holder);

        usuarioBean.setEstadoValidacion("1");
        return usuarioBean;
	}

}
