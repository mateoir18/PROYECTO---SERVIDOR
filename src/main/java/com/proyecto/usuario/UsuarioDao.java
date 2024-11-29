package com.proyecto.usuario;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface UsuarioDao extends CrudRepository<Usuario, Long> {

	Optional<Usuario> findByUsuario(String usuario);

	

	


	

}
