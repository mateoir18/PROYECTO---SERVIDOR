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

import com.proyecto.dto.AuthResponseDTO;
import com.proyecto.dto.LoginRequestDTO;
import com.proyecto.servicios.LoginService;



@RestController

public class UsuarioRESTController {

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	UsuarioDao usuarioDao;
	
	@Autowired
	LoginService loginservice;

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
	public ResponseEntity<AuthResponseDTO> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario nuevoUsuario) {
	    return usuarioDao.findById(id).map(usuarioExistente -> {
	        usuarioExistente.setUsuario(nuevoUsuario.getUsuario());

	        // Cifrar la nueva contraseña si no está vacía
	        if (nuevoUsuario.getPassword() != null && !nuevoUsuario.getPassword().isEmpty()) {
	        	// Usa directamente bCryptPasswordEncoder
	        	String encryptedPassword = bCryptPasswordEncoder.encode(nuevoUsuario.getPassword());

	            usuarioExistente.setPassword(encryptedPassword);
	        }

	        usuarioDao.save(usuarioExistente);

	        // Generar nuevo token después de actualizar
	        LoginRequestDTO usuarioEdit = new LoginRequestDTO();
	        usuarioEdit.setUsuario(nuevoUsuario.getUsuario());
	        usuarioEdit.setPassword(nuevoUsuario.getPassword());
	        AuthResponseDTO authResponse = loginservice.autenticar(usuarioEdit);
	        return ResponseEntity.ok(authResponse);
	    }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
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
