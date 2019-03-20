package co.gov.metropol.area247.repository.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import co.gov.metropol.area247.repository.domain.abstracts.Entities;


@Entity
@Table(name = "D247VIA_TIPO_SISTEMA_RUTA", schema = "MOVILIDAD")
public class TipoSistemaRuta extends Entities {

	private static final long serialVersionUID = 8699029723619959674L;

	@Column(name = "S_NOMBRE")
	private String nombre;

	@Column(name = "S_DESCRIPCION")
	private String descripcion;
	
	@Column(name = "N_ID_ITEM")
	private Long idItem;
	
	@Column(name = "N_FUENTE_DATOS")
	private int fuenteDatos;

	@Override
	public TipoSistemaRuta withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public TipoSistemaRuta withEnabled(boolean enabled) {
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

	public Long getIdItem() {
		return idItem;
	}

	public void setIdItem(Long idItem) {
		this.idItem = idItem;
	}

	public int getFuenteDatos() {
		return fuenteDatos;
	}

	public void setFuenteDatos(int fuenteDatos) {
		this.fuenteDatos = fuenteDatos;
	}

}