package com.proyecto.usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController

public class UsuarioRESTController {

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	UsuarioDao usuarioDao;

	@GetMapping("/usuarios")
	public ResponseEntity<List<Usuario>> listarUsuarios() {
		return ResponseEntity.status(HttpStatus.OK).body((List<Usuario>) usuarioDao.findAll());
	}

	@GetMapping("/usuarios/{id}")
	public ResponseEntity<Usuario> obtenerUsuario(@PathVariable Long id) {
		Optional<Usuario> UsuarioOptional = usuarioDao.findById(id);
		if (UsuarioOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(UsuarioOptional.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
	
	@GetMapping("/usuarios/nombre/{username}")
	public ResponseEntity<Usuario> obtenerIdUsuario(@PathVariable String username) {
		System.out.println("llegamos");
		Optional<Usuario> UsuarioOptional = usuarioDao.findByUsuario(username);
		if (UsuarioOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(UsuarioOptional.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@PostMapping("/usuarios/add")
	public ResponseEntity<Usuario> agregarUsuario(@RequestBody Usuario usuario) {
//	    usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword())); // Encriptar contraseña
	    return ResponseEntity.status(HttpStatus.OK).body(usuarioDao.save(usuario));
	}

	@PutMapping("/usuarios/edit/{id}")
	public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario nuevoUsuario) {
		Optional<Usuario> optionalUsuario = usuarioDao.findById(id);
		if (optionalUsuario.isPresent()) {
			Usuario usuarioExistente = optionalUsuario.get();
			usuarioExistente.setUsuario(nuevoUsuario.getUsuario());
			usuarioExistente.setPassword(nuevoUsuario.getPassword());
			return ResponseEntity.status(HttpStatus.OK).body(usuarioDao.save(usuarioExistente));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@DeleteMapping("/usuarios/del/{id}")
	public ResponseEntity<Usuario> eliminarUsuario(@PathVariable Long id) {
		Optional<Usuario> optionalUsuario = usuarioDao.findById(id);
		if (optionalUsuario.isPresent()) {
			usuarioDao.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).body(optionalUsuario.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}

	}
}
