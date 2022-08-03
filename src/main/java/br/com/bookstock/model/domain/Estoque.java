package br.com.bookstock.model.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "t_estoque")
@SuppressWarnings("serial")
public class Estoque extends AbstractEntity {

	@Column(columnDefinition = "int default 0")
	private Integer emEstoque = 0;
	
	@Column(columnDefinition = "int default 0")
	private Integer vendidos = 0;
	
	@Valid
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_livro")
	private Livro livro;
	
}
