package br.com.bookstock.model.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.bookstock.model.domain.Usuario;
import br.com.bookstock.model.domain.repository.UsuarioRepository;
import br.com.bookstock.model.domain.repository.UsuarioRepositoryEm;
import br.com.bookstock.util.PaginacaoUtil;
import lombok.extern.java.Log;

@Log
@Service
public class UsuarioService implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private UsuarioRepositoryEm usuarioRepositoryEm;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("Carregando usuario " + username);
		
		Usuario usuario = repository.findByEmailAndAtivo(username).orElseThrow(() -> new UsernameNotFoundException("Usuario " + username + " nao encontrado."));
		
		return new User(usuario.getEmail(), usuario.getSenha(), AuthorityUtils.createAuthorityList(usuario.getPerfil().getNome()));
	}
	
	@Transactional(readOnly = false)
	public void cadastrarUsuario(Usuario usuario) {
		log.info("Cadastrando novo usuario. E-mail: " + usuario.getEmail());
		
		String crypt = new BCryptPasswordEncoder().encode(usuario.getSenha());
		usuario.setSenha(crypt);
		
		repository.save(usuario);
	}
	
	public PaginacaoUtil<Usuario> buscaUsuariosPorPaginacao(int pagina, String direcao) {
		return usuarioRepositoryEm.buscaUsuariosPorPaginacao(pagina, direcao);
	}

	public Usuario buscarUsuarioPorId(long id) {
		return repository.findById(id).orElse(null);
	}

}
