package br.com.bookstock.model.domain.paginacao.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.hibernate.QueryException;

import br.com.bookstock.util.PaginacaoUtil;

public abstract class PaginacaoUtilRepository<T> {
	
	protected abstract String getSqlOrder();

	protected abstract String getSqlWhere();

	protected abstract String getSqlFrom();
	
	protected abstract List<String> getParams();
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public PaginacaoUtil<T> buscaPorPaginacao(int pagina, String direcao, String busca, int qtdLinhas){
		int inicio = (pagina - 1) * qtdLinhas;
		
		String sql = getSqlString(direcao, busca);
		
		TypedQuery<T> query = getTypedQuery(sql, busca);
		
		List<T> registros = query
				.setFirstResult(inicio)
				.setMaxResults(qtdLinhas)
				.getResultList();
		
		long totalRegistros = countRegistros(busca);
		long totalPaginas = (totalRegistros + (qtdLinhas - 1)) / qtdLinhas;
		
		return new PaginacaoUtil<T>(qtdLinhas, pagina, totalPaginas, direcao, registros, busca);
	}

	private int countRegistros(String busca) {
		String sql =  getSqlString("asc", busca);
		
		TypedQuery<T> query = getTypedQuery(sql, busca);
		
		return query.getResultList().size();
	}
	
	@SuppressWarnings("unchecked")
	private TypedQuery<T> getTypedQuery(String sql, String busca) throws QueryException {
		if(sql != null && !sql.isEmpty()) {
			
			TypedQuery<T> query = (TypedQuery<T>) entityManager.createQuery(sql);

			if(sql.contains("where")) {
				String buscaLike = "%" + busca + "%";
				
				List<String> params = getParams();
				
				params.forEach((param) -> {
					query.setParameter(param, buscaLike);
				});
			}
			
			return query;
		}
		
		throw new QueryException("String sql não pode ser null ou vazia");
	}
	
	private String getSqlString(String direcao, String busca) {
		StringBuilder stringSql = new StringBuilder();

		stringSql.append(getSqlFrom());
		stringSql.append(" ");
		
		if (hasFiltro(busca)) {
			stringSql.append(getSqlWhere());
			stringSql.append(" ");
		}
		
		stringSql.append(getSqlOrder() + " " + direcao);
		
		return stringSql.toString();
	}

	private boolean hasFiltro(String busca) {
		return busca != null && !busca.isEmpty();
	}

}
