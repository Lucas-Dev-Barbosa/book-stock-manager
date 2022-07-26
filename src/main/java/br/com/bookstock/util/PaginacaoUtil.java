package br.com.bookstock.util;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class PaginacaoUtil<T> {

	private int qtdLinhas;
	
	private int numPagina;
	
	private long totalPaginas;
	
	private String direcao;
	
	private List<T> registros;
	
	private List<Integer> arrPaginas;
	
	private String filtro;

	public PaginacaoUtil(int qtdLinhas, int numPagina, long totalPaginas, String direcao, List<T> registros, String filtro) {
		this.qtdLinhas = qtdLinhas;
		this.numPagina = numPagina;
		this.totalPaginas = totalPaginas;
		this.direcao = direcao;
		this.registros = registros;
		this.filtro = filtro;
		
		arrPaginas = new ArrayList<>();
		for(int i = 1; i <= totalPaginas; i++) {
			arrPaginas.add(i);
		}
	}
	
	
	
}
