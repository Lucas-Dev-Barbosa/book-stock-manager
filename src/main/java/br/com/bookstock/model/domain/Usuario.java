package br.com.bookstock.model.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.ColumnDefault;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@Entity
@Table(name = "t_usuario", uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
public class Usuario extends AbstractEntity {

	@NotBlank(message = "Digite o nome.")
	private String nome;
	
	@NotBlank(message = "Digite o sobrenome.")
	private String sobrenome;
	
	@NotBlank(message = "Digite o e-mail.")
	private String email;
	
	@NotBlank(message = "Digite a senha.")
	private String senha;
	
	@ColumnDefault(value = "false")
	private Boolean ativo;
	
	@ManyToOne
	@JoinColumn(name = "id_perfil")
	private Perfil perfil;
	
}
