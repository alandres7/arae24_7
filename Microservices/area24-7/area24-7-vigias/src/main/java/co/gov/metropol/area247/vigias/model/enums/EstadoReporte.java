package co.gov.metropol.area247.vigias.model.enums;

public enum EstadoReporte {
	APROBADO(1, "APROBADO"),
	RECHAZADO(0, "RECHAZADO"),
	PENDIENTE(2, "PENDIENTE");
	
	private String nombre;
	private int codigo;
	
	EstadoReporte(int codigo, String estadoReporte) {
		this.codigo = codigo;		
		this.nombre = estadoReporte;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.name();
	}
	
}
