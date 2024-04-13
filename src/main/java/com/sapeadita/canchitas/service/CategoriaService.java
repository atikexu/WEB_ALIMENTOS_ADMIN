package com.sapeadita.canchitas.service;

import java.util.List;

import com.sapeadita.canchitas.bean.CategoriaBean;
import com.sapeadita.canchitas.bean.SubCategoriaBean;

public interface CategoriaService {
	public List<CategoriaBean> listarCategoria(CategoriaBean categoriaBean) throws Exception;
	public CategoriaBean obtenerCategoriaXId(CategoriaBean categoriaBean) throws Exception;
	public CategoriaBean registrarCategoria(CategoriaBean categoriaBean) throws Exception;
	public CategoriaBean actualizarCategoria(CategoriaBean categoriaBean) throws Exception;
	
	public List<SubCategoriaBean> listarSubCategoria(SubCategoriaBean subcategoriaBean) throws Exception;
}
