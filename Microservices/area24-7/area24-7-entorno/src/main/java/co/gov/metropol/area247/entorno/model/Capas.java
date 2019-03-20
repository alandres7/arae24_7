package co.gov.metropol.area247.entorno.model;

public enum Capas {
	
	AIRE (10), 
	AGUA (11),
	CLIMA (12), 
	SUELO (13),
	NODEFINIDO(-1);
	private final int valor;
	
	Capas(int valor) {
		this.valor = valor;
	}
	
	public final int getValor() {
		return valor;
	}
	
	public static Capas obtenerPorId(int id) {
		for(Capas capa: Capas.values()) {
			if(capa.getValor()==id)
				return capa;
		}
		return Capas.NODEFINIDO;
	}
}
