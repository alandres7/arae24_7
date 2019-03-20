package co.gov.metropol.area247.services.rest.opt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Contiene las variables que manejan la informacion de las calles de las calles
 * del trayecto en caso de que el modo de recorrer el trayecto sea caminando.
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true, allowSetters = true)
public class CalleWSDTO {

	/**
	 * Distancia en metros de la calle
	 */
	@JsonProperty(value = "distance")
	private Double distancia;

	/**
	 * Orientacion relativa(Derecha, Izquierda, Salida)
	 */
	@JsonProperty(value = "relativeDirection")
	private String orientacionRelativa;

	/**
	 * Orientacion absoluta(Norte, Sur, etc)
	 */
	@JsonProperty(value = "absoluteDirection")
	private String orientacionAbsoluta;

	/**
	 * Nombre de la calle
	 */
	@JsonProperty(value = "streetName")
	private String nombre;

	@JsonProperty(value = "lon")
	private Double longitud;

	@JsonProperty(value = "lat")
	private Double latitud;

	public Double getDistancia() {
		return distancia;
	}

	public void setDistancia(Double distancia) {
		this.distancia = distancia;
	}

	public String getOrientacionRelativa() {
		return orientacionRelativa;
	}

	public void setOrientacionRelativa(String orientacionRelativa) {
		this.orientacionRelativa = orientacionRelativa;
	}

	public String getOrientacionAbsoluta() {
		return orientacionAbsoluta;
	}

	public void setOrientacionAbsoluta(String orientacionAbsoluta) {
		this.orientacionAbsoluta = orientacionAbsoluta;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Double getLongitud() {
		return longitud;
	}

	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}

	public Double getLatitud() {
		return latitud;
	}

	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}

}
