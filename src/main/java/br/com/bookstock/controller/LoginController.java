package br.com.bookstock.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/login-error")
	public String loginError(ModelMap model, HttpServletRequest resp) {
		HttpSession session = resp.getSession();
		Exception lastException = (Exception) session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
		
		String texto;
		if(lastException instanceof BadCredentialsException) {
			texto = "Login incorreto ou usuário inativo";
		} else {
			texto = lastException.getMessage();
		}
		
		model.addAttribute("alerta", "erro");
		model.addAttribute("titulo", "Acesso Recusado");
		model.addAttribute("texto", texto);
		
		return "login";
	}
}
