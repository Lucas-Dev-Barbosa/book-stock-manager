package br.com.bookstock.util;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaginacaoUtil<T> {

	private int qtdLinhas;
	
	private int numPagina;
	
	private long totalPaginas;
	
	private String direcao;
	
	private List<T> registros;
	
}
