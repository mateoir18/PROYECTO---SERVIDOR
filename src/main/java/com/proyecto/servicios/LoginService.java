package com.proyecto.servicios;





import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.proyecto.dto.AuthResponseDTO;
import com.proyecto.dto.LoginRequestDTO;
import com.proyecto.usuario.Usuario;
import com.proyecto.usuario.UsuarioDao;

import java.util.Optional;

@Service // Marca esta clase como un servicio de Spring
public class LoginService {

   

    @Autowired
    AuthenticationManager authManager; // Gestor de autenticación de Spring Security

    @Autowired
    UsuarioDao usuarioDAO; // DAO para acceder y gestionar usuarios en la base de datos

    @Autowired
    JwtService jwtService; // Servicio para gestionar operaciones relacionadas con JWT

    // Método para autenticar un usuario
    public AuthResponseDTO autenticar(LoginRequestDTO request) throws DisabledException, BadCredentialsException {

        // Autentica el usuario con el nombre de usuario y la contraseña proporcionados
        authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsuario(), request.getPassword()));

        // Busca el usuario en la base de datos por nombre de usuario
        Optional<Usuario> user = usuarioDAO.findByUsuario(request.getUsuario());

        // Genera un token JWT para el usuario autenticado
        String token = jwtService.getToken((UserDetails) user.get());

        // Devuelve una respuesta de autenticación con el token generado
        return AuthResponseDTO.builder().token(token).build();
    }
}
