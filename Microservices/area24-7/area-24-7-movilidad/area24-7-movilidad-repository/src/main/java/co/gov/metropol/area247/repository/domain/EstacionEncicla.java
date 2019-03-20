package co.gov.metropol.area247.repository.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import co.gov.metropol.area247.repository.domain.abstracts.Entities;

@Entity
@Table(name = "TSIMAUD_ITEMS_ENCICLA", schema = "MOVILIDAD")
@NamedQueries({
	@NamedQuery(name = "EstacionEncicla.findByLocalizacion" , query = "Select e From EstacionEncicla e Where 6371*(2*atan2 (sqrt(sin(((3.14*(e.latitud - (:latitude)))/180)/2) * sin(((3.14*(e.latitud - (:latitude)))/180)/2)+cos((3.14 * e.latitud)/180)*cos((3.14 * (:latitude))/180) * sin(((3.14*(e.longitud - (:longitude)))/180)/2)* sin(((3.14*(e.longitud - (:longitude)))/180)/2)),(sqrt(1-(sin(((3.14 * (e.latitud - (:latitude)))/180)/2) *sin(((3.14*(e.latitud - (:latitude)))/180)/2) +cos((3.14 * e.latitud)/180)* cos((3.14 * (:latitude))/180)* sin(((3.14*(e.longitud - (:longitude)))/180)/2)* sin(((3.14 * (e.longitud - (:longitude)))/180)/2)))))) <= (:radio) ORDER BY 6371*(2*atan2 (sqrt(sin(((3.14*(e.latitud - (:latitude)))/180)/2) * sin(((3.14*(e.latitud - (:latitude)))/180)/2)+cos((3.14 * e.latitud)/180)*cos((3.14 * (:latitude))/180) * sin(((3.14*(e.longitud - (:longitude)))/180)/2)* sin(((3.14*(e.longitud - (:longitude)))/180)/2)),(sqrt(1-(sin(((3.14 * (e.latitud - (:latitude)))/180)/2) *sin(((3.14*(e.latitud - (:latitude)))/180)/2) +cos((3.14 * e.latitud)/180)* cos((3.14 * (:latitude))/180)* sin(((3.14*(e.longitud - (:longitude)))/180)/2)* sin(((3.14 * (e.longitud - (:longitude)))/180)/2)))))) ASC" )
})
public class EstacionEncicla extends Entities{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "N_ID_ITEM")
	private Long idEstacion;
	
	@Column(name = "N_ORDEN")
	private Long orden;
	
	@Column(name = "S_NOMBRE", nullable = false, length = 200)
	private String nombre;
	
	@Column(name = "S_DIRECCION", length = 200)
	private String direccion;
	
	@Column(name = "S_DESCRIPCION", length = 300)
	private String descripcion;
	
	@Column(name = "D_LAT")
	private Double latitud;
	
	@Column(name = "D_LON")
	private Double longitud;
	
	@Column(name = "N_CAPACIDAD")
	private Long capacidad;
	
	@Column(name = "N_BICICLETAS")
	private Long ciclas;
	
	@Column(name = "N_LUGARES")
	private Long lugares;
	
	@Column(name = "S_IMAGEN", length = 200)
	private String imagen;

	@Override
	public EstacionEncicla withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public EstacionEncicla withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}

	public Long getIdEstacion() {
		return idEstacion;
	}

	public EstacionEncicla setIdEstacion(Long idEstacion) {
		this.idEstacion = idEstacion;
		return this;
	}

	public Long getOrden() {
		return orden;
	}

	public EstacionEncicla setOrden(Long orden) {
		this.orden = orden;
		return this;
	}

	public String getNombre() {
		return nombre;
	}

	public EstacionEncicla setNombre(String nombre) {
		this.nombre = nombre;
		return this;
	}

	public String getDireccion() {
		return direccion;
	}

	public EstacionEncicla setDireccion(String direccion) {
		this.direccion = direccion;
		return this;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public EstacionEncicla setDescripcion(String descripcion) {
		this.descripcion = descripcion;
		return this;
	}

	public Double getLatitud() {
		return latitud;
	}

	public EstacionEncicla setLatitud(Double latitud) {
		this.latitud = latitud;
		return this;
	}

	public Double getLongitud() {
		return longitud;
	}

	public EstacionEncicla setLongitud(Double longitud) {
		this.longitud = longitud;
		return this;
	}

	public Long getCapacidad() {
		return capacidad;
	}

	public EstacionEncicla setCapacidad(Long capacidad) {
		this.capacidad = capacidad;
		return this;
	}

	public Long getCiclas() {
		return ciclas;
	}

	public EstacionEncicla setCiclas(Long ciclas) {
		this.ciclas = ciclas;
		return this;
	}

	public Long getLugares() {
		return lugares;
	}

	public EstacionEncicla setLugares(Long lugares) {
		this.lugares = lugares;
		return this;
	}

	public String getImagen() {
		return imagen;
	}

	public EstacionEncicla setImagen(String imagen) {
		this.imagen = imagen;
		return this;
	}

}
