package co.gov.metropol.area247.centrocontrol.dto;

public class ErrorCampoArchivoDto {
	
	private String posicion;
	private String nombre;
	private String valor;
	private String mensajeError;
	
	public ErrorCampoArchivoDto() {
		
	}
	public ErrorCampoArchivoDto(String mensajeError) {
		this.posicion = "";
		this.nombre = "";
		this.valor = "";
		this.mensajeError = mensajeError;		
	}
	public ErrorCampoArchivoDto(String posicion, String nombre, String valor, String mensajeError) {
		this.posicion = posicion;
		this.nombre = nombre;
		this.valor = valor;
		this.mensajeError = mensajeError;
	}
	
	public String getPosicion() {
		return posicion;
	}
	public void setPosicion(String posicion) {
		this.posicion = posicion;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getMensajeError() {
		return mensajeError;
	}
	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}
		
}
