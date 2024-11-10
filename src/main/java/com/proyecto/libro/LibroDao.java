package com.proyecto.libro;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface LibroDao extends CrudRepository<Libro, Long> {

	List<Libro> findByAutorId(Long autorId);

}
