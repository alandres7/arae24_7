package co.gov.metropol.area247.repository.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import co.gov.metropol.area247.repository.domain.abstracts.Entities;

@Entity
@Table(name = "D247VIA_TIPO_LINEA", schema = "MOVILIDAD")
public class TipoLinea extends Entities {

	private static final long serialVersionUID = 3523540158457370204L;

	@Column(name = "N_ID_ITEM")
	private Long idTipoLinea;

	@Column(name = "S_NOMBRE")
	private String nombre;

	@Column(name = "S_DESCRIPCION")
	private String descripcion;

	@Override
	public TipoLinea withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public TipoLinea withEnabled(boolean enabled) {
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

	public Long getIdTipoLinea() {
		return idTipoLinea;
	}

	public void setIdTipoLinea(Long idTipoLinea) {
		this.idTipoLinea = idTipoLinea;
	}

}
