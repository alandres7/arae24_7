package co.gov.metropol.area247.core.gateway.siata.dto;

import java.util.List;

import co.gov.metropol.area247.core.ordenamiento.dto.CoordenadaDto;

public interface IStation {
	/**
	 * Metodo para obtener el valor de la medicion 
	 * @return
	 */
	public String getMedicion();
	/**
	 * Metodo para obtener el codigo de la medicion
	 * @return
	 */
	public int getCodigo();
	/**
	 * Metodo para obtener el nombre de la estacion
	 * @return
	 */
	public String getNombre();
	/**
	 * Metodo para obtener Descripcion de la estacion
	 * @return
	 */
	public String getDescripcion();
	/**
	 * Metodo para obtener las coordenadas
	 * @return
	 */
	public List<CoordenadaDto> getCoordenadas();
	/**
	 * Metodo para obtener los pronosticos si los hay
	 * @return
	 */
	public List<Forecast> getPronosticos();
}
