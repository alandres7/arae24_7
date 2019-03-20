package co.gov.metropol.area247.services.rest.gtpc;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true, allowSetters = true)
public class ViajesGtpcWSDTO {

	@JsonProperty(value = "gtype")
	private Long idRuta;

	@JsonProperty(value = "codigoRuta")
	private String codigoRuta;

	@JsonProperty(value = "paraderoInicio")
	private String paraderoInicio;

	@JsonProperty(value = "paraderoFIn")
	private String paraderoFIn;

	@JsonProperty(value = "stopTimes")
	private List<TiempoParadaGtpcWSDTO> stopTimes;

	public Long getIdRuta() {
		return idRuta;
	}

	public void setIdRuta(Long idRuta) {
		this.idRuta = idRuta;
	}

	public String getCodigoRuta() {
		return codigoRuta;
	}

	public void setCodigoRuta(String codigoRuta) {
		this.codigoRuta = codigoRuta;
	}

	public String getParaderoInicio() {
		return paraderoInicio;
	}

	public void setParaderoInicio(String paraderoInicio) {
		this.paraderoInicio = paraderoInicio;
	}

	public String getParaderoFin() {
		return paraderoFIn;
	}

	public void setParaderoFin(String paraderoFIn) {
		this.paraderoFIn = paraderoFIn;
	}

	public List<TiempoParadaGtpcWSDTO> getTiempoParada() {
		return stopTimes;
	}

	public void setTiempoParada(List<TiempoParadaGtpcWSDTO> tiempoParada) {
		this.stopTimes = tiempoParada;
	}

}
