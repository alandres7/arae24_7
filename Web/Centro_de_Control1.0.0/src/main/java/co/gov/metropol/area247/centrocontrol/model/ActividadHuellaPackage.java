package co.gov.metropol.area247.centrocontrol.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/* Clase encargada de almacenar las diversas actividades 
 * de las encuestas del aplicativo huellas. */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ActividadHuellaPackage {

	private ActividadHuellaTransaccion transaccion;
	private List<ActividadHuella> responses;
	
	
	public ActividadHuellaTransaccion getTransaccion() {
		return transaccion;
	}
	
	public void setTransaccion(ActividadHuellaTransaccion transaccion) {
		this.transaccion = transaccion;
	}
	
	public List<ActividadHuella> getResponses() {
		return responses;
	}
	
	public void setResponses(List<ActividadHuella> responses) {
		this.responses = responses;
	}
				
}