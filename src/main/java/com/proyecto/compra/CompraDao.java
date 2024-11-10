 package com.proyecto.compra;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.proyecto.usuario.Usuario;

public interface CompraDao extends CrudRepository<Compra, CompraId> {

	List<Compra> findByUsuarioId(Long idusuario);

	List<Compra> findByUsuario(Usuario usuario);

}
