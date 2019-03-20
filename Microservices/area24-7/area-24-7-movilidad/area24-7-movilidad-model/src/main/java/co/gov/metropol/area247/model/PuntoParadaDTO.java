package co.gov.metropol.area247.model;

/**
 * Objecto que contiene las varialbles para manejar la informacion de los puntos
 * de parada que la persona realiza caminando.
 *
 */
public class PuntoParadaDTO {

	private String nombre;

	private Double latitud;

	private Double longitud;

	private String vertice;

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

	public String getVertice() {
		return vertice;
	}

	public void setVertice(String vertice) {
		this.vertice = vertice;
	}

}
