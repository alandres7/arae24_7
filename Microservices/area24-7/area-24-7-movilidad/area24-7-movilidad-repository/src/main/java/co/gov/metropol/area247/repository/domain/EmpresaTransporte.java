package co.gov.metropol.area247.repository.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import co.gov.metropol.area247.repository.domain.abstracts.Entities;

@Entity
@Table(name = "T247VIA_EMPRESA_TRANSPORTE", schema = "MOVILIDAD")
public class EmpresaTransporte extends Entities{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "N_ID_ITEM")
	private Long idItem;
	
	@Column(name = "S_NOMBRE")
	private String nombre;
	
	@Column(name = "S_ACTIVO")
	private String activo;
	
	public Long getIdItem() {
		return idItem;
	}

	public void setIdItem(Long idItem) {
		this.idItem = idItem;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getActivo() {
		return activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
	}

	@Override
	public EmpresaTransporte withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public EmpresaTransporte withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}

}
