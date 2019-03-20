package co.gov.metropol.area247.repository.domain.support.enums;

@Deprecated
public enum ModoEstacionEnum {

	ENCICLA(2l, "Sistema de transporte Encicla");
	
	private final long indice;
	private final String descripcion;
	
	private ModoEstacionEnum(long indice, String descripcion) {
		this.indice = indice;
		this.descripcion = descripcion;
	}
	
	public long getIndice() {
		return this.indice;
	}
	
	public String getDescripcion() {
		return this.descripcion;
	}
}
