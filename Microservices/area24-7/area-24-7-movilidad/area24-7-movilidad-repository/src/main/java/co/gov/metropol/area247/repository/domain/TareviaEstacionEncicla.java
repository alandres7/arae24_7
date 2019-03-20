package co.gov.metropol.area247.repository.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import co.gov.metropol.area247.repository.domain.abstracts.Entities;

@Entity
@Table(name = "T247VIA_ESTACION_ENCICLA", schema = "MOVILIDAD")
@NamedQueries({
	@NamedQuery(name ="TareviaEstacionEncicla.findByLocalizacion", query = "Select e From TareviaEstacionEncicla e Where 6371*(2*atan2 (sqrt(sin(((3.14*(e.latitudEstacionEncila - (:latitude)))/180)/2) * sin(((3.14*(e.latitudEstacionEncila - (:latitude)))/180)/2)+cos((3.14 * e.latitudEstacionEncila)/180)*cos((3.14 * (:latitude))/180) * sin(((3.14*(e.longitudEstacionEncila - (:longitude)))/180)/2)* sin(((3.14*(e.longitudEstacionEncila - (:longitude)))/180)/2)),(sqrt(1-(sin(((3.14 * (e.latitudEstacionEncila - (:latitude)))/180)/2) *sin(((3.14*(e.latitudEstacionEncila - (:latitude)))/180)/2) +cos((3.14 * e.latitudEstacionEncila)/180)* cos((3.14 * (:latitude))/180)* sin(((3.14*(e.longitudEstacionEncila - (:longitude)))/180)/2)* sin(((3.14 * (e.longitudEstacionEncila - (:longitude)))/180)/2)))))) <= (:radio) ORDER BY 6371*(2*atan2 (sqrt(sin(((3.14*(e.latitudEstacionEncila - (:latitude)))/180)/2) * sin(((3.14*(e.latitudEstacionEncila - (:latitude)))/180)/2)+cos((3.14 * e.latitudEstacionEncila)/180)*cos((3.14 * (:latitude))/180) * sin(((3.14*(e.longitudEstacionEncila - (:longitude)))/180)/2)* sin(((3.14*(e.longitudEstacionEncila - (:longitude)))/180)/2)),(sqrt(1-(sin(((3.14 * (e.latitudEstacionEncila - (:latitude)))/180)/2) *sin(((3.14*(e.latitudEstacionEncila - (:latitude)))/180)/2) +cos((3.14 * e.latitudEstacionEncila)/180)* cos((3.14 * (:latitude))/180)* sin(((3.14*(e.longitudEstacionEncila - (:longitude)))/180)/2)* sin(((3.14 * (e.longitudEstacionEncila - (:longitude)))/180)/2)))))) ASC")
})
public class TareviaEstacionEncicla extends Entities{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "ID_TAREVIA_ESTACION_ENCICLA")
	private Long idEstacionEncicla;
	
	@Column(name = "ID_DAREVIA_ZONA")
	private Long idZona;
	
	@Column(name = "S_NOMBRE", nullable = false, length = 30)
	private String nombreEstacionEncicla;
	
	@Column(name = "S_DIRECCION", nullable = false, length = 50)
	private String direccionEstacionEncicla;
	
	@Column(name = "S_DESC_DIRECCION", nullable = false, length = 100)
	private String descripcionEstacionEncicla;
	
	@Column(name = "N_LATITUD" , nullable = false)
	private Double latitudEstacionEncila;
	
	@Column(name = "N_LONGITUD" , nullable = false)
	private Double longitudEstacionEncila;
	
	@Column(name = "S_ACTIVO", nullable = false, length = 1)
	private String activaEstacionEncicla;
	
	@Column(name = "S_TIPO_ESTACION", nullable = false, length = 1)
	private String tipoEstacionEncicla;
	
	@Column(name = "N_CAPACIDAD" , nullable = false)
	private Long capacidadEstacionEncila;
	
	@Column(name = "N_BICICLETAS" , nullable = false)
	private Long cantidadBicicletas;
	
	@Column(name = "N_LUGARES" , nullable = false)
	private Long lugares;
	
	@Column(name = "ID_MODO_ESTACION" , nullable = false)
	private Long idModoEstacion;
	
	@Override
	public TareviaEstacionEncicla withId(Long id) {
		super.setId(id);
		return this;
	}

	@Override
	public TareviaEstacionEncicla withEnabled(boolean enabled) {
		super.setEnabled(enabled);
		return this;
	}

	public Long getIdEstacionEncicla() {
		return idEstacionEncicla;
	}

	public void setIdEstacionEncicla(Long idEstacionEncicla) {
		this.idEstacionEncicla = idEstacionEncicla;
	}

	public Long getIdZona() {
		return idZona;
	}

	public void setIdZona(Long idZona) {
		this.idZona = idZona;
	}

	public String getNombreEstacionEncicla() {
		return nombreEstacionEncicla;
	}

	public void setNombreEstacionEncicla(String nombreEstacionEncicla) {
		this.nombreEstacionEncicla = nombreEstacionEncicla;
	}

	public String getDireccionEstacionEncicla() {
		return direccionEstacionEncicla;
	}

	public void setDireccionEstacionEncicla(String direccionEstacionEncicla) {
		this.direccionEstacionEncicla = direccionEstacionEncicla;
	}

	public String getDescripcionEstacionEncicla() {
		return descripcionEstacionEncicla;
	}

	public void setDescripcionEstacionEncicla(String descripcionEstacionEncicla) {
		this.descripcionEstacionEncicla = descripcionEstacionEncicla;
	}

	public Double getLatitudEstacionEncila() {
		return latitudEstacionEncila;
	}

	public void setLatitudEstacionEncila(Double latitudEstacionEncila) {
		this.latitudEstacionEncila = latitudEstacionEncila;
	}

	public Double getLongitudEstacionEncila() {
		return longitudEstacionEncila;
	}

	public void setLongitudEstacionEncila(Double longitudEstacionEncila) {
		this.longitudEstacionEncila = longitudEstacionEncila;
	}

	public String getActivaEstacionEncicla() {
		return activaEstacionEncicla;
	}

	public void setActivaEstacionEncicla(String activaEstacionEncicla) {
		this.activaEstacionEncicla = activaEstacionEncicla;
	}

	public String getTipoEstacionEncicla() {
		return tipoEstacionEncicla;
	}

	public void setTipoEstacionEncicla(String tipoEstacionEncicla) {
		this.tipoEstacionEncicla = tipoEstacionEncicla;
	}

	public Long getCapacidadEstacionEncila() {
		return capacidadEstacionEncila;
	}

	public void setCapacidadEstacionEncila(Long capacidadEstacionEncila) {
		this.capacidadEstacionEncila = capacidadEstacionEncila;
	}

	public Long getCantidadBicicletas() {
		return cantidadBicicletas;
	}

	public void setCantidadBicicletas(Long cantidadBicicletas) {
		this.cantidadBicicletas = cantidadBicicletas;
	}

	public Long getLugares() {
		return lugares;
	}

	public void setLugares(Long lugares) {
		this.lugares = lugares;
	}

	public Long getIdModoEstacion() {
		return idModoEstacion;
	}

	public void setIdModoEstacion(Long idModoEstacion) {
		this.idModoEstacion = idModoEstacion;
	}
	
	
}
