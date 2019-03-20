package co.gov.metropol.area247.service;

import com.google.gson.Gson;

public interface IGbfsEscrituraService {

	/**
	 * Crea o sobreescribe archivos GBFS en la ruta indicada como argumento
	 * <P>
	 * Creado el 19/10/2018 02:42 p.m
	 */
	void escribirGBFS();
	
	/**
	 * Crear o sobreescribe el archivo <em>gbfs.json</em> que
	 * contiene la informacion de los feed que describen el sistema de bicicletas.
	 * <P>
	 * Creado 25/10/2018 11:30 a.m
	 * 
	 * @return objeto {@link Gson} que contiene la informacion de los feed
	 */
	Gson crearJsonGbfs();

	/**
	 * Crear o sobreescribe el archivo <em>system_information.json</em> que
	 * contiene la informacion del sistema de bicicletas.
	 * <P>
	 * Creado 19/10/2018 3:39 p.m
	 * 
	 * @return objeto {@link Gson} que contiene la informacion del sistema de
	 *         bicicletas
	 */
	Gson crearJsonSistemaInformacion();

	/**
	 * Crear o sobreescribe el archivo <em>station_information.json</em> que
	 * contiene la informacion de las estaciones de las bicicletas.
	 * <P>
	 * Creado 24/10/2018 5:22 p.m
	 * 
	 * @return objeto {@link Gson} que contiene la informacion de las estaciones
	 *         del sistema de bicicletas
	 */
	Gson crearJsonEstacionesInformacion();

	/**
	 * Crear o sobreescribe el archivo <em>station_status.json</em> que contiene
	 * la informacion del estado de las estaciones.
	 * <P>
	 * Creado 25/10/2018 11:02 a.m
	 * 
	 * @return objeto {@link Gson} que contiene la informacion del estado de las
	 *         estaciones
	 */
	Gson crearJsonEstadoEstaciones();

	/**
	 * Crear o sobreescribe el archivo <em>system_regions.json</em> que contiene
	 * la informacion de las regiones por donde opera el sistema de bicicletas
	 * <P>
	 * Creado 25/10/2018 11:08 a.m
	 * 
	 * @return objeto {@link Gson} que contiene la informacion de las regiones
	 *         donde opera el sistema de bicicletas
	 */
	Gson crearJsonRegionesSistema();

}
