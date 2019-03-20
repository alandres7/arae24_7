package co.gov.metropol.area247.entorno.model.dto;

import java.io.Serializable;
import java.util.Date;

public class PronosticoDetail implements Serializable{
	
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
	private String temperaturaMinima;
	private String temperaturaMaxima;
	private String municipio;
	private String nombreEstacion;
	
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
	public String getTemperaturaMinima() {
		return temperaturaMinima;
	}
	public void setTemperaturaMinima(String temperaturaMinima) {
		this.temperaturaMinima = temperaturaMinima;
	}
	public String getTemperaturaMaxima() {
		return temperaturaMaxima;
	}
	public void setTemperaturaMaxima(String temperaturaMaxima) {
		this.temperaturaMaxima = temperaturaMaxima;
	}
	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	/**
	 * @param tiempoInicial
	 * @param tiempoFinal
	 * @param codigoVentana
	 * @param tiempo
	 * @param descripcion
	 * @param urlIcono
	 * @param temperatura
	 * @param municipio
	 */
	public PronosticoDetail(Date tiempoInicial, Date tiempoFinal, Long codigoVentana, String tiempo, String descripcion,
			String urlIcono, String temperatura, String temperaturaMinima, String temperaturaMaxima,String municipio, 
			String nombreEstacion) {
		this.tiempoInicial = tiempoInicial;
		this.tiempoFinal = tiempoFinal;
		this.codigoVentana = codigoVentana;
		this.tiempo = tiempo;
		this.descripcion = descripcion;
		this.urlIcono = urlIcono;
		this.temperatura = temperatura;
		this.temperaturaMinima = temperaturaMinima;
		this.temperaturaMaxima = temperaturaMaxima;
		this.municipio = municipio;		
		this.nombreEstacion = nombreEstacion;
	}
	/**
	 * 
	 */
	public PronosticoDetail() {
		// TODO Auto-generated constructor stub
	}
	public String getNombreEstacion() {
		return nombreEstacion;
	}
	public void setNombreEstacion(String nombreEstacion) {
		this.nombreEstacion = nombreEstacion;
	}
	
}
