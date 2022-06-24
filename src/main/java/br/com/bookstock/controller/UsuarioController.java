package br.com.bookstock.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.bookstock.model.domain.Perfil;
import br.com.bookstock.model.domain.Usuario;
import br.com.bookstock.model.domain.service.PerfilService;
import br.com.bookstock.model.domain.service.UsuarioService;
import br.com.bookstock.util.PaginacaoUtil;

@Controller
@RequestMapping("usuario")
public class UsuarioController {

	@Autowired
	private PerfilService service;

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping("/cadastro")
	public String cadastrarUsuario(Usuario usuario, Model model) {
		List<Perfil> listaPerfis = service.listarPerfis();
		model.addAttribute("perfis", listaPerfis);

		return "usuario/cadastro-usuario";
	}

	@GetMapping("/listar")
	public String listarUsuarios(ModelMap model, 
			@RequestParam("pagina") Optional<Integer> pagina,
			@RequestParam("direcao") Optional<String> direcao) {
		
		int paginaAtual = pagina.orElse(1);
		String ordem = direcao.orElse("asc");
		
		PaginacaoUtil<Usuario> registros = usuarioService.buscaUsuariosPorPaginacao(paginaAtual, ordem);
		model.addAttribute("paginaUsuarios", registros);

		return "usuario/lista-usuarios";
	}
	
	@GetMapping("/pre-editar/{id}")
	public String preEditar(RedirectAttributes attr, @PathVariable long id) {
		Usuario usuario = usuarioService.buscarUsuarioPorId(id);
		attr.addAttribute("usuario", usuario);
		
		return "redirect:/usuario/listar";
	}

	@PostMapping("/cadastrar")
	public String cadastrarUsuario(Usuario usuario, RedirectAttributes attr) {
		usuarioService.cadastrarUsuario(usuario);

		attr.addFlashAttribute("sucesso", "Usuario cadastrado com sucesso!");

		return "redirect:/usuario/cadastro";
	}

}
