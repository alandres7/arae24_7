package co.gov.metropol.area247.service;

import co.gov.metropol.area247.model.MetroDTO;

public interface IMetroGtfsService {

	/**
	 * Lee el archivo GTFS de la ubicacion definida en el parametro de entrada,
	 * esta informacion la mapea al objeto {@link MetroDTO} para poder manipular
	 * esta informacion en todo el proyecto.
	 * 
	 * @param ubicacion
	 *            - donde se encuentra alojado el archivo GTFS, esta ubicacion
	 *            puede ser una ruta en disco fisico o en la nube.
	 * 
	 * @return un objeto {@link MetroDTO}
	 */
	MetroDTO obtenerInfoMetroGtfs(String ubicacion);

}
