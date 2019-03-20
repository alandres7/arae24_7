package co.gov.metropol.area247.core.gateway.siata.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Forecast {
	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT-05:00")
	private Date tiempoInicial;
	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT-05:00")
	private Date tiempoFinal;
	private Long codigoVentana;
	private String nombreVentana;
	private String descripcion;
	private String urlIcono;
	public Date getTiempoInicial() {
		return tiempoInicial;
	}
	public void setTiempoInicial(Date tiempoInicial) {
		this.tiempoInicial = tiempoInicial;
	}
	public Date getTiempoFinal() {
		return tiempoFinal;
	}
	public void setTiempoFinal(Date tiempoFinal) {
		this.tiempoFinal = tiempoFinal;
	}
	public Long getCodigoVentana() {
		return codigoVentana;
	}
	public void setCodigoVentana(Long codigoVentana) {
		this.codigoVentana = codigoVentana;
	}
	public String getNombreVentana() {
		return nombreVentana;
	}
	public void setNombreVentana(String nombreVentana) {
		this.nombreVentana = nombreVentana;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getUrlIcono() {
		return urlIcono;
	}
	public void setUrlIcono(String urlIcono) {
		this.urlIcono = urlIcono;
	}
}
