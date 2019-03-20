package co.gov.metropol.area247.repository.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import co.gov.metropol.area247.repository.domain.abstracts.Entities;

@Entity
@Table(name = "T247VIA_PARADERO_RUTA", schema = "MOVILIDAD")
@NamedQueries({
	@NamedQuery(name = "Paradero.findByLocalizacion" , query = "Select e From Paradero e Where 6371*(2*atan2 (sqrt(sin(((3.14*(e.latitud - (:latitude)))/180)/2) * sin(((3.14*(e.latitud - (:latitude)))/180)/2)+cos((3.14 * e.latitud)/180)*cos((3.14 * (:latitude))/180) * sin(((3.14*(e.longitud - (:longitude)))/180)/2)* sin(((3.14*(e.longitud - (:longitude)))/180)/2)),(sqrt(1-(sin(((3.14 * (e.latitud - (:latitude)))/180)/2) *sin(((3.14*(e.latitud - (:latitude)))/180)/2) +cos((3.14 * e.latitud)/180)* cos((3.14 * (:latitude))/180)* sin(((3.14*(e.longitud - (:longitude)))/180)/2)* sin(((3.14 * (e.longitud - (:longitude)))/180)/2)))))) <= (:radio) ORDER BY 6371*(2*atan2 (sqrt(sin(((3.14*(e.latitud - (:latitude)))/180)/2) * sin(((3.14*(e.latitud - (:latitude)))/180)/2)+cos((3.14 * e.latitud)/180)*cos((3.14 * (:latitude))/180) * sin(((3.14*(e.longitud - (:longitude)))/180)/2)* sin(((3.14*(e.longitud - (:longitude)))/180)/2)),(sqrt(1-(sin(((3.14 * (e.latitud - (:latitude)))/180)/2) *sin(((3.14*(e.latitud - (:latitude)))/180)/2) +cos((3.14 * e.latitud)/180)* cos((3.14 * (:latitude))/180)* sin(((3.14*(e.longitud - (:longitude)))/180)/2)* sin(((3.14 * (e.longitud - (:longitude)))/180)/2)))))) ASC" )
})
public class Paradero extends Entities {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "N_ID_ITEM")
	private Long idParadero;
	
	@Column(name = "S_DESCRIPCION")
	private String descripcion;
	
	@Column(name = "N_LATITUD")
	private Double latitud;
	
	@Column(name = "N_LONGITUD")
	private Double longitud;
	
	@Column(name = "N_CODIGO_PARADERO")
	private Long codigoParadero;
	
	@Column(name = "ID_TIPO_PARADERO")
	private Long idTipoParadero;
	
	@Column(name = "ID_TIPO_ORIENTACION")
	private Long idTipoOrientacion;
	
	@Column(name = "S_ACTIVO")
	private String activo;
	
	@Column(name = "ID_RUTA")
	private Long idRuta;
	
	@Override
	public Paradero withId(Long id) {
		super.setId(id);
		return this;
	}
	@Override
	public Paradero withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}

    public Long getIdParadero() {
		return idParadero;
	}
	public void setIdParadero(Long idParadero) {
		this.idParadero = idParadero;
	}
	public String getDescripcion() {
        return descripcion;
    }

    public Paradero setDescripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public Double getLatitud() {
        return latitud;
    }

    public Paradero setLatitud(Double latitud) {
        this.latitud = latitud;
        return this;
    }

    public Double getLongitud() {
        return longitud;
    }

    public Paradero setLongitud(Double longitud) {
        this.longitud = longitud;
        return this;
    }

    public Long getCodigoParadero() {
        return codigoParadero;
    }

    public Paradero setCodigoParadero(Long codigoParadero) {
        this.codigoParadero = codigoParadero;
        return this;
    }

    public Long getIdTipoParadero() {
        return idTipoParadero;
    }

    public Paradero setIdTipoParadero(Long idTipoParadero) {
        this.idTipoParadero = idTipoParadero;
        return this;
    }

    public Long getIdTipoOrientacion() {
        return idTipoOrientacion;
    }

    public Paradero setIdTipoOrientacion(Long idTipoOrientacion) {
        this.idTipoOrientacion = idTipoOrientacion;
        return this;
    }

    public String getActivo() {
        return activo;
    }

    public Paradero setActivo(String activo) {
        this.activo = activo;
        return this;
    }

    public Long getIdRuta() {
        return idRuta;
    }

    public Paradero setIdRuta(Long idRuta) {
        this.idRuta = idRuta;
        return this;
    }
	
}
