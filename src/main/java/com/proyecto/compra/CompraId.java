package com.proyecto.compra;

import java.io.Serializable;

import jakarta.persistence.Column;

public class CompraId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "libro_id")
    private Long libroId;
    
    

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the clienteId
	 */
	public Long getUsuarioId() {
		return usuarioId;
	}

	/**
	 * @return the libroId
	 */
	public Long getLibroId() {
		return libroId;
	}

	/**
	 * @param clienteId the clienteId to set
	 */
	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	/**
	 * @param libroId the libroId to set
	 */
	public void setLibroId(Long libroId) {
		this.libroId = libroId;
	}
    
    
    
}
