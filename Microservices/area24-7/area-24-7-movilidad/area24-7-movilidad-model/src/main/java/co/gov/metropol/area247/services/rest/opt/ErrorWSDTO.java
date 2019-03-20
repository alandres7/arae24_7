package co.gov.metropol.area247.services.rest.opt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true, allowSetters = true)
public class ErrorWSDTO {

	/**
	 * Identificador del error (e.g 404, etc)
	 */
	@JsonProperty(value = "id")
	private Long id;

	@JsonProperty(value = "message")
	private String idMensaje;

	@JsonProperty(value = "msg")
	private String mensaje;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdMensaje() {
		return idMensaje;
	}

	public void setIdMensaje(String idMensaje) {
		this.idMensaje = idMensaje;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

}
