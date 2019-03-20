package co.gov.metropol.area247.core.gateway.siata.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import co.gov.metropol.area247.core.ordenamiento.dto.CoordenadaDto;

public class StationAgua implements IStation{
	private String categoria;
	private String urlIcono;
	private List<CoordenadaDto> coordenadas;
	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT-05:00")
	private Date ultimaActualizacion;
	private String nombre;
	private int codigo;
	private double porcentajeNivel;
	private String subCuenca;
	private String colorIconoHex;
	private String colorIconoRGB;

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

	public List<CoordenadaDto> getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(List<CoordenadaDto> coordenadas) {
		this.coordenadas = coordenadas;
	}

	public Date getUltimaActualizacion() {
	return ultimaActualizacion;
	}

	public void setUltimaActualizacion(Date ultimaActualizacion) {
	this.ultimaActualizacion = ultimaActualizacion;
	}

	public String getNombre() {
	return nombre;
	}

	public void setNombre(String nombre) {
	this.nombre = nombre;
	}

	public int getCodigo() {
	return codigo;
	}

	public void setCodigo(int codigo) {
	this.codigo = codigo;
	}
	/**
	 * @return the porcentajeNivel
	 */
	public double getPorcentajeNivel() {
		return porcentajeNivel;
	}

	/**
	 * @param porcentajeNivel the porcentajeNivel to set
	 */
	public void setPorcentajeNivel(double porcentajeNivel) {
		this.porcentajeNivel = porcentajeNivel;
	}

	public String getSubCuenca() {
	return subCuenca;
	}

	public void setSubCuenca(String subCuenca) {
	this.subCuenca = subCuenca;
	}
//	@JsonIgnore
//	@Override
//	public String getMedicion() {
//		return null;
//	}

//	@Override
//	public String getDescripcion() {
//		return this.subCuenca;
//	}
//	@JsonIgnore
//	@Override
//	public List<PronosticoDto> getPronosticos() {
//		return null;
//	}
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
	public String getMedicion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescripcion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Forecast> getPronosticos() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
