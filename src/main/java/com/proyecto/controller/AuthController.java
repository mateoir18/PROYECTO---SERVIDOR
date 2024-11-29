package com.proyecto.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.proyecto.dto.LoginRequestDTO;
import com.proyecto.dto.RegisterRequestDTO;
import com.proyecto.servicios.AuthService;
import com.proyecto.servicios.RegisterService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	AuthService authService;

	@Autowired
	RegisterService registerService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {

		return authService.login(request);

	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequestDTO request, BindingResult bindingResult) {

		return registerService.manejarRegistro(request, bindingResult);

	}

}