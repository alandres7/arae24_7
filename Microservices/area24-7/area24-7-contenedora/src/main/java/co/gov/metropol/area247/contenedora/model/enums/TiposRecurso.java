package co.gov.metropol.area247.contenedora.model.enums;

public enum TiposRecurso {
	
	CAPA(1),
	CATEGORIA(2),
	SUBCATEGORIA(3);
	
	private final int valor;
	
	TiposRecurso(int valor) {
		this.valor = valor;
	}

	public int getValor() {
		return valor;
	}
	
	
	
	

}
