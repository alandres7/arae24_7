package co.gov.metropol.area247.centrocontrol.model.enums;

public enum TiposObjeto {
	
	ARBOL(1L),
	FORMULARIO(2L);
	
	private final Long valor;
	
	public Long getValor() {
		return valor;
	}

	TiposObjeto(Long valor){
		this.valor = valor;
	}
	
	

}
