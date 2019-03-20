package co.gov.metropol.area247.service.impl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.onebusaway.csv_entities.EntityHandler;
import org.onebusaway.gtfs.impl.GtfsRelationalDaoImpl;
import org.onebusaway.gtfs.model.Agency;
import org.onebusaway.gtfs.model.Route;
import org.onebusaway.gtfs.model.ServiceCalendar;
import org.onebusaway.gtfs.model.ShapePoint;
import org.onebusaway.gtfs.model.Stop;
import org.onebusaway.gtfs.model.StopTime;
import org.onebusaway.gtfs.model.Trip;
import org.onebusaway.gtfs.serialization.GtfsReader;
import org.onebusaway.gtfs.services.GtfsMutableRelationalDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.service.IGtfsEscrituraService;
import co.gov.metropol.area247.service.ITipoParametroService;
import co.gov.metropol.area247.util.constantes.Constantes.TipoParametro;
import co.gov.metropol.area247.util.ex.Area247Exception;

/**
 * Esta implementacion crea un archivo GTFS a partir de la informacion que se
 * encuentra persistida en base de datos.
 */
@Service("gtfsMetroArchivoEscrituraService")
public class GtfsMetroArchivoEscrituraServiceImpl implements IGtfsEscrituraService {

	private GtfsMutableRelationalDao store;
	
	@Autowired
	private ITipoParametroService tipoParametroService;
	
	@Override
	public void escribirGTFS() {}

	@Override
	public Set<Route> obtenerRutas() {
		cargarInfoGTFS();
		return new HashSet<>(store.getAllRoutes());
	}

	@Override
	public Set<Stop> obtenerParaderos() {
		cargarInfoGTFS();
		return new HashSet<>(store.getAllStops());
	}

	@Override
	public Set<ShapePoint> obtenerCoordenadas() {
		cargarInfoGTFS();
		return new HashSet<>(store.getAllShapePoints());
	}
	
	@Override
	public Set<Trip> obtenerViajes() {
		cargarInfoGTFS();
		return new HashSet<>(store.getAllTrips());
	}
	
	@Override
	public Set<StopTime> obtenerTiemposDeParada() {
		cargarInfoGTFS();
		return new HashSet<>(store.getAllStopTimes());
	}

	@Override
	public Map<Trip, List<StopTime>> obtenerViajesConTiemposParada() {
		return null;
	}

	@Override
	public Set<Agency> obtenerAgencias() {
		cargarInfoGTFS();
		return new HashSet<>(store.getAllAgencies());
	}

	@Override
	public Collection<ServiceCalendar> obtenerCalendarios() {
		cargarInfoGTFS();
		return store.getAllCalendars();
	}

	/**
	 * Lee el archivo GTFS para obtener la informacion de este
	 */
	public void cargarInfoGTFS() {
		try {
			if (null == store) {
				File archivoGTFS = obtenerArchivoGTFS(
						tipoParametroService.obtenerValorCadena(TipoParametro.URL_FILE_GTFS_METRO));
				GtfsReader reader = new GtfsReader();
				reader.setInputLocation(archivoGTFS);
				reader.addEntityHandler((EntityHandler) new GtfsEntityHandler());
				store = new GtfsRelationalDaoImpl();
				reader.setEntityStore(store);
				reader.run();
			}
		} catch (IOException e) {
			throw new Area247Exception("Error al cargar la información del GTFS : " + e.getMessage());
		}
	}
	
	/**
	 * El parametro de entrada contiene la URL o el Path del archivo GTFS a
	 * procesar.
	 * 
	 * @param arg
	 *            - URL o PATH del arhivo GTFS
	 * 
	 * @return un {@link File} con la informacion del GTFS
	 * TODO factorizar, este metodo tambien se utiliza en {@link MetroGtfsServiceImpl}
	 */
	public File obtenerArchivoGTFS(String arg) {

		try {

			if (isURL(arg)) {
				URL url;
				url = new URL(arg);
				return new File(url.getFile());
			}

			File file = new File(getClass().getResource(arg).getFile());
			
			return file;

		} catch (MalformedURLException e) {
			throw new Area247Exception("Error al obtener el arhivo GTFS desde la url " + arg + " : " + e.getMessage());
		}
	}
	
	/** TODO factorizar, este metodo tambien se utiliza en {@link MetroGtfsServiceImpl}*/
	private static boolean isURL(String arg) {
		return arg.startsWith("http");
	}
	
	private static class GtfsEntityHandler implements EntityHandler {

		public void handleEntity(Object bean) {
			if (bean instanceof Stop) {
				Stop stop = (Stop) bean;
			}
		}
	}

}
