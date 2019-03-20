package co.gov.metropol.area247.repository.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import co.gov.metropol.area247.repository.domain.abstracts.Entities;

@Entity
@Table(name = "T247VIA_PUNTO_TARJETA_CIVICA", schema = "MOVILIDAD")
public class PuntoTarjetaCivica extends Entities{

	@Column(name = "N_ID_ITEM")
	private Long idItem;
	
	@Column(name = "S_DESCRIPCION")
	private String descripcion;
	
	@Column(name = "S_TIPO_PUNTO")
	private String tipoPunto;
	
	@Column(name = "N_LATITUD")
	private Double latitud;
	
	@Column(name = "N_LONGITUD")
	private Double longitud;
	
	@Column(name = "S_ACTIVO")
	private String activo;
	
	@Override
	public PuntoTarjetaCivica withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public PuntoTarjetaCivica withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTipoPunto() {
		return tipoPunto;
	}

	public void setTipoPunto(String tipoPunto) {
		this.tipoPunto = tipoPunto;
	}

	public Double getLatitud() {
		return latitud;
	}

	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}

	public Double getLongitud() {
		return longitud;
	}

	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}

	public String getActivo() {
		return activo;
	}

	public void setActivo(String activo) {
		this.activo = activo;
	}

	public Long getIdItem() {
		return idItem;
	}

	public void setIdItem(Long idItem) {
		this.idItem = idItem;
	}
	
	

}
