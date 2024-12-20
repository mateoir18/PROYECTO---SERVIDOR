package com.proyecto.libro;

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

import com.proyecto.autor.Autor;
import com.proyecto.autor.AutorDao;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class LibroRESTController {

    @Autowired
    LibroDao LibroDao;
    @Autowired
    AutorDao autorDao;

    @GetMapping("/libros")
    public ResponseEntity<List<Libro>> listarLibros() {
        return ResponseEntity.status(HttpStatus.OK).body((List<Libro>) LibroDao.findAll());
    }

    @GetMapping("/libro/{id}")
    public ResponseEntity<Libro> obtenerLibro(@PathVariable Long id) {
    	System.out.println("llegamos al libro");
        Optional<Libro> LibroOptional = LibroDao.findById(id);
        if (LibroOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(LibroOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    
    
    @GetMapping("libros/autor/{id}")
    
    public ResponseEntity<List<Libro>> obtenerLibrosDeAutor(@PathVariable Long id) {
    	
        // Busca el autor por su ID para verificar que existe
        Optional<Autor> autorOptional = autorDao.findById(id);
        
        
        if (autorOptional.isPresent()) {
            // Si el autor existe, busca todos los libros asociados a ese autor
            List<Libro> libros = LibroDao.findByAutorId(id);
            return ResponseEntity.status(HttpStatus.OK).body(libros);
        } else {
            // Si el autor no existe, retorna un 404 Not Found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    

    @PostMapping("/libros/add")
    public ResponseEntity<Libro> agregarLibro(@RequestBody Libro libro) {
        return ResponseEntity.status(HttpStatus.OK).body(LibroDao.save(libro));
    }

    @PutMapping("/libros/edit/{id}")
    public ResponseEntity<Libro> actualizarLibro(@PathVariable Long id, @RequestBody Libro nuevoLibro) {
        Optional<Libro> optionalLibro = LibroDao.findById(id);
        if (optionalLibro.isPresent()) {
            Libro libroExistente = optionalLibro.get();
            libroExistente.setTitulo(nuevoLibro.getTitulo());
            libroExistente.setPrecio(nuevoLibro.getPrecio());

            // Buscar al autor existente por su ID
            Optional<Autor> optionalAutor = autorDao.findById(nuevoLibro.getAutor().getId()); 
            if (optionalAutor.isPresent()) {
                libroExistente.setAutor(optionalAutor.get());
            } else {
                // Manejar el caso donde el autor no se encuentra
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            return ResponseEntity.status(HttpStatus.OK).body(LibroDao.save(libroExistente));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @DeleteMapping("/libro/del/{id}")
    public ResponseEntity<Libro> eliminarLibro(@PathVariable Long id) {
        Optional<Libro> optionalLibro = LibroDao.findById(id);
        if (optionalLibro.isPresent()) {
            LibroDao.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(optionalLibro.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
