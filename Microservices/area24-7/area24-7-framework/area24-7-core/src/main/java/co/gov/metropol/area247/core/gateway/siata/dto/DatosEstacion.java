package co.gov.metropol.area247.core.gateway.siata.dto;

import java.io.Serializable;

import org.json.JSONObject;

public class DatosEstacion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3083544391574289939L;
	
	private String mensaje;
	
	private JSONObject datos;

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public JSONObject getDatos() {
		return datos;
	}

	public void setDatos(JSONObject datos) {
		this.datos = datos;
	}

	/**
	 * @param mensaje
	 * @param datos
	 */
	public DatosEstacion(String mensaje, JSONObject datos) {
		this.mensaje = mensaje;
		this.datos = datos;
	}

	/**
	 * 
	 */
	public DatosEstacion() {
		// TODO Auto-generated constructor stub
	}
	
}
