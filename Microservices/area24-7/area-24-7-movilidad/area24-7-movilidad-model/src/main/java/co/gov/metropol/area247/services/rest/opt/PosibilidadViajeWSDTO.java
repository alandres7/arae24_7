package co.gov.metropol.area247.services.rest.opt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Esta clase contiene la información de los posibles viajes que se pueden
 * realizar desde un origen hasta un destino, estos posibles viajes se obtienen
 * de del servicio del OTP.
 * 
 * @version 1.0
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true, allowSetters = true)
public class PosibilidadViajeWSDTO {

	/**
	 * Codigo que indica el resultado del consumo del WS de OpenTripPlanner. 
	 * 1 - Consulta exitosa, 2 - No se encontraron viajes, 3 - Servidor OTP apagado
	 * 
	 */
	private int codigo;
	private String mensaje;
	
	/**
	 * En caso de que el servicio falle almacenara informacion del error en esta
	 * variable
	 */
	@JsonProperty(value = "error")
	private ErrorWSDTO error;

	/**
	 * Informacion del plan de viajes disponibles desde el origen hasta el
	 * destino
	 */
	@JsonProperty(value = "plan")
	private PlanWSDTO plan;

	public ErrorWSDTO getError() {
		return error;
	}

	public void setError(ErrorWSDTO error) {
		this.error = error;
	}

	public PlanWSDTO getPlan() {
		return plan;
	}

	public void setPlan(PlanWSDTO plan) {
		this.plan = plan;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

}
