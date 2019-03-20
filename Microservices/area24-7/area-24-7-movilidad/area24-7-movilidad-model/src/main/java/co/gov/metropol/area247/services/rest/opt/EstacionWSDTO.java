package co.gov.metropol.area247.services.rest.opt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import co.gov.metropol.area247.util.Utils;

/**
 * Clase para almacenar la informacion de una estacion, parada o punto
 * importante de un trayecto
 */
@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true, allowSetters = true)
public class EstacionWSDTO {

	@JsonProperty(value = "stopId")
	private String id;

	@JsonProperty(value = "name")
	private String nombre;

	@JsonProperty(value = "lat")
	private Double latitud;

	@JsonProperty(value = "lon")
	private Double longitud;

	/**
	 * Modo en como inicia o termina en un punto(e.g Caminando, Transitando en
	 * vehiculo, etc)
	 */
	@JsonProperty(value = "vertexType")
	private String tipoVertice;

	@JsonProperty(value = "bikeShareId")
	private String estacionEnciclaId;
	
	private Long cantidadBicicletasDisponibles;
	private Long cantidadPuestosDisponibles;

	public String getId() {
		if (!Utils.isNull(id) && id.contains(":"))
			return id.split(":")[1];
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public String getTipoVertice() {
		return tipoVertice;
	}

	public void setTipoVertice(String tipoVertice) {
		this.tipoVertice = tipoVertice;
	}

	public String getEstacionEnciclaId() {
		return estacionEnciclaId;
	}

	public void setEstacionEnciclaId(String estacionEnciclaId) {
		this.estacionEnciclaId = estacionEnciclaId;
	}

	public Long getCantidadBicicletasDisponibles() {
		return cantidadBicicletasDisponibles;
	}

	public void setCantidadBicicletasDisponibles(Long cantidadBicicletasDisponibles) {
		this.cantidadBicicletasDisponibles = cantidadBicicletasDisponibles;
	}

	public Long getCantidadPuestosDisponibles() {
		return cantidadPuestosDisponibles;
	}

	public void setCantidadPuestosDisponibles(Long cantidadPuestosDisponibles) {
		this.cantidadPuestosDisponibles = cantidadPuestosDisponibles;
	}

}
