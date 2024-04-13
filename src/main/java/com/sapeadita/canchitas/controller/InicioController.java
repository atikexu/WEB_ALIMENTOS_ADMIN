package com.sapeadita.canchitas.controller;

import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sapeadita.canchitas.bean.UsuarioBean;
import com.sapeadita.canchitas.dao.LoginDao;

@Controller
public class InicioController {
	public static Logger log = LogManager.getLogger(InicioController.class);
	
	@Autowired
	LoginDao loginDao;
	
	@GetMapping("/")
	public String inicio(Model model, Authentication auth, HttpSession session) throws Exception {
		log.info("Inicio");
		if(session.getAttribute("usuario")!=null) {
			return "redirect:/private/principal";
		}
		model.addAttribute("usuario", new UsuarioBean());
		return "redirect:/public/inicio";
	}
	
}
