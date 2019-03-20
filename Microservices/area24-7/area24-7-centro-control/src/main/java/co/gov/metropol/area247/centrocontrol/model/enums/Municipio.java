package co.gov.metropol.area247.centrocontrol.model.enums;

public enum Municipio {

	BARBOSA("BARBOSA"),
	BELLO("BELLO"),
	CALDAS("CALDAS"),
	COPACABANA("COPACABANA"),
	ENVIGADO("ENVIGADO"),
	GIRARDOTA("GIRARDOTA"),
	ITAGUI("ITAGUI"),
	LA_ESTRELLA("LA_ESTRELLA"),	
	SABANETA("SABANETA"),
	MEDELLIN("MEDELLIN"),
	RURAL("RURAL"),
	URBANO("URBANO");
	
	private final String municipio;

	Municipio(String municipio) {
		this.municipio = municipio;
	}

	public String getMunicipio() {
		return municipio;
	}

}
