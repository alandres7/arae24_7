package co.gov.metropol.area247.core.gateway.siata.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;

import co.gov.metropol.area247.core.ordenamiento.dto.CoordenadaDto;

public class StationAire implements IStation, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7061915246678954586L;
	private List<RecomendacionAireJson> recomendaciones;
	int codigo;
	String nombre;
	@JsonProperty("valorICA")
	private double valorICA;
	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT-05:00")
	private Date ultimaActualizacion;
	@JsonProperty("categoria")
	private String categoria;	
	@JsonProperty("urlIcono")
	private String urlIcono;
	private String colorIconoHex;
	private String colorIconoRGB;
	private List<CoordenadaDto> coordenadas;
	@Override
	public List<CoordenadaDto> getCoordenadas() {
		return coordenadas;
	}
	public void setCoordenadas(List<CoordenadaDto> coordenadas) {
		this.coordenadas = coordenadas;
	}
	public double getValorICA() {
		return valorICA;
	}
	public void setValorICA(double valorICA) {
		this.valorICA = valorICA;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Date getUltimaActualizacion() {
		return ultimaActualizacion;
	}
	public void setUltimaActualizacion(Date ultimaActualizacion) {
		this.ultimaActualizacion = ultimaActualizacion;
	}
//	public String getUltimaActualizacion() {
//		return ultimaActualizacion;
//	}
//	public void setUltimaActualizacion(String ultimaActualizacion) {
//		this.ultimaActualizacion = ultimaActualizacion;
//	}
	@JsonIgnore
	public String getMedicion() {
		return "";
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public List<RecomendacionAireJson> getRecomendaciones() {
		return recomendaciones;
	}
	public void setRecomendaciones(List<RecomendacionAireJson> recomendaciones) {
		this.recomendaciones = recomendaciones;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getUrlIcono() {
		return urlIcono;
	}
	public void setUrlIcono(String urlIcono) {
		this.urlIcono = urlIcono;
	}
	@JsonIgnore
	@Override
	public String getDescripcion() {		
		return "";
	}
	@JsonIgnore
	@Override
	public List<Forecast> getPronosticos() {
		return null;
	}
	public String getColorIconoHex() {
		return colorIconoHex;
	}
	public void setColorIconoHex(String colorIconoHex) {
		this.colorIconoHex = colorIconoHex;
	}
	public String getColorIconoRGB() {
		return colorIconoRGB;
	}
	public void setColorIconoRGB(String colorIconoRGB) {
		this.colorIconoRGB = colorIconoRGB;
	}
	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}
