package br.com.bookstock.model.domain.paginacao.repository;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.bookstock.model.domain.Usuario;

@Repository
public class UsuarioPaginacaoRepository extends PaginacaoUtilRepository<Usuario> {
	
	@Override
	protected String getSqlOrder() {
		return "order by u.nome";
	}

	@Override
	protected String getSqlWhere() {
		return "where u.nome like :nome or u.sobrenome like :sobrenome or u.email like :email";
	}

	@Override
	protected String getSqlFrom() {
		return "from Usuario u";
	}
	
	@Override
	protected List<String> getParams() {
		return Arrays.asList("nome", "sobrenome", "email");
	}

}
