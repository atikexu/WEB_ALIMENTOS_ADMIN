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

import com.sapeadita.canchitas.bean.ProductoBean;
import com.sapeadita.canchitas.dao.ProductoDao;
import com.sapeadita.canchitas.mapper.ProductosMapper;

@Repository
public class ProductoDaoImpl implements ProductoDao{

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public List<ProductoBean> listarProducto(ProductoBean productoBean) throws Exception {
		String sql="select * from alimentos.producto order by prioridad asc";
		List<ProductoBean> productos= jdbcTemplate.query(sql,new ProductosMapper());
		return productos;
	}
	
	@Override
	public ProductoBean obtenerProductoXId(ProductoBean productoBean) throws Exception {
		ProductoBean producto = new ProductoBean();
		String sql="select * from alimentos.producto where idproducto=? and estado='1'";
		List<ProductoBean> buscar= jdbcTemplate.query(sql,new ProductosMapper(),productoBean.getIdProducto());
		if(buscar.isEmpty()) {
			producto = null;
		}else {
			producto = buscar.get(0);
		}
		return producto;
	}
	
	@Override
	public ProductoBean registrarProducto(ProductoBean productoBean) throws Exception {
		String sql = "insert into alimentos.producto(idcategoria,idsubcategoria,nombre,descripcion,estado,precio,prioridad,nuevo,nombreimagen,fechacreacion,usuariocreacion) values(?,?,?,?,?,?,?,?,?,?,?)";
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, productoBean.getIdCategoria());
                ps.setInt(2, productoBean.getIdSubCategoria());
                ps.setString(3, productoBean.getNombre());
                ps.setString(4, productoBean.getDescripcion());
                ps.setString(5, productoBean.getEstado());
                ps.setDouble(6, 0);//precio
                ps.setInt(7, 0);//prioridad
                ps.setInt(8, 0);//nuevo
                ps.setString(9, productoBean.getNombreImagen());
                ps.setString(10, productoBean.getFechaCreacion());
                ps.setString(11, productoBean.getUsuarioCreacion());
                return ps;
            }
        }, holder);
        if (holder.getKeys().size() > 1) {
        	Long newId2 = (Long) holder.getKeys().get("idproducto");
        	productoBean.setIdProducto(newId2.intValue());
        }
        return productoBean;
	}
	
	@Override
	public ProductoBean actualizarProducto(ProductoBean productoBean) throws Exception {
//		String sql = "update alimentos.producto "
//				+ "set estado=?, nombre=?, descripcion=?, precio=? where idproducto=?";
		String sql = "update alimentos.producto "
				+ "set estado=?, nombre=?, descripcion=?, nombreimagen=? , idcategoria=?, idsubcategoria=?, usuariomodificacion=?, fechamodificacion=? where idproducto=?";
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, productoBean.getEstado());
                ps.setString(2, productoBean.getNombre());
                ps.setString(3, productoBean.getDescripcion());
//                ps.setDouble(4, productoBean.getPrecio());
                ps.setString(4, productoBean.getNombreImagen());
                ps.setInt(5, productoBean.getIdCategoria());
                ps.setInt(6, productoBean.getIdSubCategoria());
                ps.setString(7, productoBean.getUsuarioModificacion());
                ps.setString(8, productoBean.getFechaModificacion());
                ps.setInt(9, productoBean.getIdProducto());
                return ps;
            }
        }, holder);
        return productoBean;
	}
	
	@Override
	public List<ProductoBean> listarProductosXCategoria(ProductoBean productoBean) throws Exception {
		String sql="";
		if(productoBean.getIdCategoria()!=9) {
			sql="select * from alimentos.producto p, alimentos.categoria c, alimentos.subcategoria s "
				+ "where p.idcategoria = c.idcategoria "
				+ "and p.idsubcategoria = s.idsubcategoria "
				+ "and (p.idcategoria=? or '0'=?) order by p.prioridad asc";
		}else {
			sql="select * from alimentos.producto p, alimentos.categoria c, alimentos.subcategoria s "
				+ "where p.idcategoria = c.idcategoria "
				+ "and p.idsubcategoria = s.idsubcategoria "
				+ "and (p.idcategoria=? or '0'=?)  order by p.prioridad asc";
		}
		
		List<ProductoBean> productos= jdbcTemplate.query(sql,new ProductosMapper(),productoBean.getIdCategoria(),productoBean.getIdCategoria());
		return productos;
	}

}
