package br.com.bookstock.model.domain.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.com.bookstock.model.domain.Usuario;
import br.com.bookstock.util.PaginacaoUtil;

@Repository
public class UsuarioRepositoryEm {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public PaginacaoUtil<Usuario> buscaUsuariosPorPaginacao(int pagina, String direcao){
		int qtdLinhas = 10;
		int inicio = (pagina - 1) * qtdLinhas;
		
		List<Usuario> usuarios = entityManager
				.createQuery("from Usuario u order by u.nome " + direcao, Usuario.class)
				.setFirstResult(inicio)
				.setMaxResults(qtdLinhas)
				.getResultList();
		
		long totalRegistros = usuarios.size();
		long totalPaginas = (totalRegistros + (qtdLinhas - 1)) / qtdLinhas;
		
		return new PaginacaoUtil<Usuario>(qtdLinhas, pagina, totalPaginas, direcao, usuarios);
	}

}
