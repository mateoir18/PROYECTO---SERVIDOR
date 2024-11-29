package com.proyecto.autor;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class AutorRESTController {

	@Autowired
	private AutorDao AutorDao;
	
	
	@GetMapping("/")
	
	public void inicio() {
	
	}

	@GetMapping("/autores")

	public ResponseEntity<List<Autor>> listarAutores() {
		return ResponseEntity.status(HttpStatus.OK).body((List<Autor>) AutorDao.findAll());
	}

	@GetMapping("/autor/{id}")

	public ResponseEntity<Autor> obtenerAutor(@PathVariable Long id) {

		Optional<Autor> autorOptional = AutorDao.findById(id);

		if (autorOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(autorOptional.get());
		} else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}

	@PostMapping("/autores/add")

	public ResponseEntity<Autor> agregarAutor(@RequestBody Autor autor) {
		return ResponseEntity.status(HttpStatus.OK).body(AutorDao.save(autor));
	}

	@PutMapping("/autores/edit/{id}")

	public ResponseEntity<Autor> actualizarAutor(@PathVariable Long id, @RequestBody Autor nuevoAutor) {

		Optional<Autor> optionalAutor = AutorDao.findById(id);
		if (optionalAutor.isPresent()) {

			Autor autorExistente = optionalAutor.get();
			autorExistente.setNombre(nuevoAutor.getNombre());
			autorExistente.setApellido(nuevoAutor.getApellido());
			return ResponseEntity.status(HttpStatus.OK).body(AutorDao.save(autorExistente));
		} else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}

	@DeleteMapping("/autores/del/{id}")

	public ResponseEntity<Autor> eliminarAutor(@PathVariable Long id) {

		Optional<Autor> optionalAutor = AutorDao.findById(id);
		if (optionalAutor.isPresent()) {
			AutorDao.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).body(optionalAutor.get());
		} else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}

}
