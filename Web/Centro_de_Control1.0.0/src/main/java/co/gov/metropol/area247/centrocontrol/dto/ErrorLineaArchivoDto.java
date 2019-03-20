package co.gov.metropol.area247.centrocontrol.dto;

import java.util.List;

public class ErrorLineaArchivoDto {
	
	private int linea;
	private String id;
	private List<ErrorCampoArchivoDto> erroresLinea;
	
	public ErrorLineaArchivoDto() {
		
	}
	public ErrorLineaArchivoDto(int linea, String id, List<ErrorCampoArchivoDto> erroresLinea) {
		this.linea = linea;
		this.id = id;
		this.erroresLinea = erroresLinea;
	}

	public int getLinea() {
		return linea;
	}
	public void setLinea(int linea) {
		this.linea = linea;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<ErrorCampoArchivoDto> getErroresLinea() {
		return erroresLinea;
	}
	public void setErroresLinea(List<ErrorCampoArchivoDto> erroresLinea) {
		this.erroresLinea = erroresLinea;
	}

}
