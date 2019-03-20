package co.gov.metropol.area247.repository.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import co.gov.metropol.area247.repository.domain.abstracts.Entities;


@Entity
@Table(name = "D247VIA_TIPO_ESTADO_REGISTRO", schema = "MOVILIDAD")
public class TipoEstadoRegistro extends Entities {
	
	private static final long serialVersionUID = -7936661497475574643L;

	@Column(name = "S_NOMBRE")
	private String nombre;

	@Column(name = "S_DESCRIPCION")
	private String descripcion;

	@Override
	public TipoEstadoRegistro withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public TipoEstadoRegistro withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
