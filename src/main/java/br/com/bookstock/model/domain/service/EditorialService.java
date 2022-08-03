package br.com.bookstock.model.domain.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.com.bookstock.model.domain.Estoque;
import br.com.bookstock.model.domain.paginacao.repository.EstoquePaginacaoRepository;
import br.com.bookstock.model.domain.repository.EstoqueRepository;
import br.com.bookstock.util.PaginacaoUtil;
import lombok.extern.java.Log;

@Log
@Service
public class EditorialService {

	@Autowired
	private EstoqueRepository repository;
	
	@Autowired
	private EstoquePaginacaoRepository paginacaoRepository;
	
	@Transactional(readOnly = false)
	public void salvarLivro(Estoque estoque, MultipartFile fotoCapa) {
		log.info("Salvando informacoes do livro [" + estoque.getLivro().getTitulo() + "] no estoque");
		
		try {
			estoque.getLivro().setFotoCapa(fotoCapa.getBytes());
		} catch (IOException e) {
			log.severe(e.getMessage());
		}
		
		if(estoque.getId() != null) {
			processaAtualizacao(estoque);
		}
		
		repository.save(estoque);
	}
	
	private void processaAtualizacao(Estoque estoque) {
		estoque.getLivro().geraFotoCapaByteArrByBase64();
	}

	public PaginacaoUtil<Estoque> buscarLivrosCadastradosPorPaginacao(int paginaAtual, String ordem, String busca) {
		return paginacaoRepository.buscaPorPaginacao(paginaAtual, ordem, busca, 15);
	}

	public Estoque obterLivroPorId(Long id) {
		return repository.findLivroById(id);
	}

	public void excluirLivro(Long idEstoque) {
		repository.deleteById(idEstoque);
	}
	
}
