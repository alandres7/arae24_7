package co.gov.metropol.area247.huellas.rest.request;

public enum TipoTransporte {
	
	AUTO("AUTO", 244.9156944, 0.052163144, 5),
	BUS("BUS", 589.2383471, 0.077476136, 26),
	ALIMENTADOR("ALIMENTADOR", 589.2383471, 0.077476136, 26),
	METRO("METRO", 237.5809015, 0, 26), //Esta medida es experimental tomado de https://www.terra.org/calc/ dado que el AMVA no nos entrego info al respecto,
	TRANVIA("TRANVIA", 237.5809015, 0, 26),
	METRO_PLUS("METRO_PLUS", 237.5809015, 0, 26),
	METRO_CABLE("METRO_CABLE", 237.5809015, 0, 26),
	A_PIE("A PIE", 0, 0, 1),
	BICI("BICI", 0, 0, 1),
	ENCICLA("ENCICLA", 0, 0, 1);
	
	
	private String nombre;
	private double factorEmisionCO2;
	private double factorEmisionPM2_5;
	private int numPasajeros;
	
	/**
	 * @param nombre
	 * @param factorEmisionCO2
	 * @param factorEmisionPM2_5
	 * @param numPasajeros
	 */
	private TipoTransporte(String nombre, double factorEmisionCO2, double factorEmisionPM2_5, int numPasajeros) {
		this.nombre = nombre;
		this.factorEmisionCO2 = factorEmisionCO2;
		this.factorEmisionPM2_5 = factorEmisionPM2_5;
		this.numPasajeros = numPasajeros;
	}

	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	
	public double getFactorEmisionCO2() {
		return factorEmisionCO2;
	}

	public void setFactorEmisionCO2(double factorEmisionCO2) {
		this.factorEmisionCO2 = factorEmisionCO2;
	}

	public double getFactorEmisionPM2_5() {
		return factorEmisionPM2_5;
	}

	public void setFactorEmisionPM2_5(double factorEmisionPM2_5) {
		this.factorEmisionPM2_5 = factorEmisionPM2_5;
	}
	
	public int getCantPasajeros() {
		return numPasajeros;
	}

	public void setCantPasajeros(int cantPasajeros) {
		this.numPasajeros = cantPasajeros;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.nombre;
	}
	
}
