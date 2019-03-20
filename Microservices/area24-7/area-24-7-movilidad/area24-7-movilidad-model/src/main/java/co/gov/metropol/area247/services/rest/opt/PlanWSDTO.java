package co.gov.metropol.area247.services.rest.opt;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Contiene las variables para manejar un plan de viaje
 */
@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true, allowSetters = true)
public class PlanWSDTO {

	/**
	 * Fecha en la que se realiza la peticion de calculo de posibilidad de
	 * viajes
	 */
	@JsonProperty(value = "date")
	private Date fecha;

	/**
	 * Punto de origen del viaje
	 */
	@JsonProperty(value = "from")
	private EstacionWSDTO desde;

	/**
	 * Punto destino del viaje
	 */
	@JsonProperty(value = "to")
	private EstacionWSDTO hasta;

	/**
	 * Punto destino del viaje
	 */
	@JsonProperty(value = "distanceByCar")
	private Double distanciaAutomovil;
	
	/**
	 * Todos los posibles caminos a realizar para llegar al destino desde el
	 * origen.
	 */
	@JsonProperty(value = "itineraries")
	private List<ItinerarioWSDTO> itinerarios;

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public EstacionWSDTO getDesde() {
		return desde;
	}

	public void setDesde(EstacionWSDTO desde) {
		this.desde = desde;
	}

	public EstacionWSDTO getHasta() {
		return hasta;
	}

	public void setHasta(EstacionWSDTO hasta) {
		this.hasta = hasta;
	}

	public List<ItinerarioWSDTO> getItinerarios() {
		return itinerarios;
	}

	public void setItinerarios(List<ItinerarioWSDTO> itinerarios) {
		this.itinerarios = itinerarios;
	}

	public Double getDistanciaAutomovil() {
		return distanciaAutomovil;
	}

	public void setDistanciaAutomovil(Double distanciaAutomovil) {
		this.distanciaAutomovil = distanciaAutomovil;
	}

	
}
