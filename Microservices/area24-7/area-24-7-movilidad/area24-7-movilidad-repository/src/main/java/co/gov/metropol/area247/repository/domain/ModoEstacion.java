package co.gov.metropol.area247.repository.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import co.gov.metropol.area247.repository.domain.abstracts.Entities;



@Entity
@Table(name = "D247VIA_MODO_ESTACION", schema = "MOVILIDAD")
public class ModoEstacion extends Entities {

	private static final long serialVersionUID = 4685595686438771311L;

	@Column(name = "S_NOMBRE")
	private String nombre;

	@Column(name = "S_DESCRIPCION")
	private String descripcion;

	@Override
	public ModoEstacion withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public ModoEstacion withEnabled(boolean enabled) {
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
