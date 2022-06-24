package br.com.bookstock.model.domain;

import lombok.Getter;

@Getter
public enum Perfis {

	ADMINISTRADOR(1, "ADMINISTRADOR"),
	OPERADOR(2, "OPERADOR");
	
	private long cod;
	private String descricao;
	
	private Perfis(long cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
}
