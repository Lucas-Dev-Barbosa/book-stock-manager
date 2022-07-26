package br.com.bookstock.controller;

import java.security.Principal;
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
		model.addAttribute("perfis", getPerfis());

		return "usuario/cadastro-usuario";
	}

	@GetMapping("/listar")
	public String listarUsuarios(ModelMap model, 
			@RequestParam("pagina") Optional<Integer> pagina,
			@RequestParam("direcao") Optional<String> direcao,
			@RequestParam("filtro") Optional<String> filtro) {
		
		int paginaAtual = pagina.orElse(1);
		String ordem = direcao.orElse("asc");
		String busca = filtro.orElse(null); 
		
		PaginacaoUtil<Usuario> registros = usuarioService.buscaUsuariosPorPaginacao(paginaAtual, ordem, busca);
		model.addAttribute("paginaUsuarios", registros);

		return "usuario/lista-usuarios";
	}
	
	@GetMapping("/pre-editar/{id}")
	public String preEditar(RedirectAttributes attr, @PathVariable long id) {
		Usuario usuario = usuarioService.buscarUsuarioPorId(id);
		attr.addFlashAttribute("usuario", usuario);
		attr.addFlashAttribute("perfis", getPerfis());
		
		return "redirect:/usuario/listar";
	}
	
	@PostMapping("/editar")
	public String editarUsuario(Usuario usuario, RedirectAttributes attr) {
		usuarioService.editarUsuario(usuario);

		attr.addFlashAttribute("sucesso", "Dados do Usuario salvos com sucesso!");

		return "redirect:/usuario/listar";
	}
	
	@GetMapping("/pre-excluir/{id}")
	public String preExcluir(RedirectAttributes attr, @PathVariable long id) {
		attr.addFlashAttribute("id", id);
		
		return "redirect:/usuario/listar";
	}
	
	@GetMapping("/excluir/{id}")
	public String excluirUsuario(@PathVariable long id, RedirectAttributes attr) {
		usuarioService.excluirUsuario(id);

		attr.addFlashAttribute("sucesso", "Usuario excluído com sucesso!");

		return "redirect:/usuario/listar";
	}

	@PostMapping("/cadastrar")
	public String cadastrarUsuario(Usuario usuario, RedirectAttributes attr) {
		usuarioService.cadastrarUsuario(usuario);

		attr.addFlashAttribute("sucesso", "Usuario cadastrado com sucesso!");

		return "redirect:/usuario/cadastro";
	}
	
	@GetMapping("/perfil")
	public String listarPerfilUsuario(Model model, Principal principal) {
		Usuario usuario = usuarioService.buscarUsuarioPorEmail(principal.getName());
		
		model.addAttribute("usuario", usuario);

		return "usuario/perfil-usuario";
	}
	
	private List<Perfil> getPerfis() {
		List<Perfil> listaPerfis = service.listarPerfis();
		return listaPerfis;
	}

}
