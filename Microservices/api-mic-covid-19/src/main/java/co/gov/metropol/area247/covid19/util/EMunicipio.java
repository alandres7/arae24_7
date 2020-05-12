package co.gov.metropol.area247.covid19.util;

public enum EMunicipio {
	
	MEDELLIN("MEDELLIN","Medellín","Medellin"),
	BELLO("BELLO", "Bello", "Bello"),
	ENVIGADO("ENVIGADO","Envigado","Envigado"),
	LA_ESTRELLA("LA ESTRELLA","La Estrella","La Estrella"),
	COPACABANA("COPACABANA","Copacabana","Copacabana"),
	ITAGUI("ITAGUI","Itagüí","Itagui"),
	SABANETA("SABANETA","Sabaneta","Sabaneta"),
	GIRARDOTA("GIRARDOTA","Girardota","Girardota"),
	CALDAS("CALDAS","Caldas","Caldas"),
	BARBOSA("BARBOSA","Barbosa","Barbosa");
	
	EMunicipio(String nameInterno, String nameDatosAbiertos, 
				String nameDatosAbiertosAlt) {
		this.nameInterno = nameInterno;
		this.nameDatosAbiertos = nameDatosAbiertos;
		this.nameDatosAbiertosAlt = nameDatosAbiertosAlt;
	}
	
	final private String nameInterno;
	final private String nameDatosAbiertos;
	final private String nameDatosAbiertosAlt;
	
	public String getNameInterno() {
		return nameInterno;
	}
	public String getNameDatosAbiertos() {
		return nameDatosAbiertos;
	}
	public String getNameDatosAbiertosAlt() {
		return nameDatosAbiertosAlt;
	}

}
