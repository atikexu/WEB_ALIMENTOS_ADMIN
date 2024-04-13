package com.sapeadita.canchitas.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sapeadita.canchitas.bean.CategoriaBean;
import com.sapeadita.canchitas.bean.MensajeBean;
import com.sapeadita.canchitas.bean.ProductoBean;
import com.sapeadita.canchitas.bean.UsuarioBean;
import com.sapeadita.canchitas.service.CategoriaService;
import com.sapeadita.canchitas.service.EmailService;
import com.sapeadita.canchitas.service.LoginService;
import com.sapeadita.canchitas.service.ProductoService;

@Controller
@RequestMapping("/login")
public class LoginController {
	public static Logger log = LogManager.getLogger(LoginController.class);
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	CategoriaService categoriaService;
	
	@Autowired
	ProductoService productoService;
	
	@GetMapping("/login")
	public String inicio(Model model, Authentication auth, HttpSession session) throws Exception {
		log.info("Inicio login");
		List<CategoriaBean> categorias = categoriaService.listarCategoria(new CategoriaBean());
		List<ProductoBean> productos = productoService.listarProducto(new ProductoBean());
		if(session.getAttribute("usuario")!=null) {
			model.addAttribute("categorias", categorias);
			model.addAttribute("productos", productos);
			model.addAttribute("producto", new ProductoBean());
			model.addAttribute("categoria", new CategoriaBean());
			return "private/principal_alimentos";
		}
		model.addAttribute("usuario", new UsuarioBean());
		return "/login/login";
	}
	
	@GetMapping("/register")
	public String iniciarRegistro(Model model, Authentication auth, HttpSession session) {
		log.info("Inicio iniciarRegistro");
		if(session.getAttribute("usuario")!=null) {
			return "/private/principal";
		}
		model.addAttribute("usuario", new UsuarioBean());
		return "/login/register";
	}
	
	@PostMapping("/register")
	public String registrar(@ModelAttribute UsuarioBean usuario, BindingResult result, Model model, RedirectAttributes rm) throws Exception {
		log.info("Inicio registrar");
		if(result.hasErrors()) {
			return "redirect:/login/register";
		}else {
			UsuarioBean newUser = new UsuarioBean();
			newUser = loginService.registrarUsuario(usuario);
			model.addAttribute("usuario", newUser);
			emailService.enviarEmailVerificacion(newUser);
		}
		
		rm.addAttribute("username", usuario.getUsername());
		rm.addAttribute("codigovalidacion", usuario.getCodigoValidacion());
		return "redirect:/login/verifica";
	}
	
	@GetMapping("/verifica")
	public String verificar(String username,String codigoValidacion, Model model, RedirectAttributes rm) {
		MensajeBean mensaje = new MensajeBean();
		UsuarioBean usuario = new UsuarioBean();
		usuario.setUsername(username);
//		mensaje.setMensaje1("incicio verifica");
		model.addAttribute("username", username);
		model.addAttribute("codigoValidacion", codigoValidacion);
		model.addAttribute("usuario", usuario);
		model.addAttribute("mensaje", mensaje);
		log.info("Inicio verifica parametros");
		return "/login/verifica";

	}
	
	@PostMapping("/verificacodigo")
	public String verificarCodigo(@ModelAttribute UsuarioBean usuario, Model model, RedirectAttributes rm) throws Exception {
		MensajeBean mensaje = new MensajeBean();
		UsuarioBean usuarioValidar = new UsuarioBean();
		usuarioValidar = loginService.verificarUsuarioXUserXCodigo(usuario.getUsername(), usuario.getCodigoValidacion());
		if(usuarioValidar==null) {
			usuario.setCodigoValidacion("");
			mensaje.setMensaje1("Código de validación incorrecto");
			model.addAttribute("mensaje", mensaje);
			model.addAttribute("username", usuario.getUsername());
			model.addAttribute("codigoValidacion", "");
			model.addAttribute("usuario", usuario);
			rm.addAttribute("username", usuario.getUsername());
			rm.addAttribute("codigoValidacion", "");
			return "/login/verifica";
		}else {
			loginService.actualizarEstadoValidacionUsuario(usuarioValidar);
			emailService.enviarEmailConfirmacion(usuarioValidar);
			return "redirect:/login/login";
		}
	}
	
	@PostMapping("/verifica")
	public String verificar(@ModelAttribute UsuarioBean usuario, BindingResult result, Model model) throws Exception {
		log.info("Inicio verificar");

			model.addAttribute("usuario", loginService.registrarUsuario(usuario));

		return "/login/verifica";
	}
	
	@GetMapping("/recupera")
	public String inicioRecupera(Model model, Authentication auth, HttpSession session) {
		log.info("Inicio recupera");
		if(session.getAttribute("usuario")!=null) {
			return "/private/principal";
		}
		model.addAttribute("usuario", new UsuarioBean());
		return "/login/recupera";
	}
	
	@PostMapping("/recupera")
	public String buscarRecupera(@ModelAttribute UsuarioBean usuario, BindingResult result, Model model, RedirectAttributes rm) throws Exception {	
		log.info("Inicio buscar recupera");
		MensajeBean mensaje = new MensajeBean();
		UsuarioBean usuarioValidar = new UsuarioBean();
		usuarioValidar = loginService.verificarUsuarioXUserXEmail(usuario.getEmail(), usuario.getEmail());
		if(usuarioValidar==null) {
			usuario.setEmail("");
			mensaje.setMensaje1("Usuario o correo no registrado.");
			model.addAttribute("mensaje", mensaje);
			model.addAttribute("username", usuario.getUsername());
			model.addAttribute("email", "");
			model.addAttribute("usuario", usuario);
			rm.addAttribute("username", usuario.getUsername());
			rm.addAttribute("email", "");
			return "/login/recupera";
		}else {
			usuarioValidar = loginService.actualizarContraUsuario(usuarioValidar);
			emailService.enviarEmailRecuperacion(usuarioValidar);
			return "redirect:/login/login";
		}
		
	}
	
	@GetMapping("/exit")
	public String Salir(Model model, Authentication auth, HttpSession session) {
		session.invalidate();
		return "/login/login";
	}
}
