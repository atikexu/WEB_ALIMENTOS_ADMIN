package com.sapeadita.canchitas.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sapeadita.canchitas.bean.CategoriaBean;
import com.sapeadita.canchitas.bean.ProductoBean;
import com.sapeadita.canchitas.bean.SubCategoriaBean;
import com.sapeadita.canchitas.service.CategoriaService;
import com.sapeadita.canchitas.service.FTPService;
import com.sapeadita.canchitas.service.ProductoService;
import com.sapeadita.canchitas.bean.UsuarioBean;
import com.sapeadita.canchitas.exceptions.FTPErrors;
import com.sapeadita.canchitas.service.LoginService;

@Controller
@RequestMapping("/private")
public class PrivateController {
	public static Logger log = LogManager.getLogger(PrivateController.class);
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	CategoriaService categoriaService;
	
	@Autowired
	ProductoService productoService;
	
	@Autowired
    private FTPService ftpService;
	
	@GetMapping("/principal")
	public String inicio(Authentication auth, HttpSession session, Model model) throws Exception {
		log.info("Inicio principal");
		String username = auth.getName();
		List<CategoriaBean> categorias = categoriaService.listarCategoria(new CategoriaBean());
		List<SubCategoriaBean> subcategorias = categoriaService.listarSubCategoria(new SubCategoriaBean());
		List<ProductoBean> productos = productoService.listarProducto(new ProductoBean());
		
		if(session.getAttribute("usuario")==null) {
			UsuarioBean usuario = loginService.verificarUsuarioXUser(username);
			usuario.setPassword(null);
			session.setAttribute("usuario", usuario);
		}
		model.addAttribute("categorias", categorias);
		model.addAttribute("subcategorias", subcategorias);
		model.addAttribute("productos", productos);
		model.addAttribute("producto", new ProductoBean());
		model.addAttribute("categoria", new CategoriaBean());
		return "private/principal_alimentos";
	}
	
	@PostMapping("/principal")
	public String buscarProductoCategoria(Authentication auth, HttpSession session, @ModelAttribute CategoriaBean categoria, BindingResult result, Model model, RedirectAttributes rm) throws Exception {
		log.info("Inicio principal buscarProductoCategoria");
		String username = auth.getName();
		List<CategoriaBean> categorias = categoriaService.listarCategoria(new CategoriaBean());
		ProductoBean prod = new ProductoBean();
		prod.setIdCategoria(categoria.getIdCategoria());
		if(categoria.getIdCategoria()!=9) {
			prod.setIdSubCategoria(0);
		}
//		else {
//			prod.setIdSubCategoria(categoria.getIdCategoria());
//		}
		
		
		List<ProductoBean> productos = productoService.listarProductosXCategoria(prod);
		List<SubCategoriaBean> subcategorias = categoriaService.listarSubCategoria(new SubCategoriaBean());
		
		if(session.getAttribute("usuario")==null) {
			UsuarioBean usuario = loginService.verificarUsuarioXUser(username);
			usuario.setPassword(null);
			session.setAttribute("usuario", usuario);
		}
		model.addAttribute("categorias", categorias);
		model.addAttribute("subcategorias", subcategorias);
		model.addAttribute("productos", productos);
		model.addAttribute("producto", new ProductoBean());
		model.addAttribute("categoria", categoria);
		return "private/principal_alimentos";
	}
	
	@GetMapping("/grabarProducto")
	public String mostrarProducto(Authentication auth, HttpSession session, Model model) throws Exception {
		log.info("Inicio mostrarProducto");
		String username = auth.getName();
		List<CategoriaBean> categorias = categoriaService.listarCategoria(new CategoriaBean());
		List<ProductoBean> productos = productoService.listarProducto(new ProductoBean());
		List<SubCategoriaBean> subcategorias = categoriaService.listarSubCategoria(new SubCategoriaBean());
		if(session.getAttribute("usuario")==null) {
			UsuarioBean usuario = loginService.verificarUsuarioXUser(username);
			usuario.setPassword(null);
			session.setAttribute("usuario", usuario);
		}
		model.addAttribute("categorias", categorias);
		model.addAttribute("subcategorias", subcategorias);
		model.addAttribute("productos", productos);
		model.addAttribute("producto", new ProductoBean());
		model.addAttribute("categoria", new CategoriaBean());
		return "private/principal_alimentos";
	}
	
	@PostMapping("/grabarProducto")
	public String grabarProducto(@ModelAttribute ProductoBean producto, BindingResult result, Model model, RedirectAttributes rm, Authentication auth) {
		log.info("Inicio grabarProducto");
		String username = auth.getName();
		ProductoBean productoActualizar = null;
		
		try {
			if(producto.getIdCategoria()!=9) {
				producto.setIdSubCategoria(0);
			}
			MultipartFile file = producto.getImagenMulti();
			
			if(file.getSize()>0) {
				
				File convFile = new File(file.getOriginalFilename());
				System.out.println("Ruta del archivo: " + convFile.getAbsolutePath());
				convFile.createNewFile(); 
				FileOutputStream fos = new FileOutputStream(convFile); 
				fos.write(file.getBytes());
				fos.close();

	//			File convFile = new File(System.getProperty("java.io.tmpdir")+"/"+"archivo.txt");
	//			file.transferTo(convFile);
				try {
	
		              ftpService.connectToFTP("comprayventaperu.pe","alimentosftp@alimentoselectos.pe","Alimentos2021$");
	//	              ftpService.uploadFileToFTP(new File("C:\\Alan\\WORKSPACE_CANCHITA\\canchitas\\src\\main\\resources\\static\\assets\\images\\icon\\logo4.png"),"uploads/","foto.png");
		              ftpService.uploadFileToFTP(convFile,"uploads/",producto.getNombreImagen());
	//	              ftpService.downloadFileFromFTP("uploads/foto.png","/home/kaka.png");
		              ftpService.disconnectFTP();
		        } catch (FTPErrors ftpErrors) {
		        	log.error(ftpErrors.getMessage());
		        }
			}
			if(producto.getIdProducto()==null || producto.getIdProducto().equals("")) {
				if(file.getSize()==0) {
					producto.setNombreImagen("logotipo-alimentos-selectos.png");
				}
				producto.setFechaCreacion(""+LocalDate.now());
				producto.setUsuarioCreacion(username);
				productoActualizar = productoService.registrarProducto(producto);
			}else {
				producto.setFechaModificacion(""+LocalDate.now());
				producto.setUsuarioModificacion(username);
				productoActualizar = productoService.actualizarProducto(producto);
			}
			
			List<CategoriaBean> categorias = categoriaService.listarCategoria(new CategoriaBean());
			List<ProductoBean> productos = productoService.listarProducto(new ProductoBean());
			List<SubCategoriaBean> subcategorias = categoriaService.listarSubCategoria(new SubCategoriaBean());
//        	documento.setMensajeRespuesta("Registro guardado");	
			model.addAttribute("categorias", categorias);
			model.addAttribute("subcategorias", subcategorias);
			model.addAttribute("productos", productos);
			model.addAttribute("producto", new ProductoBean());			
			model.addAttribute("categoria", new CategoriaBean());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		log.info("Fin grabarProducto");
		return "/private/principal_alimentos";
	}
	

	
}
