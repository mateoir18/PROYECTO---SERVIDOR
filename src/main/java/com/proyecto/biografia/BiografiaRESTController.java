package com.proyecto.biografia;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/biografias")
public class BiografiaRESTController {

	@Autowired
	BiografiaDao BiografiaDao;

	@GetMapping
	public ResponseEntity<List<Biografia>> listarBiografias() {
		return ResponseEntity.status(HttpStatus.OK).body((List<Biografia>) BiografiaDao.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Biografia> obtenerBiografia(@PathVariable Long id) {
		Optional<Biografia> biografiaOptional = BiografiaDao.findById(id);
		if (biografiaOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(biografiaOptional.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@PostMapping
	public ResponseEntity<Biografia> agregarBiografia(@RequestBody Biografia biografia) {
		return ResponseEntity.status(HttpStatus.OK).body(BiografiaDao.save(biografia));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Biografia> actualizarBiografia(@PathVariable Long id, @RequestBody Biografia nuevaBiografia) {
		Optional<Biografia> optionalBiografia = BiografiaDao.findById(id);
		if (optionalBiografia.isPresent()) {
			Biografia biografiaExistente = optionalBiografia.get();
			biografiaExistente.setAutor(nuevaBiografia.getAutor());
			biografiaExistente.setNacionalidad(nuevaBiografia.getNacionalidad());
			biografiaExistente.setFecha_nac(nuevaBiografia.getFecha_nac());
			biografiaExistente.setPremios(nuevaBiografia.getPremios());
			biografiaExistente.setObras_destacadas(nuevaBiografia.getObras_destacadas());

			return ResponseEntity.status(HttpStatus.OK).body(BiografiaDao.save(biografiaExistente));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@DeleteMapping("/del/{id}")
	public ResponseEntity<Biografia> eliminarBiografia(@PathVariable Long id) {
		Optional<Biografia> optionalBiografia = BiografiaDao.findById(id);
		if (optionalBiografia.isPresent()) {
			BiografiaDao.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).body(optionalBiografia.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
}
