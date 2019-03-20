package co.gov.metropol.area247.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.onebusaway.gtfs.model.Agency;
import org.onebusaway.gtfs.model.Route;
import org.onebusaway.gtfs.model.ServiceCalendar;
import org.onebusaway.gtfs.model.ShapePoint;
import org.onebusaway.gtfs.model.Stop;
import org.onebusaway.gtfs.model.StopTime;
import org.onebusaway.gtfs.model.Trip;

public interface IGtfsEscrituraService {

	/**
	 * Crear el archivo GTFS con la informacion del tipo de transporte(GTPC,
	 * Metro, ENCICLA) a implementar.
	 * <P>
	 * Creado 26/07/2018 4:50 p.m
	 */
	void escribirGTFS();

	/**
	 * Obtiene toda la informacion de las rutas activas en objetos tipo
	 * {@link Route}.
	 * <P>
	 * Creado 23/07/2018 2:30 p.m
	 * 
	 * @return conjunto de objetos tipo {@link Route}
	 */
	Set<Route> obtenerRutas();

	/**
	 * Obtiene toda la informacion de los paraderos de las rutas activas en
	 * objetos tipo {@link Stop}.
	 * <P>
	 * Creado 23/07/2018 2:35 p.m
	 * 
	 * @return conjunto de objetos tipo {@link Stop}
	 */
	Set<Stop> obtenerParaderos();

	/**
	 * Obtiene toda la informacion de las coordenadas de las rutas activas en
	 * objetos tipo {@link ShapePoint}.
	 * <P>
	 * Creado 23/07/2018 2:40 p.m
	 * 
	 * @return conjunto de objetos tipo {@link ShapePoint}
	 */
	Collection<ShapePoint> obtenerCoordenadas();
	
	/**
	 * Obtiene toda la informacion de todos los viajes que realizan todas las
	 * rutas.
	 * <P>
	 * Creado 13/07/2018 9:19 p.m
	 * 
	 * @return conjunto de objetos tipo {@link Trip}
	 */
	Collection<Trip> obtenerViajes();
	
	/**
	 * Obtiene toda la informacion de todos los tiempos de parada de todos los
	 * viajes que realizan todas las rutas.
	 * <P>
	 * Creado 13/07/2018 9:19 p.m
	 * 
	 * @return conjunto de objetos tipo {@link StopTime}
	 */
	Collection<StopTime> obtenerTiemposDeParada();

	/**
	 * Obtiene toda la informacion de los viajes y tiempos de paradas que
	 * realizan las rutas activas en objetos tipo {@link ShapePoint}.
	 * <P>
	 * Creado 23/07/2018 2:50 p.m
	 * 
	 * @return mapa con los viajes como llave de tipo {@link Trip} y sus valores
	 *         son los tiempos de parada con objetos tipo {@link StopTime}
	 */
	Map<Trip, List<StopTime>> obtenerViajesConTiemposParada();

	/**
	 * Obtiene la agencia de las rutas del metro en objetos tipo {@link Agency}
	 * <P>
	 * Creado 23/07/2018 2:50 p.m
	 * 
	 * @return las agencias o empresas que manejan el transporte {@link Agency}
	 */
	Set<Agency> obtenerAgencias();

	/**
	 * Obtiene los dias de trabajo de las rutas, por ahora se trabaja igual de
	 * lunes a lunes para rutas, lineas y encicla
	 * <P>
	 * Creado 23/07/2018 5:50 p.m
	 * 
	 * @return una coleccion con los dias de trabajo de las rutas tipo
	 *         {@link ServiceCalendar}
	 */
	Collection<ServiceCalendar> obtenerCalendarios();

}
