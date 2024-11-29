package com.proyecto.servicios;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.proyecto.usuario.Usuario;
import com.proyecto.usuario.UsuarioDao;



@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsuarioDao usuarioDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    Optional<Usuario> usuario = usuarioDao.findByUsuario(username);
	    if (usuario.isPresent()) {
	        return usuario.get();
	    }
	    throw new UsernameNotFoundException(username);
	}

}