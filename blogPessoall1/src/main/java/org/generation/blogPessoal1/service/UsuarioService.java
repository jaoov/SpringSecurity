package org.generation.blogPessoal1.service;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.generation.blogPessoal1.model.UserLogin;
import org.generation.blogPessoal1.model.Usuario;
import org.generation.blogPessoal1.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService 
{
	@Autowired
	private UsuarioRepository repository;
	
	public Optional<Usuario> CadastrarUsuario(Usuario usuario) {
		
		if (repository.findByUsuario(usuario.getUsuario()).isPresent()) {
			return null;
		}
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		String senhaCriptografada = encoder.encode(usuario.getSenha());
		usuario.setSenha(senhaCriptografada);
		
		return Optional.of(repository.save(usuario));
	}
	
	public Optional<UserLogin> Logar(Optional<UserLogin> usuarioLogin) {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		Optional<Usuario> usuario = repository.findByUsuario(usuarioLogin.get().getUsuario());
		
		if(usuario.isPresent()) {
			if(encoder.matches(usuarioLogin.get().getSenha(), usuario.get().getSenha())) {
				
				String auth = usuarioLogin.get().getUsuario() + ":" + usuarioLogin.get().getSenha();
				byte[] encodeAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "Basic " + new String(encodeAuth);
				
				usuarioLogin.get().setToken(authHeader);
				usuarioLogin.get().setUsuario(usuario.get().getUsuario());
				return usuarioLogin;
				
			}
		}
		
		return null;
	}
	
}