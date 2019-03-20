package co.gov.metropol.area247.centrocontrol.dto;

import java.util.List;

public class ValidarArchivoDto {
	
	private int lineas;
	private List<ErrorLineaArchivoDto> errores;
	
	public int getLineas() {
		return lineas;
	}
	public void setLineas(int lineas) {
		this.lineas = lineas;
	}
	public List<ErrorLineaArchivoDto> getErrores() {
		return errores;
	}
	public void setErrores(List<ErrorLineaArchivoDto> errores) {
		this.errores = errores;
	}

}
