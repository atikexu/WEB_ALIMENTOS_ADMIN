package com.sapeadita.canchitas.dao;

import java.util.List;

import com.sapeadita.canchitas.bean.CategoriaBean;
import com.sapeadita.canchitas.bean.SubCategoriaBean;

public interface CategoriaDao {
	public abstract List<CategoriaBean> listarCategoria(CategoriaBean categoriaBean) throws Exception;
	public abstract CategoriaBean obtenerCategoriaXId(CategoriaBean categoriaBean) throws Exception;
	public abstract CategoriaBean registrarCategoria(CategoriaBean categoriaBean) throws Exception;
	public abstract CategoriaBean actualizarCategoria(CategoriaBean categoriaBean) throws Exception;
	
	public abstract List<SubCategoriaBean> listarSubCategoria(SubCategoriaBean subcategoriaBean);
}
