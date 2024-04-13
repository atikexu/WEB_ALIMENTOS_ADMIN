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
import org.springframework.stereotype.Repository;

import com.sapeadita.canchitas.bean.CategoriaBean;
import com.sapeadita.canchitas.bean.SubCategoriaBean;
import com.sapeadita.canchitas.dao.CategoriaDao;
import com.sapeadita.canchitas.mapper.CategoriasMapper;
import com.sapeadita.canchitas.mapper.SubCategoriasMapper;

@Repository
public class CategoriaDaoImpl implements CategoriaDao{

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public List<CategoriaBean> listarCategoria(CategoriaBean categoriaBean) throws Exception {
		String sql="select * from alimentos.categoria where estado='1'";
		List<CategoriaBean> categorias= jdbcTemplate.query(sql,new CategoriasMapper());
		return categorias;
	}
	
	@Override
	public CategoriaBean obtenerCategoriaXId(CategoriaBean categoriaBean) throws Exception {
		CategoriaBean categoria = new CategoriaBean();
		String sql="select * from alimentos.categoria where idcategoria=? and estado='1'";
		List<CategoriaBean> buscar= jdbcTemplate.query(sql,new CategoriasMapper(),categoriaBean.getIdCategoria());
		if(buscar.isEmpty()) {
			categoria = null;
		}else {
			categoria = buscar.get(0);
			
//			InputStream inputStream = categoria.getImagenBlob().getBinaryStream();
//	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//	        byte[] buffer = new byte[4096];
//	        int bytesRead = -1;
//	         
//	        while ((bytesRead = inputStream.read(buffer)) != -1) {
//	            outputStream.write(buffer, 0, bytesRead);                  
//	        }
//	         
//	        byte[] imageBytes = outputStream.toByteArray();
//	        String base64Image = Base64.getEncoder().encodeToString(imageBytes);
//
//	        inputStream.close();
//	        outputStream.close();
//	         
//	        categoria.setImagen(base64Image);
		}
		return categoria;
	}
	
	@Override
	public CategoriaBean registrarCategoria(CategoriaBean categoriaBean) throws Exception {
		String sql = "insert into alimentos.categoria(nombre,descripcion,estado,marca) values(?,?,?,?)";
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, categoriaBean.getNombre());
                ps.setString(2, categoriaBean.getDescripcion());
                ps.setString(3, categoriaBean.getEstado());
                ps.setString(4, categoriaBean.getMarca());
                return ps;
            }
        }, holder);
        if (holder.getKeys().size() > 1) {
        	Long newId2 = (Long) holder.getKeys().get("idcategoria");
        	categoriaBean.setIdCategoria(newId2.intValue());
        }
        return categoriaBean;
	}
	
	@Override
	public CategoriaBean actualizarCategoria(CategoriaBean categoriaBean) throws Exception {
		String sql = "update alimentos.categoria "
				+ "set estado=?, nombre=?, descripcion=?, marca=? where idcategoria=?";
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, categoriaBean.getEstado());
                ps.setString(2, categoriaBean.getNombre());
                ps.setString(3, categoriaBean.getDescripcion());
                ps.setString(4, categoriaBean.getMarca());
                ps.setInt(5, categoriaBean.getIdCategoria());
                return ps;
            }
        }, holder);
        return categoriaBean;
	}

	@Override
	public List<SubCategoriaBean> listarSubCategoria(SubCategoriaBean subcategoriaBean) {
		String sql="select * from alimentos.subcategoria where estado='1' order by idsubcategoria asc";
		List<SubCategoriaBean> subcategorias= jdbcTemplate.query(sql,new SubCategoriasMapper());
		return subcategorias;
	}

}
