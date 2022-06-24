package br.com.bookstock.model.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "t_perfil", uniqueConstraints = @UniqueConstraint(columnNames = {"nome"}))
@SuppressWarnings("serial")
public class Perfil extends AbstractEntity {

	private String nome;
	
	private String descricao;
	
	@OneToMany
	@JoinColumn(name = "id_perfil")
	private List<Usuario> usuario;
	
}
