package org.generation.blogPessoal1.repository;

import java.util.Optional;

import org.generation.blogPessoal1.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>
{
	public Optional<Usuario> findByUsuario(String usuario);
}
