
package co.gov.metropol.area247.services.rest.opt;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Contiene las variables que manejaran informacion del camino disponible para
 * llegar al destino del viaje
 */
@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true, allowSetters = true)
public class ItinerarioWSDTO {


	/**
	 * Tarifa calculada para el viaje.
	 */
	@JsonProperty(value = "rate")
	private Long tarifa;
	
	/**
	 * Tiempo en minutos gastado en el recorrido del viaje.
	 */
	@JsonProperty(value = "duration")
	private Long tiempoViaje;

	/**
	 * Distancia en metros recorrida en el viaje.
	 */
	@JsonProperty(value = "walkDistance")
	private Double distanciaViaje;

	/**
	 * Hora de salida a la ubicacion de destino.
	 */
	@JsonProperty(value = "startTime")
	private Date horaSalidaOrigen;

	/**
	 * Hora estimada de llegada a la ubicacion de destino.
	 */
	@JsonProperty(value = "endTime")
	private Date horaLLegadaDestino;

	/**
	 * Tiempo en minutos estimado caminando en los trayectos del viaje
	 */
	@JsonProperty(value = "walkTime")
	private Long tiempoCaminando;

	/**
	 * Tiempo en minutos estimado utilizando los medios de transporte
	 */
	@JsonProperty(value = "transitTime")
	private Long tiempoTransitado;

	/**
	 * Etapas del viaje.
	 */
	@JsonProperty(value = "legs")
	private List<TrayectoWSDTO> trayectos;

	public Long getTiempoViaje() {
		return tiempoViaje;
	}

	public void setTiempoViaje(Long tiempoViaje) {
		this.tiempoViaje = tiempoViaje;
	}

	public Double getDistanciaViaje() {
		return distanciaViaje;
	}

	public void setDistanciaViaje(Double distanciaViaje) {
		this.distanciaViaje = distanciaViaje;
	}

	public Date getHoraSalidaOrigen() {
		return horaSalidaOrigen;
	}

	public void setHoraSalidaOrigen(Date horaSalidaOrigen) {
		this.horaSalidaOrigen = horaSalidaOrigen;
	}

	public Date getHoraLLegadaDestino() {
		return horaLLegadaDestino;
	}

	public void setHoraLLegadaDestino(Date horaLLegadaDestino) {
		this.horaLLegadaDestino = horaLLegadaDestino;
	}

	public Long getTiempoCaminando() {
		return tiempoCaminando;
	}

	public void setTiempoCaminando(Long tiempoCaminando) {
		this.tiempoCaminando = tiempoCaminando;
	}

	public Long getTiempoTransitado() {
		return tiempoTransitado;
	}

	public void setTiempoTransitado(Long tiempoTransitado) {
		this.tiempoTransitado = tiempoTransitado;
	}

	public List<TrayectoWSDTO> getTrayectos() {
		return trayectos;
	}

	public void setTrayectos(List<TrayectoWSDTO> trayectos) {
		this.trayectos = trayectos;
	}

	public Long getTarifa() {
		return tarifa;
	}

	public void setTarifa(Long tarifa) {
		this.tarifa = tarifa;
	}
	
}
