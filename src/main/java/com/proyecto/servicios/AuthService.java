package com.proyecto.servicios;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.proyecto.dto.LoginRequestDTO;
import com.proyecto.dto.RegisterRequestDTO;
import com.proyecto.usuario.Usuario;
import com.proyecto.usuario.UsuarioDao;

@Service // Marca esta clase como un servicio de Spring
public class AuthService {

	@Autowired
	UsuarioDao usuarioDAO; // DAO para acceder y gestionar usuarios en la base de datos

	@Autowired
	JwtService jwtService; // Servicio para gestionar operaciones relacionadas con JWT

	@Autowired
	AuthenticationManager authManager; // Gestor de autenticación de Spring Security

	@Autowired
	PasswordEncoder bcryptPasswordEncoder; // Codificador de contraseñas para encriptar y verificar contraseñas

	@Autowired
	LoginService loginService; // Servicio para gestionar el proceso de autenticación

	// Método para registrar un nuevo usuario
	public void register(RegisterRequestDTO request, BindingResult bindingResult) {

		Usuario user = new Usuario(); // Crea una nueva instancia de Usuario

		// Establece los detalles del nuevo usuario

		user.setPassword(bcryptPasswordEncoder.encode(request.getPassword())); // Encripta la contraseña
		user.setUsuario(request.getUsuario());
		user.setRol("USER"); //

		// Guarda el nuevo usuario en la base de datos
		usuarioDAO.save(user);
	}

	// Método para iniciar sesión
	public ResponseEntity<?> login(LoginRequestDTO request) {

		// Llama al servicio de autenticación y devuelve una respuesta HTTP con el
		// resultado
		return ResponseEntity.status(HttpStatus.OK).body(loginService.autenticar(request));
	}
}