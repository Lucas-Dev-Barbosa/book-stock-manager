package br.com.bookstock.security;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import br.com.bookstock.model.domain.Perfis;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	private static final String ADMINISTRATOR = Perfis.ADMINISTRADOR.getDescricao();
	private static final String OPERADOR = Perfis.OPERADOR.getDescricao();
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
		
		.antMatchers("/webjars/**", "/css/**", "/image/**", "/js/**")
		.permitAll()
		
		.antMatchers("/usuario/cadastro", "/usuario/cadastrar", "/usuario/listar", "/usuario/pre-editar/*", "/usuario/editar")
		.hasAnyAuthority(ADMINISTRATOR)
		
		.antMatchers("/")
		.hasAnyAuthority(ADMINISTRATOR, OPERADOR)
		
		.anyRequest()
		.authenticated()
		
		.and()
		
		.formLogin()
		.loginPage("/login")
		.defaultSuccessUrl("/", true)
		.failureUrl("/login-error")
		.permitAll()
		
		.and()
		
		.logout()
		.logoutSuccessUrl("/login");
		
		
		
		http.sessionManagement()
		.maximumSessions(1)
		.expiredUrl("/login")
		.maxSessionsPreventsLogin(false)
		.sessionRegistry(sessionRegistry());
		
		return http.build();
	}
	
	@Bean
	public PasswordEncoder encoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}
	
	@Bean
	public ServletListenerRegistrationBean<?> servletListenerRegistrationBean() {
		return new ServletListenerRegistrationBean<>(new HttpSessionEventPublisher());
	}
	
}
