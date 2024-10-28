package com.proyecto.compra;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyecto.libro.Libro;
import com.proyecto.usuario.Usuario;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

@Entity
public class Compra {

	@EmbeddedId
	private CompraId id;

	@ManyToOne
	@MapsId("usuarioId")
	@JoinColumn(name = "usuario_id")
	@JsonManagedReference
	private Usuario usuario;

	@ManyToOne
	@MapsId("libroId")
	@JoinColumn(name = "libro_id")
	@JsonManagedReference
	private Libro libro;

	private String fecha;
	

	// Getters y Setters

	/**
	 * @return the id
	 */
	public CompraId getId() {
		return id;
	}

	/**
	 * @return the cliente
	 */
	public Usuario getCliente() {
		return usuario;
	}

	/**
	 * @return the libro
	 */
	public Libro getLibro() {
		return libro;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(CompraId id) {
		this.id = id;
	}

	/**
	 * @param cliente the cliente to set
	 */
	public void setCliente(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * @param libro the libro to set
	 */
	public void setLibro(Libro libro) {
		this.libro = libro;
	}

	/**
	 * @return the fecha
	 */
	public String getFecha() {
		return fecha;
	}

	

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	

}
