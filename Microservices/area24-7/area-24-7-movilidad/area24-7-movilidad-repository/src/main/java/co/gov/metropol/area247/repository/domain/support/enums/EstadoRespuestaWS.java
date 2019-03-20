package co.gov.metropol.area247.repository.domain.support.enums;

public enum EstadoRespuestaWS {
	
	EXITOSO(1, "Exitoso"),
	NO_ENCONTRO_INFO(2, "No encontro información relacionada"),
	ERROR_DATOS(4, "Error en los datos, existen datos inconsistentes o los datos obligatorios no fueron recibidos"),
	AUTENTICACION_FALLIDA(5, "Autenticación fallida, los datos de autenticación no coinciden para realizar la comunicación"),
	ERROR_TECNICO(6, "Se ha presentado un error técnico");
	
	private final int indice;
	private final String msg;
	
	private EstadoRespuestaWS(int indice, String msg) {
		this.indice = indice;
		this.msg = msg;
	}

	public int getIndice() {
		return indice;
	}

	public String getMsg() {
		return msg;
	}
	
	
	
}
