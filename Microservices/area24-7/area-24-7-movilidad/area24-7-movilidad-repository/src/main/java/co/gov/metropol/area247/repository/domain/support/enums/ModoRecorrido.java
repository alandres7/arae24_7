package co.gov.metropol.area247.repository.domain.support.enums;

public enum ModoRecorrido {
	
	TRANVIA(0, "TRAM"),
	AUTOBUS(1, "SUBWAY"),
	METRO(2, "RAIL"),
	METRO_PLUS(3, "BUS"),
	ALIMENTADOR(4, "FERRY"),
	ENCICLA(5, "BICYCLE_RENT"),
	METRO_CABLE(6, "GONDOLA"),
	INTEGRADO(7, "FUNICULAR"),
	BICICLETA_PARTICULAR(8, "BICYCLE"),
	CAMINAR(9, "WALK"),
	TRANSPORTE_PUBLICO(10, "TRANSIT"),
	AUTOMOVIL(11, "CAR");
		
	
	private final int indice;
	
	private final String modoOTP;
	
	private ModoRecorrido(int indice, String modoOTP) {
		this.indice = indice;
		this.modoOTP = modoOTP;
	}
	
	public int indice() {
		return this.indice;
	}
	
	public String getModoOTP() {
		return this.modoOTP;
	}
	
	public static ModoRecorrido valueOf(int indice) {
		for (ModoRecorrido modo : values()) {
			if (modo.indice == indice)
				return modo;
		}
		throw new IllegalArgumentException("No encontró constante para ["+ indice +"]");
	}
	
	public static ModoRecorrido valueOfModoOtp(String modoOTP) {
		for (ModoRecorrido modo : values()) {
			if (modo.modoOTP.equalsIgnoreCase(modoOTP))
				return modo;
		}
		throw new IllegalArgumentException("No encontró constante para ["+ modoOTP +"]");
	}
	
	public static ModoRecorrido[] valuesOf(int... indices) {
		ModoRecorrido[] modosRecorrido = new ModoRecorrido[indices.length];
		for (int i = 0; i < indices.length; i++) {
			modosRecorrido[i] = valueOf(indices[i]);	
		}	
		return modosRecorrido;
	}
	
	public static boolean isLinea(int indice) {
		ModoRecorrido modo = valueOf(indice);
		return isLinea(modo);
	}

	public static boolean isLinea(ModoRecorrido modo) {
		return modo.equals(TRANVIA) || modo.equals(METRO) || modo.equals(METRO_CABLE) || modo.equals(METRO_PLUS);
	}
	
	public boolean isLinea() {
		return this.equals(TRANVIA) || this.equals(METRO) || this.equals(METRO_CABLE) || this.equals(METRO_PLUS);
	}
	
	public boolean isRuta() {
		return this.equals(AUTOBUS) || this.equals(ALIMENTADOR) || this.equals(INTEGRADO);
	}
}
