package co.gov.metropol.area247.entorno.domain.enums;

public enum CapaEntorno {
	
	CLIMA ("CLIMA"),
	AIRE("AIRE"),
	AGUA("AGUA"),
	SUELO("SUELO");
	
	private CapaEntorno(String nombreCapa) {
		this.nombreCapa = nombreCapa;
	}
	
	private String nombreCapa;

	public String getNombreCapa() {
		return nombreCapa;
	}

	public void setNombreCapa(String nombreCapa) {
		this.nombreCapa = nombreCapa;
	}
	
}
