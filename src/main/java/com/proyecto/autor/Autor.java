package com.proyecto.autor;

import java.util.HashSet;
import java.util.Set;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.proyecto.biografia.Biografia;
import com.proyecto.libro.Libro;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Autor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincremental
	private Long id;
	private String nombre;
	private String apellido;

	@OneToMany(mappedBy = "autor", targetEntity = Libro.class, cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private Set<Libro> libro = new HashSet<>();
	
	
	@OneToOne(mappedBy = "autor", targetEntity = Biografia.class, cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private Biografia biografia;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @return the apellido
	 */
	public String getApellido() {
		return apellido;
	}

	/**
	 * @return the libro
	 */
	public Set<Libro> getLibro() {
		return libro;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @param apellido the apellido to set
	 */
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	/**
	 * @param libro the libro to set
	 */
	public void setLibro(Set<Libro> libro) {
		this.libro = libro;
	}

	/**
	 * @return the biografia
	 */
	public Biografia getBiografia() {
		return biografia;
	}

	/**
	 * @param biografia the biografia to set
	 */
	public void setBiografia(Biografia biografia) {
		this.biografia = biografia;
	}
	
	

}
