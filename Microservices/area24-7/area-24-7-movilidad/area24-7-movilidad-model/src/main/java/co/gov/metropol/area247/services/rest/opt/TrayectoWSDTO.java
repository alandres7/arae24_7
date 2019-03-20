package co.gov.metropol.area247.services.rest.opt;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vividsolutions.jts.geom.Coordinate;

import co.gov.metropol.area247.util.Utils;
import co.gov.metropol.area247.util.web.Coordenada;

/**
 * Esta clase contiene la informacion del trayecto del posible viaje que arroja
 * el servicio del OpenTripPlanner.
 */
@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true, allowSetters = true)
public class TrayectoWSDTO {

	/**
	 * Indica en que se realiza el recorrido, ejemplo 0(Caminando), 1(Linea),
	 * 2(Ruta)
	 */
	private Integer tipoTransporte;

	/**
	 * Duracion en minutos que tarda en recorrer el trayecto.
	 */
	private Long duracion;

	/**
	 * Distancia en metros del trayecto
	 */
	@JsonProperty(value = "distance")
	private Double distancia;

	/**
	 * Hora en la que se incia a recorrer el trayecto
	 */
	@JsonProperty(value = "startTime")
	private Date horaSalida;

	/**
	 * Hora en la que llega al final del trayecto
	 */
	@JsonProperty(value = "endTime")
	private Date horaLlegada;

	/**
	 * El modo en el que se desplaza por el trayecto(e.g WALK, TRANSIT, BUS,
	 * RAIL, etc)
	 */
	@JsonProperty(value = "mode")
	private String modo;

	/**
	 * Si el modo es en bus, linea, ferreo u otras entonces estas tienen una
	 * ruta. Si el modo de desplazamiento es caminando entonces no hay ruta de
	 * transito.
	 */
	@JsonProperty(value = "routeId")
	private String codigoRuta;

	@JsonProperty(value = "routeLongName")
	private String nombreRuta;

	@JsonProperty(value = "routeShortName")
	private String nombreCortoRuta;

	@JsonProperty(value = "routeType")
	private Long tipoRuta;

	/**
	 * Si el modo es en bus, linea, ferreo u otras entonces estas estan
	 * asociadas a una empresa. Si el modo de desplazamiento es caminando
	 * entonces no hay una agencia o empresa asociada.
	 */
	@JsonProperty(value = "agencyId")
	private String codigoAgencia;

	@JsonProperty(value = "agencyName")
	private String nombreAgencia;

	/**
	 * Estacion de donde inicia el trayecto {@link EstacionWSDTO}
	 */
	@JsonProperty(value = "from")
	private EstacionWSDTO desde;

	/**
	 * Estacion donde finaliza el trayecto {@link EstacionWSDTO}
	 */
	@JsonProperty(value = "to")
	private EstacionWSDTO hasta;

	/**
	 * Calles que del trayecto que se recorren caminando, este campo aplica si
	 * el modo de desplazamiento es caminando.<br>
	 * 
	 * {@link CalleWSDTO}
	 */
	@JsonProperty(value = "steps")
	private List<CalleWSDTO> calles;

	/**
	 * Si el trayecto es en linea o ruta entonces aqui es donde se almacenara la
	 * informacion de las coordenadas de toda la ruta o linea.<br>
	 * <br>
	 * 
	 * {@link Coordenada}
	 */
	private List<Coordinate> coordenadas;
	
	@JsonProperty(value = "legGeometry")
	private Map<Object, String> puntosCodificados;

	public Long getDuracion() {
		return duracion;
	}

	public void setDuracion(Long duracion) {
		this.duracion = duracion;
	}

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

	public String getModo() {
		return modo;
	}

	public void setModo(String modo) {
		this.modo = modo;
	}

	public String getCodigoRuta() {
		if (!Utils.isNull(codigoRuta) && codigoRuta.contains(":"))
			return codigoRuta.split(":")[1];
		return codigoRuta;
	}

	public void setCodigoRuta(String codigoRuta) {
		this.codigoRuta = codigoRuta;
	}

	public String getNombreRuta() {
		return nombreRuta;
	}

	public void setNombreRuta(String nombreRuta) {
		this.nombreRuta = nombreRuta;
	}

	public Long getTipoRuta() {
		return tipoRuta;
	}

	public void setTipoRuta(Long tipoRuta) {
		this.tipoRuta = tipoRuta;
	}

	public String getCodigoAgencia() {
		return codigoAgencia;
	}

	public void setCodigoAgencia(String codigoAgencia) {
		this.codigoAgencia = codigoAgencia;
	}

	public String getNombreAgencia() {
		return nombreAgencia;
	}

	public void setNombreAgencia(String nombreAgencia) {
		this.nombreAgencia = nombreAgencia;
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

	public List<CalleWSDTO> getCalles() {
		return calles;
	}

	public void setCalles(List<CalleWSDTO> calles) {
		this.calles = calles;
	}

	public String getNombreCortoRuta() {
		return nombreCortoRuta;
	}

	public void setNombreCortoRuta(String nombreCortoRuta) {
		this.nombreCortoRuta = nombreCortoRuta;
	}

	public Integer getTipoTransporte() {
		return tipoTransporte;
	}

	public void setTipoTransporte(Integer tipoTransporte) {
		this.tipoTransporte = tipoTransporte;
	}

	public List<Coordinate> getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(List<Coordinate> coordenadas) {
		this.coordenadas = coordenadas;
	}

	public Map<Object, String> getPuntosCodificados() {
		return puntosCodificados;
	}

	public void setPuntosCodificados(Map<Object, String> puntosCodificados) {
		this.puntosCodificados = puntosCodificados;
	}
}
