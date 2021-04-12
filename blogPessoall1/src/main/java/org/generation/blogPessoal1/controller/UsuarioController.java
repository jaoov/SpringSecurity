package org.generation.blogPessoal1.controller;

import java.util.Optional;

import org.generation.blogPessoal1.model.UserLogin;
import org.generation.blogPessoal1.model.Usuario;
import org.generation.blogPessoal1.repository.UsuarioRepository;
import org.generation.blogPessoal1.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController 
{
	@Autowired
	private UsuarioService usuarioService;
	
	@PostMapping
	public ResponseEntity<UserLogin> Autentication(@RequestBody Optional<UserLogin> user)
	{
		return usuarioService.Logar(user).map(resp -> ResponseEntity.ok(resp))
							.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
		
	}
	@PostMapping("/cadastrar")
	public ResponseEntity<Optional<Usuario>>Post(@RequestBody Usuario usuario)
	{
		return ResponseEntity.status(HttpStatus.CREATED)
					.body(usuarioService.CadastrarUsuario(usuario));
	}
	
}