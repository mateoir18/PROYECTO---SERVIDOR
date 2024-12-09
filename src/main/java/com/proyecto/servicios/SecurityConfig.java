package com.proyecto.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.proyecto.filter.JwtAuthenticationFilter;

@Configuration // Indica que esta es una clase de configuración de Spring
@EnableWebSecurity // Habilita la seguridad web en Spring
@EnableMethodSecurity // Habilita la seguridad basada en métodos
public class SecurityConfig {

	@Autowired
	JwtAuthenticationFilter jwtAuthenticationFilter; // Inyección de dependencia para el filtro de autenticación JWT

	@Autowired
	AuthenticationProvider authProvider; // Inyección de dependencia para el proveedor de autenticación
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http
	        .csrf().disable() // Deshabilitar CSRF para pruebas
	        .cors().and() // Habilitar CORS
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/", "/autores", "/autor/{id}", "/libros", "/libro/{id}",
	                             "/libros/autor/{id}", "/biografias", "/biografia/{id}", "/auth/*", "/compras/masvendidos")
	                .permitAll()
	            .requestMatchers("/usuarios/{id}","/compras/usuario/{idusuario}",
	                             "/compras/add/{idusuario}/{idlibro}", "/usuarios/nombre/{username}")
	                .authenticated()
	                .requestMatchers("/usuarios/edit/{id}").hasAnyAuthority("ADMIN", "USER")


	            .requestMatchers("/autores/add", "/autores/edit/{id}", "/autores/del/{id}", "/libros/add",
	                             "/libros/edit/{id}", "/libro/del/{id}", "/biografias/add",
	                             "/biografia/edit/{id}", "/biografia/del/{id}", "/usuarios/del/{id}",
	                             "/compras", "/compras/{idusuario}/{idlibro}", "/compras/del/{idusuario}/{idlibro}", "/compras/edit/{usuarioId}/{libroId}" , "/usuarios")
	                .hasAuthority("ADMIN")
	        )
	        
	        .sessionManagement(sessionManager -> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Configura la política de sesiones como stateless
			.authenticationProvider(authProvider) // Configura el proveedor de autenticación
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // Añade el filtro JWT antes del filtro de autenticación de usuario y contraseña
			.logout(logout -> logout.permitAll()); // Permite el logout para todos
	return http.build(); // Construye y devuelve la cadena de filtros de seguridad
	        

	    
	}





	@Bean // Define el bean para la configuración de CORS
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**") // Permite CORS para todas las rutas
						.allowedOriginPatterns("*") // Permite todas las procedencias
						.allowedMethods("*") // Permite todos los métodos HTTP
						.allowCredentials(true) // Permite el uso de credenciales
						.allowedHeaders("Authorization", "Content-Type"); // Permite estos encabezados
			}
		};
	}

}