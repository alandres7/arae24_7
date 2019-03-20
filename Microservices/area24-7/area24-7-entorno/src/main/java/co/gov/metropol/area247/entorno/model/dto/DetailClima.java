package co.gov.metropol.area247.entorno.model.dto;

import java.io.Serializable;
import java.util.Date;

public class DetailClima implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1526105367634745348L;
	private Date tiempoInicial;
	private Date tiempoFinal;
	private Long codigoVentana;
	private String tiempo;
	private String descripcion;
	private String urlIcono;
	private String temperatura;
	private String municipio;
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
	public String getTiempo() {
		return tiempo;
	}
	public void setTiempo(String tiempo) {
		this.tiempo = tiempo;
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
	public String getTemperatura() {
		return temperatura;
	}
	public void setTemperatura(String temperatura) {
		this.temperatura = temperatura;
	}
	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	
	
	
}
