package co.gov.metropol.area247.model;

import java.util.Date;
import java.util.List;

@Deprecated
public class ViajeDTO {

	/**
	 * Tiempo en minutos gastado en el viaje
	 */
	private Long tiempo;

	/**
	 * Distancia en metros de todo el recorrido del viaje.
	 */
	private Double distancia;

	/**
	 * Hora de salida a la ubicacion de destino.
	 */
	private Date horaSalidaOrigen;

	/**
	 * Hora estimada de llegada a la ubicacion de destino.
	 */
	private Date horaLLegadaDestino;

	/**
	 * Tiempo en minutos estimado caminando en los trayectos del viaje
	 */
	private Long tiempoCaminando;

	/**
	 * Tiempo en minutos estimado utilizando los medios de transporte
	 */
	private Long tiempoTransitado;

	/**
	 * Etapas del viaje.
	 */
	private List<TrayectoDTO> trayectosDTO;

	public Long getTiempo() {
		return tiempo;
	}

	public void setTiempo(Long tiempo) {
		this.tiempo = tiempo;
	}

	public Double getDistancia() {
		return distancia;
	}

	public void setDistancia(Double distancia) {
		this.distancia = distancia;
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

	public List<TrayectoDTO> getTrayectosDTO() {
		return trayectosDTO;
	}

	public void setTrayectosDTO(List<TrayectoDTO> trayectosDTO) {
		this.trayectosDTO = trayectosDTO;
	}

}
