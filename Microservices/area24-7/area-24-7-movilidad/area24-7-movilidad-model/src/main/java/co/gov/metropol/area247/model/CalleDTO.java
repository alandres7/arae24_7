package co.gov.metropol.area247.model;

/**
 * Contiene las variables que manejan la informacion de las calles de las calles
 * del trayecto en caso de que el modo de recorrer el trayecto sea caminando.
 *
 */
public class CalleDTO {

	/**
	 * Distancia en metros de la calle
	 */
	private Double distancia;

	/**
	 * Orientacion relativa(Derecha, Izquierda, Salida)
	 */
	private String orientacionRelativa;

	/**
	 * Orientacion absoluta(Norte, Sur, etc)
	 */
	private String orientacionAbsoluta;

	/**
	 * Nombre de la calle
	 */
	private String name;

	private Double longitud;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
