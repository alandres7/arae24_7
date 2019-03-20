package co.gov.metropol.area247.services.rest.gtpc;

import java.sql.Time;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true, allowGetters = true, allowSetters = true)
public class TiempoParadaGtpcWSDTO {

	@JsonProperty(value = "paradero")
	private String paradero;
	
	@JsonProperty(value = "tiempoIn")
	private String tiempoIn;
	
	private int tiempoInInt;
	
	@JsonProperty(value = "tiempoOut")
	private String tiempoOut;
	
	private int tiempoOutInt;

	public String getParadero() {
		return paradero;
	}

	public void setParadero(String paradero) {
		this.paradero = paradero;
	}

	public String getTiempoEntrada() {
		return tiempoIn;
	}

	public void setTiempoEntrada(String tiempoEntrada) {
		this.tiempoIn = tiempoEntrada;
	}

	public String getTiempoSalida() {
		return tiempoOut;
	}

	public void setTiempoSalida(String tiempoSalida) {
		this.tiempoOut = tiempoSalida;
	}

	public int getTiempoInInt() {
		return horaStringAInt(tiempoIn);
	}

	public int getTiempoOutInt() {
		return horaStringAInt(tiempoOut);
	}
	
	private static int horaStringAInt(String hora) {
		Time horaTime = Time.valueOf(hora);
		return ((horaTime.getHours() * 3600) + (horaTime.getMinutes() * 60) + horaTime.getSeconds());
	}
}
