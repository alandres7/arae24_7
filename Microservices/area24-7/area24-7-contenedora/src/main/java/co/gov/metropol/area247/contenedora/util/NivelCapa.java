package co.gov.metropol.area247.contenedora.util;

public enum NivelCapa {
	
	CAPA(2,"CAPA"),
	CATEGORIA(3,"CATEGORIA");
	
	NivelCapa(int nivel, String tabla){
		this.nivel = nivel;
		this.tabla = tabla;
	}
	int nivel;
	String tabla;
	
	public int getNivel() {
		return nivel;
	}
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	
	public String getTabla() {
		return tabla;
	}
	public void setTabla(String tabla) {
		this.tabla = tabla;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.tabla;
	}
}
