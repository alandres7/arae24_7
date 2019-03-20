package co.gov.metropol.area247.repository.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import co.gov.metropol.area247.repository.domain.abstracts.Entities;

@Entity
@Table(name = "D247VIA_ZONA", schema = "MOVILIDAD")
public class DareviaZona extends Entities{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "ID_DAREVIA_ZONA")
	private Long idZona;
	
	@Column(name = "S_NOMBRE", nullable = false, length = 30)
	private String nombreZona;
	
	@Column(name = "S_DESCRIPCION", nullable = false, length = 50)
	private String descripcionZona;
	
	@Override
	public DareviaZona withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public DareviaZona withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}

	public Long getIdZona() {
		return idZona;
	}

	public void setIdZona(Long idZona) {
		this.idZona = idZona;
	}

	public String getNombreZona() {
		return nombreZona;
	}

	public void setNombreZona(String nombreZona) {
		this.nombreZona = nombreZona;
	}

	public String getDescripcionZona() {
		return descripcionZona;
	}

	public void setDescripcionZona(String descripcionZona) {
		this.descripcionZona = descripcionZona;
	}
	
	

}
