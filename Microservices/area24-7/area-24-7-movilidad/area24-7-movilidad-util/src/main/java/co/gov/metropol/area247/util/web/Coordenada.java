package co.gov.metropol.area247.util.web;

public class Coordenada {
	
	private double latitud;
	private double longitud;
	private double radio;
	private static int RADIO = 6371;

	public Coordenada() {}

	public Coordenada(double latitud, double longitud, double radio) {
		this.latitud = latitud;
		this.longitud = longitud;
		this.radio = radio;
	}
	
	public Coordenada(double latitud, double longitud) {
		this.latitud = latitud;
		this.longitud = longitud;
	}

	public Coordenada(String latitud, String longitud) {
		this.latitud = Double.parseDouble(latitud);
		this.longitud = Double.parseDouble(longitud);
	}

	/**
	* Metodo que calcula la distancia entre dos coordenadas geograficas
	* @param c1 coordenada 1
	* @param c2 coordenada 2
	* @return distancia (en Km's)
	*/
	public static double calcularDistancia(Coordenada c1, Coordenada c2) {
		double dLat = Math.toRadians(c2.getLatitud() - c1.getLatitud());
		double dLon = Math.toRadians(c2.getLongitud() - c1.getLongitud());
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
			Math.cos(Math.toRadians(c1.getLatitud())) * Math.cos(Math.toRadians(c2.getLatitud())) *
			Math.sin(dLon / 2) * Math.sin(dLon / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double d = RADIO * c;
		return d;
	}

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public double getRadio() {
		return radio;
	}

	public void setRadio(double radio) {
		this.radio = radio;
	}
}
