package co.gov.metropol.area247.repository.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;

import co.gov.metropol.area247.repository.domain.abstracts.Entities;
import co.gov.metropol.area247.repository.domain.support.enums.ModoRecorrido;

@Entity
@Table(name = "T247VIA_LINEA_METRO", schema = "MOVILIDAD")
@NamedQueries({
		@NamedQuery(name = "LineaMetro.findByCodeOrDescriptionLinea", query = "Select l From LineaMetro l Where l.codigo = :parametro or l.descripcion = :parametro") })
public class LineaMetro extends Entities {

	private static final long serialVersionUID = -5119008421592578175L;

	@Column(name = "N_ID_ITEM")
	private Long idLinea;

	@Column(name = "S_CODIGO_LINEA")
	private String codigo;

	@Column(name = "S_DESCRIPCION")
	private String descripcion;

	@Column(name = "N_LONGITUD_LINEA")
	private Double longitud;

	@Column(name = "S_COORDENADAS")
	private LineString coordenadas;

	@Column(name = "S_PRIMERPUNTO")
	private Point primerPunto;

	@Column(name = "S_ULTIMOPUNTO")
	private Point ultimoPunto;

	@Column(name = "S_ACTIVO")
	private Character activo;

	@Column(name = "ID_TIPO_LINEA")
	private Long idTipoLinea;

	@Column(name = "N_TIEMPO_ESTIMADO_LINEA")
	private Long tiempoEstimado;

	@Column(name = "ID_MODO_LINEA")
	@Enumerated(EnumType.ORDINAL)
	private ModoRecorrido idModoLinea;

	@Column(name = "N_VALOR_TARIFA_LINEA")
	private Double valorTarifa;

	@Override
	public LineaMetro withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public LineaMetro withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}

	public Long getIdLinea() {
		return idLinea;
	}

	public LineaMetro setIdLinea(Long idLinea) {
		this.idLinea = idLinea;
		return this;
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

	public Double getLongitud() {
		return longitud;
	}

	public void setLongitud(Double longitud) {
		this.longitud = longitud;
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

	public Character getActivo() {
		return activo;
	}

	public void setActivo(Character activo) {
		this.activo = activo;
	}

	public Long getTiempoEstimado() {
		return tiempoEstimado;
	}

	public void setTiempoEstimado(Long tiempoEstimado) {
		this.tiempoEstimado = tiempoEstimado;
	}

	public Double getValorTarifa() {
		return valorTarifa;
	}

	public void setValorTarifa(Double valorTarifa) {
		this.valorTarifa = valorTarifa;
	}

	public Long getIdTipoLinea() {
		return idTipoLinea;
	}

	public void setIdTipoLinea(Long idTipoLinea) {
		this.idTipoLinea = idTipoLinea;
	}

	public ModoRecorrido getIdModoLinea() {
		return idModoLinea;
	}

	public void setIdModoLinea(ModoRecorrido idModoLinea) {
		this.idModoLinea = idModoLinea;
	}

}
