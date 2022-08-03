package br.com.bookstock.model.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bookstock.model.domain.Perfil;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long>{

}
