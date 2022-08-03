package br.com.bookstock.model.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "t_livro", uniqueConstraints = @UniqueConstraint(columnNames = { "isbn" }))
@SuppressWarnings("serial")
public class Livro extends AbstractEntity {

	@NotBlank(message = "Digite o título.")
	private String titulo;
	
	@NotBlank(message = "Digite a sinopse do livro.")
	@Column(columnDefinition = "TEXT")
	private String sinopse;
	
	@NotBlank(message = "Digite o autor.")
	private String autor;
	
	@NotBlank(message = "Digite o ISBN.")
	@Column(length = 20)
	private String isbn;
	
	@NotBlank(message = "Digite o nome da editora.")
	private String editora;
	
	@Column(columnDefinition = "LONGBLOB")
	private byte[] fotoCapa;
	
	@NotNull(message = "Informe o preço.")
	private BigDecimal preco;
	
	@NotNull(message = "Informe a data de publicação.")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dataPublicacao;
	
	@Min(value = 1, message = "O número de páginas tem que ser maior do que zero.")
	@NotNull(message = "Digite o número de páginas")
	private Integer numeroPaginas;
	
	@Transient
	private String fotoCapaBase64;
	
	public String getFotoCapaBase64() {
		if(this.fotoCapa != null && this.fotoCapa.length > 0) {
			return Base64.encodeBase64String(this.fotoCapa);
		} else {
			return this.fotoCapaBase64;
		}
	}
	
	public byte[] geraFotoCapaByteArrByBase64() {
		if(this.fotoCapa == null || this.fotoCapa.length == 0) {
			if(this.fotoCapaBase64 != null && !this.fotoCapaBase64.isEmpty())
				this.fotoCapa = Base64.decodeBase64(this.fotoCapaBase64);
		} 
		
		return this.fotoCapa;
	}
	
}
