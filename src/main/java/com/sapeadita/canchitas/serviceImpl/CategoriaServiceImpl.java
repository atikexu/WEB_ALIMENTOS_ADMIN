package com.sapeadita.canchitas.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sapeadita.canchitas.bean.CategoriaBean;
import com.sapeadita.canchitas.bean.SubCategoriaBean;
import com.sapeadita.canchitas.dao.CategoriaDao;
import com.sapeadita.canchitas.service.CategoriaService;

@Service
public class CategoriaServiceImpl implements CategoriaService{
	
	@Autowired
	CategoriaDao categoriaDao;

	@Override
	public List<CategoriaBean> listarCategoria(CategoriaBean categoriaBean) throws Exception {
		return categoriaDao.listarCategoria(categoriaBean);
	}

	@Override
	public CategoriaBean obtenerCategoriaXId(CategoriaBean categoriaBean) throws Exception {
		return categoriaDao.obtenerCategoriaXId(categoriaBean);
	}

	@Override
	public CategoriaBean registrarCategoria(CategoriaBean categoriaBean) throws Exception {
		return categoriaDao.registrarCategoria(categoriaBean);
	}

	@Override
	public CategoriaBean actualizarCategoria(CategoriaBean categoriaBean) throws Exception {
		return categoriaDao.actualizarCategoria(categoriaBean);
	}

	@Override
	public List<SubCategoriaBean> listarSubCategoria(SubCategoriaBean subcategoriaBean) throws Exception {
		return categoriaDao.listarSubCategoria(subcategoriaBean);
	}
}
