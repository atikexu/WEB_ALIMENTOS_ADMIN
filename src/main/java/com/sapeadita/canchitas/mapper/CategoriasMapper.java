package com.sapeadita.canchitas.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sapeadita.canchitas.bean.CategoriaBean;

public class CategoriasMapper implements RowMapper<CategoriaBean>{
	@Override
	public CategoriaBean mapRow(ResultSet rs, int rowNum) throws SQLException {
		CategoriaBean categoriaBean = new CategoriaBean();
		categoriaBean.setIdCategoria(rs.getInt("idcategoria"));
		categoriaBean.setNombre(rs.getString("nombre"));
		categoriaBean.setDescripcion(rs.getString("descripcion"));
		categoriaBean.setMarca(rs.getString("marca"));
		categoriaBean.setEstado(rs.getString("estado"));
//		categoriaBean.setImagenBlob(rs.getBlob("imagen"));mediumblob imagenes
		categoriaBean.setNombreImagen(rs.getString("nombreimagen"));
		return categoriaBean;
		
	}
}
