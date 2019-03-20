package co.gov.metropol.area247.repository.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;

import co.gov.metropol.area247.repository.domain.abstracts.Entities;

@Entity
@Table(name = "T247VIA_CICLORUTA", schema = "MOVILIDAD")
public class Cicloruta extends Entities{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "N_ID_ITEM")
	private Long idItem;
	
	@Column(name = "S_CODIGO_CICLORUTA")
	private String codigo;
	
	@Column(name = "S_DESCRIPCION")
	private String descripcion;
	
	@Column(name = "N_LONGITUD_CICLORUTA")
	private Double longitud;
	
	@Column(name = "S_COORDENADAS")
	private LineString coordenadas;
	
	@Column(name = "S_PRIMER_PUNTO")
	private Point primerPunto;
	
	@Column(name = "S_ULTIMO_PUNTO")
	private Point ultimoPunto;
	
	@Column(name = "S_ACTIVO")
	private String activa;
	
	@Override
	public Cicloruta withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public Cicloruta withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}

	public Long getIdItem() {
		return idItem;
	}

	public void setIdItem(Long idItem) {
		this.idItem = idItem;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public LineString getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(LineString coordenadas) {
		this.coordenadas = coordenadas;
	}

	public Point getPrimerPunto() {
		return primerPunto;
	}

	public void setPrimerPunto(Point primerPunto) {
		this.primerPunto = primerPunto;
	}

	public Point getUltimoPunto() {
		return ultimoPunto;
	}

	public void setUltimoPunto(Point ultimoPunto) {
		this.ultimoPunto = ultimoPunto;
	}

	public String getActiva() {
		return activa;
	}

	public void setActiva(String activa) {
		this.activa = activa;
	}

	public Double getLongitud() {
		return longitud;
	}

	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}


}
