package br.com.bookstock.model.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.bookstock.model.domain.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByEmail(String email);

	@Query("from Usuario u where u.email = :email and u.ativo = true")
	Optional<Usuario> findByEmailAndAtivo(String email);
	
}
