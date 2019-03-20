package co.gov.metropol.area247.repository.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import co.gov.metropol.area247.repository.domain.abstracts.Entities;

@Entity
@Table(name = "T247VIA_OPERADOR", schema = "MOVILIDAD")
public class Operador extends Entities{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "N_ID_ITEM")
	private Long idItem;
	
	@Column(name = "ID_TIPO_SISTEMA_RUTA")
	private Long idSistemaRuta;
	
	@Column(name = "S_ACTIVO")
	private String activo;
	
	@Override
	public Operador withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public Operador withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}

	public Long getIdItem() {
		return idItem;
	}

	public void setIdItem(Long idItem) {
		this.idItem = idItem;
	}

	public Long getIdSistemaRuta() {
		return idSistemaRuta;
	}

	public void setIdSistemaRuta(Long idSistemaRuta) {
		this.idSistemaRuta = idSistemaRuta;
	}

	public String getActivo() {
		return activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
	}
	
	
	
}
