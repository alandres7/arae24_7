package co.gov.metropol.area247.repository.domain.support.enums;

public enum RutaType {
	
	METRO(1),
	BUS(2),
	INTEGRADO(3),
	ALIMENTADORES(4),
	CICLA(5);
	
	private Integer value;
	
	RutaType(Integer value){
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
}
