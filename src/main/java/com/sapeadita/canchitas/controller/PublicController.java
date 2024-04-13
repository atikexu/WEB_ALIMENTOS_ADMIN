package com.sapeadita.canchitas.controller;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sapeadita.canchitas.service.LoginService;

@Controller
@RequestMapping("/public")
public class PublicController {
	public static Logger log = LogManager.getLogger(PublicController.class);
	
	@Autowired
	LoginService loginService;
	
	@GetMapping("/inicio")
	public String inicio(Model model, Authentication auth, HttpSession session) throws Exception {
		log.info("public inicio");
		return "/public/inicio";

	}
}
