package co.gov.metropol.area247.repository.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import co.gov.metropol.area247.repository.domain.abstracts.Entities;


@Entity
@Table(name = "T247VIA_PARADERO_RUTA", schema = "MOVILIDAD")
public class ParaderoRuta extends Entities {

	private static final long serialVersionUID = 4583047977279120994L;

	@Column(name = "N_ID_ITEM")
	private Long idItem;
	
	@Column(name = "N_FUENTE_DATOS")
	private int fuenteDatos;

	@Column(name = "S_DESCRIPCION")
	private String descripcion;

	@Column(name = "N_LATITUD")
	private Double latitud;

	@Column(name = "N_LONGITUD")
	private Double longitud;
	
	@Column(name = "S_CODIGO_PARADERO")
	private String codigo;

	@Column(name = "ID_TIPO_PARADERO")
	private Long idTipoParadero;
	
	@Column(name = "ID_TIPO_ORIENTACION")
	private Long idTipoOrientacion;

	@Column(name = "ID_RUTA")
	private Long idRuta;
	
	@Column(name = "S_ACTIVO")
	private Character activo;

	@Override
	public ParaderoRuta withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public ParaderoRuta withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Long getIdTipoParadero() {
		return idTipoParadero;
	}

	public void setIdTipoParadero(Long idTipoParadero) {
		this.idTipoParadero = idTipoParadero;
	}

	public Long getIdTipoOrientacion() {
		return idTipoOrientacion;
	}

	public void setIdTipoOrientacion(Long idTipoOrientacion) {
		this.idTipoOrientacion = idTipoOrientacion;
	}

	public Long getIdRuta() {
		return idRuta;
	}

	public void setIdRuta(Long idRuta) {
		this.idRuta = idRuta;
	}

	public Character getActivo() {
		return activo;
	}

	public void setActivo(Character activo) {
		this.activo = activo;
	}

	

}
