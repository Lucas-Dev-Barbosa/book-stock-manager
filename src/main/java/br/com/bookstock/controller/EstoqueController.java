package br.com.bookstock.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.bookstock.model.domain.Estoque;
import br.com.bookstock.model.domain.service.EditorialService;
import br.com.bookstock.util.PaginacaoUtil;

@Controller
@RequestMapping("estoque")
public class EstoqueController {
	
	@Autowired
	private EditorialService service;

	@GetMapping("/cadastro-livro")
	public String cadastrarLivro(Estoque estoque) {
		return "editorial/cadastro-livro";
	}
	
	@GetMapping("/listar")
	public String listarEstoque(Model model,
			@RequestParam("pagina") Optional<Integer> pagina,
			@RequestParam("direcao") Optional<String> direcao,
			@RequestParam("filtro") Optional<String> filtro,
			@RequestParam("rota") Optional<String> rota) {
		
		int paginaAtual = pagina.orElse(1);
		String ordem = direcao.orElse("asc");
		String busca = filtro.orElse(null);
		
		String rotaFinal = rota.orElse("livros");
		
		PaginacaoUtil<Estoque> listaEstoque = service.buscarLivrosCadastradosPorPaginacao(paginaAtual, ordem, busca);
		
		model.addAttribute("paginaEstoque", listaEstoque);
		
		if(rotaFinal.equals("livros")) {
			return "editorial/lista-livros";
		} else if(rotaFinal.equals("estoque")) {
			return "comercial/lista-estoque";
		}
		
		return "/";
	}
	
	@PostMapping("/salvar-livro")
	public String cadastrarLivro(@RequestParam MultipartFile fotoCapa, @Valid Estoque estoque, BindingResult result, RedirectAttributes attr, Model model) {
		boolean hasError = result.hasErrors() || fotoCapa.isEmpty();
		
		if(fotoCapa.isEmpty())
			model.addAttribute("error", "Insira a foto da capa do livro");
		
		if(hasError)
			return "editorial/cadastro-livro";
		
		service.salvarLivro(estoque, fotoCapa);
		attr.addFlashAttribute("sucesso", "Livro salvo no estoque com sucesso!");

		return "redirect:/estoque/cadastro-livro";
	}
	
	@PostMapping("/editar-livro")
	public String editarLivro(@RequestParam MultipartFile fotoCapa, @Valid Estoque estoque, BindingResult result, RedirectAttributes attr) {
		if(result.hasErrors()) {
			return "estoque/cadastro-livro";
		}
		
		service.salvarLivro(estoque, fotoCapa);

		attr.addFlashAttribute("sucesso", "Edição feita com sucesso!");

		return "redirect:/estoque/detalhar-livro/" + estoque.getLivro().getId();
	}
	
	@GetMapping("/detalhar-livro/{id}")
	public String detalharLivro(@PathVariable Long id, @RequestParam("funcao") Optional<String> funcao, Model model) {
		String func = funcao.orElse("detalhar");
		
		Estoque estoque = service.obterLivroPorId(id);
		model.addAttribute("estoque", estoque);
		
		if(func.equals("editar"))
			return "editorial/cadastro-livro";
		
		return "editorial/detalhe-livro";
	}
	
	@GetMapping("/excluir-livro/{idEstoque}")
	public String excluirLivro(@PathVariable Long idEstoque, RedirectAttributes attr) {
		service.excluirLivro(idEstoque);
		
		attr.addFlashAttribute("sucesso", "Livro excluído do estoque com sucesso!");
		return "redirect:/estoque/listar";
	}
	
}
