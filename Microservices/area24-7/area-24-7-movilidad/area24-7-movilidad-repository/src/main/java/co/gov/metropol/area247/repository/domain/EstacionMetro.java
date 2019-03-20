package co.gov.metropol.area247.repository.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import co.gov.metropol.area247.repository.domain.abstracts.Entities;
import co.gov.metropol.area247.repository.domain.support.enums.ModoRecorrido;

@Entity
@Table(name = "T247VIA_ESTACION_METRO", schema = "MOVILIDAD")
public class EstacionMetro extends Entities {

	private static final long serialVersionUID = -6599078360831815355L;

	@Column(name = "N_ID_ITEM")
	private Long idEstacion;
	
	@Column(name = "S_CODIGO")
	private String codigo;

	@Column(name = "S_DESCRIPCION")
	private String descripcion;

	@Column(name = "N_LATITUD")
	private Double latitud;

	@Column(name = "N_LONGITUD")
	private Double longitud;

	@Column(name = "S_ACTIVO")
	private Character activo;

	@Column(name = "ID_MODO_ESTACION")
	@Enumerated(EnumType.ORDINAL)
	private ModoRecorrido idModoEstacion;

	@Column(name = "S_TIPO_ESTACION")
	private String modoLinea;

	@Column(name = "ID_LINEA")
	public Long idLinea;

	@Override
	public EstacionMetro withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public EstacionMetro withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}

	public Long getIdEstacion() {
		return idEstacion;
	}

	public EstacionMetro setIdEstacion(Long idEstacion) {
		this.idEstacion = idEstacion;
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

	public Character getActivo() {
		return activo;
	}

	public void setActivo(Character activo) {
		this.activo = activo;
	}

	public ModoRecorrido getIdModoEstacion() {
		return idModoEstacion;
	}

	public void setIdModoEstacion(ModoRecorrido idModoEstacion) {
		this.idModoEstacion = idModoEstacion;
	}

	public String getModoLinea() {
		return modoLinea;
	}

	public void setModoLinea(String modoLinea) {
		this.modoLinea = modoLinea;
	}

	public Long getIdLinea() {
		return idLinea;
	}

	public void setIdLinea(Long idLinea) {
		this.idLinea = idLinea;
	}

}
