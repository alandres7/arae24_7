package co.gov.metropol.area247.model;

import java.util.Date;
import java.util.List;

@Deprecated
/**
 * Esta clase contiene la informacion del trayecto que hace parte del posible
 * viaje.(e.g Un trayecto puede ser tres cuadras caminando para llegar al
 * centro, otro trayecto puede ser una linea del metro desde el centro hasta el
 * norte de la ciudad, etc)
 */
public class TrayectoDTO {

	/**
	 * Distancia del trayecto
	 */
	private Double distancia;

	/**
	 * Hora en la que inicia a recorrer el trayecto
	 */
	private Date horaSalida;

	/**
	 * Hora en la que finaliza de recorrer el trayecto
	 */
	private Date horaLlegada;

	/**
	 * Puede ser caminando, en bus, en ferreo, teleferico, etc
	 */
	private String modoDesplazamiento;

	/**
	 * Si el modo de desplazamiento es en tranvia, tren o teleferico entonces la
	 * informacionn debe ir en esta variable.
	 */
	private LineaMetroDTO lineaDTO;

	/**
	 * Si el modo de desplazamiento es en autobus entonces la informacion debe
	 * ir en esta variable.
	 */
	private RutaMetroDTO rutaDTO;

	/**
	 * Si es una ruta la informacion de su paradero inicial debe ir en esta
	 * variable.
	 */
	private ParaderoRutaMetroDTO paraderoDesdeDTO;

	/**
	 * Si es una ruta la informacion de su paradero final debe ir en esta
	 * variable.
	 */
	private ParaderoRutaMetroDTO paraderoHastaDTO;

	/**
	 * Si es una linea la informacion de su estacion inicial debe ir en esta
	 * variable.
	 */
	private EstacionLineaMetroDTO estacionDesdeDTO;

	/**
	 * Si es una linea la informacion de su estacion final debe ir en esta
	 * variable.
	 */
	private EstacionLineaMetroDTO estacionHastaDTO;

	/**
	 * Crear objeto para manejar la informacion de las agencias o empresa
	 * que manejan los modos de transporte.
	 */
	private TipoModoTransporteDTO modoTransporteDTO;

	/**
	 * En caso de que la persona inicia o finaliza un viaje caminando, el punto
	 * de inicio de ese trayecto se deben almacenar en este objeto.
	 */
	private PuntoParadaDTO puntoParadaDesdeDTO;

	/**
	 * En caso de que la persona inicia o finaliza un viaje caminando, el punto
	 * final de ese trayecto se deben almacenar en este objeto.
	 */
	private PuntoParadaDTO puntoParadaHastaDTO;

	/**
	 * Contiene la informacion de las calles a recorrer en caso de que el modo
	 * de desplazamiento sea caminando.
	 */
	private List<CalleDTO> callesDTO;

	public Double getDistancia() {
		return distancia;
	}

	public void setDistancia(Double distancia) {
		this.distancia = distancia;
	}

	public Date getHoraSalida() {
		return horaSalida;
	}

	public void setHoraSalida(Date horaSalida) {
		this.horaSalida = horaSalida;
	}

	public Date getHoraLlegada() {
		return horaLlegada;
	}

	public void setHoraLlegada(Date horaLlegada) {
		this.horaLlegada = horaLlegada;
	}

	public String getModoDesplazamiento() {
		return modoDesplazamiento;
	}

	public void setModoDesplazamiento(String modoDesplazamiento) {
		this.modoDesplazamiento = modoDesplazamiento;
	}

	public LineaMetroDTO getLineaDTO() {
		return lineaDTO;
	}

	public void setLineaDTO(LineaMetroDTO lineaDTO) {
		this.lineaDTO = lineaDTO;
	}

	public RutaMetroDTO getRutaDTO() {
		return rutaDTO;
	}

	public void setRutaDTO(RutaMetroDTO rutaDTO) {
		this.rutaDTO = rutaDTO;
	}

	public ParaderoRutaMetroDTO getParaderoDesdeDTO() {
		return paraderoDesdeDTO;
	}

	public void setParaderoDesdeDTO(ParaderoRutaMetroDTO paraderoDesdeDTO) {
		this.paraderoDesdeDTO = paraderoDesdeDTO;
	}

	public ParaderoRutaMetroDTO getParaderoHastaDTO() {
		return paraderoHastaDTO;
	}

	public void setParaderoHastaDTO(ParaderoRutaMetroDTO paraderoHastaDTO) {
		this.paraderoHastaDTO = paraderoHastaDTO;
	}

	public EstacionLineaMetroDTO getEstacionDesdeDTO() {
		return estacionDesdeDTO;
	}

	public void setEstacionDesdeDTO(EstacionLineaMetroDTO estacionDesdeDTO) {
		this.estacionDesdeDTO = estacionDesdeDTO;
	}

	public EstacionLineaMetroDTO getEstacionHastaDTO() {
		return estacionHastaDTO;
	}

	public void setEstacionHastaDTO(EstacionLineaMetroDTO estacionHastaDTO) {
		this.estacionHastaDTO = estacionHastaDTO;
	}

	public TipoModoTransporteDTO getModoTransporteDTO() {
		return modoTransporteDTO;
	}

	public void setModoTransporteDTO(TipoModoTransporteDTO modoTransporteDTO) {
		this.modoTransporteDTO = modoTransporteDTO;
	}

	public PuntoParadaDTO getPuntoParadaDesdeDTO() {
		return puntoParadaDesdeDTO;
	}

	public void setPuntoParadaDesdeDTO(PuntoParadaDTO puntoParadaDesdeDTO) {
		this.puntoParadaDesdeDTO = puntoParadaDesdeDTO;
	}

	public PuntoParadaDTO getPuntoParadaHastaDTO() {
		return puntoParadaHastaDTO;
	}

	public void setPuntoParadaHastaDTO(PuntoParadaDTO puntoParadaHastaDTO) {
		this.puntoParadaHastaDTO = puntoParadaHastaDTO;
	}

	public List<CalleDTO> getCallesDTO() {
		return callesDTO;
	}

	public void setCallesDTO(List<CalleDTO> callesDTO) {
		this.callesDTO = callesDTO;
	}

}
