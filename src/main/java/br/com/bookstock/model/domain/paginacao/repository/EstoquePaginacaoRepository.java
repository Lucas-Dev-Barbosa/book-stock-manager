package br.com.bookstock.model.domain.paginacao.repository;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.bookstock.model.domain.Estoque;

@Repository
public class EstoquePaginacaoRepository extends PaginacaoUtilRepository<Estoque> {

	@Override
	protected String getSqlOrder() {
		return "order by e.livro.titulo";
	}

	@Override
	protected String getSqlWhere() {
		return "where e.livro.titulo like :titulo";
	}

	@Override
	protected String getSqlFrom() {
		return "from Estoque e";
	}

	@Override
	protected List<String> getParams() {
		return Arrays.asList("titulo");
	}

}
