package br.com.bookstock.model.domain.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.hibernate.QueryException;
import org.springframework.stereotype.Repository;

import br.com.bookstock.model.domain.Usuario;
import br.com.bookstock.util.PaginacaoUtil;

@Repository
public class UsuarioRepositoryEm {
	
	private final String SQL_FROM = "from Usuario u ";
	private final String SQL_WHERE = "where u.nome like :nome or u.sobrenome like :sobrenome or u.email like :email ";
	private final String SQL_ORDER = "order by u.nome";
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public PaginacaoUtil<Usuario> buscaUsuariosPorPaginacao(int pagina, String direcao, String busca){
		int qtdLinhas = 10;
		int inicio = (pagina - 1) * qtdLinhas;
		
		String sql = getSqlStringBuscaUsuarios(direcao, busca);
		
		TypedQuery<Usuario> query = getTypedQuery(sql, busca);
		
		List<Usuario> usuarios = query
				.setFirstResult(inicio)
				.setMaxResults(qtdLinhas)
				.getResultList();
		
		long totalRegistros = countRegistrosUsuarios(busca, SQL_WHERE);
		long totalPaginas = (totalRegistros + (qtdLinhas - 1)) / qtdLinhas;
		
		return new PaginacaoUtil<Usuario>(qtdLinhas, pagina, totalPaginas, direcao, usuarios, busca);
	}

	private int countRegistrosUsuarios(String busca, String sqlWhere) {
		String sql =  getSqlStringBuscaUsuarios("asc", busca);
		
		TypedQuery<Usuario> query = getTypedQuery(sql, busca);
		
		return query.getResultList().size();
	}
	
	private TypedQuery<Usuario> getTypedQuery(String sql, String busca) throws QueryException {
		if(sql != null && !sql.isEmpty()) {
			TypedQuery<Usuario> query = entityManager.createQuery(sql, Usuario.class);

			if(sql.contains("where")) {
				String buscaLike = "%" + busca + "%";
				
				query.setParameter("nome", buscaLike);
				query.setParameter("sobrenome", buscaLike);
				query.setParameter("email", buscaLike);
			}
			
			return query;
		}
		
		throw new QueryException("String sql não pode ser null ou vazia");
	}
	
	private String getSqlStringBuscaUsuarios(String direcao, String busca) {
		StringBuilder stringSql = new StringBuilder();

		stringSql.append(SQL_FROM);
		
		if (hasFiltroUsuarios(busca)) {
			stringSql.append(SQL_WHERE);
		}
		
		stringSql.append(SQL_ORDER + " " + direcao);
		
		return stringSql.toString();
	}
	
	private boolean hasFiltroUsuarios(String busca) {
		return busca != null && !busca.isEmpty();
	}

}
