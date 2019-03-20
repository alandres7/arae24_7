package co.gov.metropol.area247.vigias.model.enums;

public enum Estados {

	PENDIENTE(2),
	APROBADO(1),
	RECHAZADO(0);
	
	private final int estado;
	
	Estados(int estado){
		this.estado = estado;
	}
	
	public int getEstado() {
		return estado;
	}
	
}