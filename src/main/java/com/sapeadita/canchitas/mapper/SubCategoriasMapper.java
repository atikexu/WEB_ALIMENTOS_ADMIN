package com.sapeadita.canchitas.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sapeadita.canchitas.bean.CategoriaBean;
import com.sapeadita.canchitas.bean.SubCategoriaBean;

public class SubCategoriasMapper implements RowMapper<SubCategoriaBean>{
	@Override
	public SubCategoriaBean mapRow(ResultSet rs, int rowNum) throws SQLException {
		SubCategoriaBean subcategoriaBean = new SubCategoriaBean();
		subcategoriaBean.setIdSubCategoria(rs.getInt("idsubcategoria"));
		subcategoriaBean.setIdCategoria(rs.getInt("idcategoria"));
		subcategoriaBean.setNombre(rs.getString("nombre"));
		subcategoriaBean.setDescripcion(rs.getString("descripcion"));
//		categoriaBean.setMarca(rs.getString("marca"));
		subcategoriaBean.setEstado(rs.getString("estado"));
//		categoriaBean.setImagenBlob(rs.getBlob("imagen"));mediumblob imagenes
//		categoriaBean.setNombreImagen(rs.getString("nombreimagen"));
		return subcategoriaBean;
		
	}
}
