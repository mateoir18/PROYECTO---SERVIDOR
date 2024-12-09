package com.proyecto.compra;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

import com.proyecto.autor.Autor;
import com.proyecto.libro.Libro;
import com.proyecto.libro.LibroDao;
import com.proyecto.usuario.Usuario;
import com.proyecto.usuario.UsuarioDao;

@RestController

public class CompraRESTController {

	@Autowired
	CompraDao compraDao;

	@Autowired
	UsuarioDao usuarioDao;

	@Autowired
	LibroDao libroDao;

	@GetMapping("/compras")
	public ResponseEntity<List<Compra>> listarCompras() {
		// Obtiene todas las compras desde el DAO
		List<Compra> compras = (List<Compra>) compraDao.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(compras);
	}

	@GetMapping("/compras/{idusuario}/{idlibro}")
	public ResponseEntity<Compra> obtenerCompra(@PathVariable Long idusuario, @PathVariable Long idlibro) {
		// Crear un nuevo CompraId con los valores de cliente y libro
		CompraId id = new CompraId();
		id.setUsuarioId(idusuario);
		id.setLibroId(idlibro);

		// Buscar la compra en la base de datos usando el id compuesto
		Optional<Compra> compraOptional = compraDao.findById(id);
		if (compraOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(compraOptional.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@GetMapping("/compras/usuario/{idusuario}")
	public ResponseEntity<List<Compra>> listarComprasPorUsuario(@PathVariable Long idusuario) {
		// Busca el usuario en la base de datos
		Optional<Usuario> usuarioOptional = usuarioDao.findById(idusuario);
		if (usuarioOptional.isPresent()) {
			// Obtiene las compras del usuario
			List<Compra> compras = compraDao.findByUsuario(usuarioOptional.get());
			return ResponseEntity.status(HttpStatus.OK).body(compras);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@GetMapping("/compras/masvendidos")
	public ResponseEntity<List<Libro>> obtenerLibrosMasVendidos() {
		// Obtener todas las compras
		List<Compra> compras = (List<Compra>) compraDao.findAll();

		// Crear un mapa para contar las ventas de cada libro
		Map<Libro, Long> conteoVentas = compras.stream()
				.collect(Collectors.groupingBy(Compra::getLibro, Collectors.counting()));

		// Ordenar los libros por la cantidad de ventas en orden descendente
		List<Libro> librosMasVendidos = conteoVentas.entrySet().stream()
				.sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue())) // Orden descendente
				.map(Map.Entry::getKey) // Extraer los libros
				.collect(Collectors.toList());

		// Retornar la lista de libros ordenados
		return ResponseEntity.status(HttpStatus.OK).body(librosMasVendidos);
	}

	@PostMapping("/compras/add/{idusuario}/{idlibro}")
	public ResponseEntity<Compra> addCompra(@PathVariable Long idusuario, @PathVariable Long idlibro) {

		Optional<Usuario> usuarioOptional = usuarioDao.findById(idusuario);
		Optional<Libro> libroOptional = libroDao.findById(idlibro);

		if (usuarioOptional.isPresent() && libroOptional.isPresent()) {

			CompraId id = new CompraId();
			id.setUsuarioId(idusuario);
			id.setLibroId(idlibro);

			Compra compra = new Compra();
			compra.setId(id);
			compra.setCliente(usuarioOptional.get());
			compra.setLibro(libroOptional.get());
			compra.setFecha(compra.getFecha());

			compraDao.save(compra);

			return ResponseEntity.status(HttpStatus.OK).body(compra);

		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
	}

	@DeleteMapping("/compras/del/{idusuario}/{idlibro}")
	public ResponseEntity<Compra> delCompra(@PathVariable Long idusuario, @PathVariable Long idlibro) {

		CompraId id = new CompraId();
		id.setUsuarioId(idusuario);
		id.setLibroId(idlibro);

		Optional<Compra> compraOptional = compraDao.findById(id);
		if (compraOptional.isPresent()) {

			compraDao.delete(compraOptional.get());
			return ResponseEntity.status(HttpStatus.OK).body(compraOptional.get());
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

	}

}
