package co.gov.metropol.area247.contenedora.model.enums;

public enum TiposMultimedia {
	
	IMAGEN(1),
	AUDIO(3),
	VIDEO(6);
	
	private final int tipoMultimedia;
	
	TiposMultimedia(int tipoMultimedia){
		this.tipoMultimedia = tipoMultimedia;
	}

	public int getTipoMultimedia() {
		return tipoMultimedia;
	}

}
