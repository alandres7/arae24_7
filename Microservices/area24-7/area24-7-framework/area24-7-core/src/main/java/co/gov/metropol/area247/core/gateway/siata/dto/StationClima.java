package co.gov.metropol.area247.core.gateway.siata.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import co.gov.metropol.area247.core.ordenamiento.dto.CoordenadaDto;

public class StationClima implements IStation {
	@JsonProperty("nombreMunicipio")
	private String nombre;
	private int codigoMunicipio;
	private List<CoordenadaDto> coordenadas;
	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT-05:00")
	private Date ultimaActualizacion;
	private List<Forecast> pronosticos;
	private String medicion;
	
	@Override
	public String getMedicion() {
		return this.medicion;
	}

	@Override
	public int getCodigo() {
		return this.codigoMunicipio;
	}

	@Override
	public String getNombre() {
		return this.nombre;
	}

	@Override
	public String getDescripcion() {
		return null;
	}
	@Override
	public List<CoordenadaDto> getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(List<CoordenadaDto> coordenadas) {
		this.coordenadas = coordenadas;
	}

	public void setCodigoMunicipio(int codigoMunicipio) {
		this.codigoMunicipio = codigoMunicipio;
	}

	public Date getUltimaActualizacion() {
		return ultimaActualizacion;
	}

	public void setUltimaActualizacion(Date ultimaActualizacion) {
		this.ultimaActualizacion = ultimaActualizacion;
	}
	@Override
	public List<Forecast> getPronosticos() {
		return pronosticos;
	}

	public void setPronosticos(List<Forecast> pronosticos) {
		this.pronosticos = pronosticos;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setMedicion(String medicion) {
		this.medicion = medicion;
	}
	
}
