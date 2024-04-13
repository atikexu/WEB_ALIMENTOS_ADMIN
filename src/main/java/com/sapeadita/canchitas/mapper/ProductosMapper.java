package com.sapeadita.canchitas.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sapeadita.canchitas.bean.ProductoBean;

public class ProductosMapper implements RowMapper<ProductoBean>{
	@Override
	public ProductoBean mapRow(ResultSet rs, int rowNum) throws SQLException {
		ProductoBean productoBean = new ProductoBean();
		productoBean.setIdProducto(rs.getInt("idproducto"));
		productoBean.setIdCategoria(rs.getInt("idcategoria"));
		productoBean.setIdSubCategoria(rs.getInt("idsubcategoria"));
		productoBean.setNombre(rs.getString("nombre"));
		productoBean.setDescripcion(rs.getString("descripcion"));
		productoBean.setPrecio(rs.getDouble("precio"));
		productoBean.setEstado(rs.getString("estado"));
		productoBean.setNombreImagen(rs.getString("nombreimagen"));
		return productoBean;
	}
}
