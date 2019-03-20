package co.gov.metropol.area247.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.onebusaway.gtfs.model.Route;
import org.onebusaway.gtfs.model.ServiceCalendar;
import org.onebusaway.gtfs.model.ShapePoint;
import org.onebusaway.gtfs.model.Stop;
import org.onebusaway.gtfs.model.StopTime;
import org.onebusaway.gtfs.model.Trip;

/**
 * Interfaz que manipula informacion del archivo GTFS
 */
public interface IGtfsService {

	/**
	 * Busca todas las coordenadas de una ruta o linea definida, estas
	 * coordenadas se encuentran definidas en el archivo shapes.txt. Hay casos
	 * en que las rutas no tienen definidas sus coordenadas, entonces para este
	 * caso se tomaran las coordenadas de las estaciones de la ruta definidas en
	 * el archivo stops.txt. Los archivos mencionados hacen parte del archivo
	 * GTFS(*.zip)
	 * 
	 * @param route
	 *            - filtro para la busqueda de coordenadas.
	 * 
	 * @return una lista de objetos de tipo {@link ShapePoint} que representan
	 *         coordenadas y estas son coincidentes con la ruta fijada como
	 *         parametro.
	 */
	List<ShapePoint> findShapePointsByRoute(Route route);

	/**
	 * Busca todos los timpos de parada de una ruta y dias de servicio.
	 * 
	 * @param route
	 *            - filtro de busqueda
	 * @param service
	 *            - filtro de busqueda
	 * 
	 * @return una lista con los tiempos de parada de la ruta y el servicio
	 */
	List<StopTime> findStopTimeByRouteAndService(Route route, ServiceCalendar service);

	/**
	 * Busca todos los timpos de parada de una ruta y dias de servicio.
	 * 
	 * @param route
	 *            - filtro de busqueda
	 * @param service
	 *            - filtro de busqueda
	 * @param trip
	 *            - filtro de busqueda
	 * 
	 * @return una lista con los tiempos de parada de la ruta y el servicio
	 */
	List<StopTime> findStopTimeByRouteServiceAndTrip(Route route, ServiceCalendar service, Trip trip);

	/**
	 * Busca todas las estaciones o paraderos de una ruta o linea definida, esta
	 * busqueda se realiza con informacion encontrada en los archivos
	 * stop_times.txt y stops.txt. Los archivos mencionados hacen parte del
	 * archivo GTFS(*.zip)
	 * 
	 * @param route
	 *            - filtro para la busqueda de coordenadas.
	 * 
	 * @return una lista de objetos de tipo {@link Stop} que representan
	 *         paraderos coincidentes con la ruta fijada como parametro.
	 */
	List<Stop> findStopsByRoute(Route route);

	/**
	 * Busca todos tiempos de parada de los vehiculos en las estaciones de la
	 * ruta definida como argumento.
	 * 
	 * @param route
	 *            - filtro de busqueda
	 * 
	 * @return lista de objetos tipo {@link StopTime} que representan los
	 *         tiempos de parada de la ruta definida.
	 */
	List<StopTime> findStopTimeByRoute(Route route);

	/**
	 * Obtiene el objeto de tipo {@link StopTime} que tenga la hora de llegada
	 * igual o mas cercana a la especificada en los argumentos, tambien que
	 * coincida con la ruta y el servicio(Laboras, Sabado o Domingo).<br>
	 * <br>
	 * Es muy utilizado para obtener los datos de los horarios de una ruta.
	 * 
	 * @param route
	 *            - filtro de busqueda
	 * @param service
	 *            - filtro de busqueda
	 * @param arrivalTime
	 *            - tiempo al cual se debe asemejar la hora de llegada del
	 *            StopTime, o acercarse.
	 * @param sequence
	 *            - Si se desea comparar el tiempo con la secuencia de las
	 *            estaciones, ejemplo 36000(10:00:00) el StopTime que se
	 *            aproxime a esta hora debe concidir con la secuencia definida.
	 * 
	 * @return objeto de tipo {@link StopTime}
	 */
	StopTime getStopTimeByRouteAndServiceAndArrivalTime(Route route, ServiceCalendar service, int arrivalTime,
			Integer sequence);

	/**
	 * Obtiene los viajes que realiza una ruta segun un calendario
	 * 
	 * @param route - filtro
	 * @param service - filtro
	 * @return un conjunto de objetos tipo {@link Trip}
	 */
	Set<Trip> findTripsByRouteAndService(Route route, ServiceCalendar service);
	
	/**
	 * Obtiene los trips y por cada trip su viajes y paradas
	 * @param route - filtro
	 * @param service - filtro
	 * @return un mapa con los trips y sus respectivos viajes y paradas
	 */
	Map<Trip, List<StopTime>> findTripsAndStopsTimeByRouteAndService(Route route, ServiceCalendar service);
}
