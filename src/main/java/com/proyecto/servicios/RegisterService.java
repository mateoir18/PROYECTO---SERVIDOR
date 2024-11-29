package com.proyecto.servicios;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.proyecto.dto.RegisterRequestDTO;

@Service // Marca esta clase como un servicio de Spring
public class RegisterService {

	@Autowired
	AuthService authService; // Servicio de autenticación para manejar el registro de usuarios

	// Método para manejar el registro de un usuario
	public ResponseEntity<?> manejarRegistro(RegisterRequestDTO request, BindingResult bindingResult) {

		// Verifica si hay errores de validación en el formulario de registro

		authService.register(request, bindingResult); // Intenta registrar al usuario usando AuthService

		return ResponseEntity.status(HttpStatus.OK).body("Usuario registrado con éxito"); // Devuelve una respuesta HTTP
																							// con el mensaje de éxito

	}
}
