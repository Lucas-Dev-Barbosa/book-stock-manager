package br.com.bookstock.model.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bookstock.model.domain.Perfil;
import br.com.bookstock.model.domain.repository.PerfilRepository;

@Service
public class PerfilService {

	@Autowired
	private PerfilRepository repository;
	
	public List<Perfil> listarPerfis(){
		return (List<Perfil>) repository.findAll();
	}
	
}
