package co.gov.metropol.area247.contenedora.util;

public enum TipoCapa {
	
	ARBOL(1, "ARBOL"),
	MAPA(2, "MAPA"),
	ENCUESTA(3, "ENCUESTA"),
	REPORTE(4, "REPORTE"),
	SUBCAPAS(5, "SUBCAPAS"),
	NA(6, "N/A");
	
	
	/**
	 * @param id
	 * @param nombre
	 */
	private TipoCapa(int id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	private int id;
	
	private String nombre;
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.nombre;
	}
	
}
