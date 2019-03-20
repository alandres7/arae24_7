package co.gov.metropol.area247.service.impl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import co.gov.metropol.area247.geometry.GeometryUtil;
import co.gov.metropol.area247.model.EmpresaTransporteDTO;
import co.gov.metropol.area247.model.EstacionLineaMetroDTO;
import co.gov.metropol.area247.model.FrecuenciaLineaMetroDTO;
import co.gov.metropol.area247.model.FrecuenciaRutaMetroDTO;
import co.gov.metropol.area247.model.HorarioLineaMetroDTO;
import co.gov.metropol.area247.model.HorarioRutaMetroDTO;
import co.gov.metropol.area247.model.InfoViajesLineaDTO;
import co.gov.metropol.area247.model.InfoViajesRutaDTO;
import co.gov.metropol.area247.model.LineaMetroDTO;
import co.gov.metropol.area247.model.MetroDTO;
import co.gov.metropol.area247.model.ParaderoRutaMetroDTO;
import co.gov.metropol.area247.model.RutaMetroDTO;
import co.gov.metropol.area247.model.TipoOrientacionDTO;
import co.gov.metropol.area247.repository.domain.support.enums.ModoRecorrido;
import co.gov.metropol.area247.service.IGtfsService;
import co.gov.metropol.area247.service.IMetroGtfsService;
import co.gov.metropol.area247.util.ex.Area247Exception;
import co.gov.metropol.area247.util.web.Coordenada;

@Service
public class MetroGtfsServiceImpl implements IMetroGtfsService {
	
	private static final char ACTIVO = 'S';
	private static final int MIN_SEGUNDO = 0;
	private static final int MAX_SEGUNDO = 100100;
	private static final int MIN_SEGUNDO_PICO_AM = 25200;
	private static final int MAX_SEGUNDO_PICO_AM = 32400;
	private static final int MIN_SEGUNDO_PICO_PM = 64800;
	private static final int MAX_SEGUNDO_PICO_PM = 72000;
	private static final int MITAD_DIA = 43200; 

	private static final String PATRON_HORA = "%02d:%02d:%02d";
	private GtfsMutableRelationalDao store;
	private Collection<Route> routes;

	private IGtfsService metroGTFS;
	
	private TipoOrientacionDTO orientacionNorteDTO;

	@Override
	public MetroDTO obtenerInfoMetroGtfs(String ubicacion) {
		
		MetroDTO metroDTO = new MetroDTO();
		
		try {

			File archivoGTFS = obtenerArchivoGTFS(ubicacion);
			cargarInfoGTFS(archivoGTFS);

			metroDTO.setCodigo("1");
			metroDTO.setDescripcion("Exitoso");
			metroDTO.setLineasDTO(obtenerLineasMetroDTO()); 
			metroDTO.setRutasDTO(obtenerRutasMetroDTO());

			archivoGTFS.delete();

		} catch (Exception e) {

			metroDTO.setCodigo("2");
			metroDTO.setDescripcion("Error interno");

		}

		return metroDTO;

	}
	
	// METODOS PARA OBTENER LA INFORMACION DE LAS LINEAS
	
	public List<LineaMetroDTO> obtenerLineasMetroDTO() {
		List<LineaMetroDTO> lineasDTO = new ArrayList<>();
		for (Route route : routes) {
			if (!route.getId().getId().contains("_")) {
				lineasDTO.add(obtenerLineaMetroDTO(route));
			}
		}
		return lineasDTO;
	}
	
	public LineaMetroDTO obtenerLineaMetroDTO(Route route) {

		LineaMetroDTO lineaDTO = new LineaMetroDTO();

		List<Coordenada> coordenadas = toCoordenadas(route);
		List<EstacionLineaMetroDTO> estacionesDTO = obtenerEstacionesLineaMetroDTO(route);
		List<HorarioLineaMetroDTO> horariosDTO = obtenerHorariosLineaMetroDTO(route);
		List<FrecuenciaLineaMetroDTO> frecuenciasDTO = obtenerFrecuenciasLineaMetroDTO(route, horariosDTO);
		List<InfoViajesLineaDTO> infoViajesDTO = obtenerInfoViajesLinea(route);

		lineaDTO.setCodigo(route.getId().getId());
		lineaDTO.setDescripcion(route.getLongName());
		lineaDTO.setLongitud(1000.0);
		lineaDTO.setCoordenadas(GeometryUtil.obtenerLineString(coordenadas));
		lineaDTO.setPrimerPunto(coordenadas.isEmpty() ? null : GeometryUtil.obtenerPunto(coordenadas.get(0)));
		lineaDTO.setUltimoPunto(coordenadas.isEmpty() ? null : GeometryUtil.obtenerPunto(coordenadas.get(coordenadas.size() - 1)));
		lineaDTO.setModoLinea(ModoRecorrido.valueOf(route.getType()));
		lineaDTO.setEstacionLineaMetro(estacionesDTO);
		lineaDTO.setHorarioLineaMetro(horariosDTO);
		lineaDTO.setFrecuenciaLineaMetro(frecuenciasDTO);
		// TODO Este campo TipoLinea no se ve necesario ya que contiene los valores de los modos de transporte
		// linea.setTipoLinea(TIPO_LINEAS[route.getType()]);
		// TODO Calcular tiempo estimado desde el archivo GTFS
		lineaDTO.setTiempoEstimado(0l);
		lineaDTO.setActivo(ACTIVO);
		lineaDTO.setInfoViajesLineaMetro(infoViajesDTO);

		return lineaDTO;
	}
	
	public List<Coordenada> toCoordenadas(Route route) {
		List<Coordenada> coordenadas = new ArrayList<>();
		metroGTFS.findShapePointsByRoute(route).iterator().forEachRemaining(shapePoint -> {
			coordenadas.add(toCoordenada(shapePoint));
		});
		return coordenadas;
	}
	
	public Coordenada toCoordenada(ShapePoint shapePoint) {

		Coordenada coordenada = new Coordenada();

		coordenada.setLatitud(shapePoint.getLat());
		coordenada.setLongitud(shapePoint.getLon());

		return coordenada;
	}
	
	public List<EstacionLineaMetroDTO> obtenerEstacionesLineaMetroDTO(Route route) {

		List<EstacionLineaMetroDTO> estacionesDTO = new ArrayList<>();

		metroGTFS.findStopsByRoute(route).iterator().forEachRemaining(stop -> {
			estacionesDTO.add(obtenerEstacionLineaMetroDTO(stop));
		});

		return estacionesDTO;
	}
	
	public EstacionLineaMetroDTO obtenerEstacionLineaMetroDTO(Stop stop) {

		EstacionLineaMetroDTO estacionDTO = new EstacionLineaMetroDTO();

		estacionDTO.setCodigo(stop.getId().getId());
		estacionDTO.setDescripcion(stop.getName());
		estacionDTO.setLatitud(stop.getLat());
		estacionDTO.setLongitud(stop.getLon());
		estacionDTO.setActivo(ACTIVO);

		return estacionDTO;
	}
	
	public List<HorarioLineaMetroDTO> obtenerHorariosLineaMetroDTO(Route route) {
		List<HorarioLineaMetroDTO> horariosDTO = new ArrayList<>();
		horariosDTO.add(obtenerHorarioLineaMetroDTO(route));
		return horariosDTO;
	}
	
	public HorarioLineaMetroDTO obtenerHorarioLineaMetroDTO(Route route) {

		HorarioLineaMetroDTO horarioDTO = new HorarioLineaMetroDTO();

		ServiceCalendar service = store.getCalendarForId(1);

		horarioDTO.setActivo(String.valueOf(ACTIVO));
		horarioDTO.setHoraInicioOperacion(formatearSegundos(metroGTFS
				.getStopTimeByRouteAndServiceAndArrivalTime(route, service, MIN_SEGUNDO, null).getArrivalTime()));
		horarioDTO.setHoraFinOperacion(formatearSegundos(metroGTFS
				.getStopTimeByRouteAndServiceAndArrivalTime(route, service, MAX_SEGUNDO, null).getArrivalTime()));
		horarioDTO.setHoraInicioPicoAM(formatearSegundos(metroGTFS
				.getStopTimeByRouteAndServiceAndArrivalTime(route, service, MIN_SEGUNDO_PICO_AM, 1).getArrivalTime()));
		horarioDTO.setHoraFinPicoAM(formatearSegundos(metroGTFS
				.getStopTimeByRouteAndServiceAndArrivalTime(route, service, MAX_SEGUNDO_PICO_AM, 1).getArrivalTime()));
		horarioDTO.setHoraInicioPicoPM(formatearSegundos(metroGTFS
				.getStopTimeByRouteAndServiceAndArrivalTime(route, service, MIN_SEGUNDO_PICO_PM, 1).getArrivalTime()));
		horarioDTO.setHoraFinPicoPM(formatearSegundos(metroGTFS
				.getStopTimeByRouteAndServiceAndArrivalTime(route, service, MAX_SEGUNDO_PICO_PM, 1).getArrivalTime()));

		return horarioDTO;
	}
	
	public List<FrecuenciaLineaMetroDTO> obtenerFrecuenciasLineaMetroDTO(Route route, List<HorarioLineaMetroDTO> horarios) {
		List<FrecuenciaLineaMetroDTO> frecuencias = new ArrayList<>();
		for (HorarioLineaMetroDTO horarioDTO : horarios) {
			frecuencias.add(obtenerFrecuenciaLineaMetroDTO(route, horarioDTO));
		}

		return frecuencias;
	}
	
	public FrecuenciaLineaMetroDTO obtenerFrecuenciaLineaMetroDTO(Route route, HorarioLineaMetroDTO horarioDTO) {
		FrecuenciaLineaMetroDTO frecuenciaDTO = new FrecuenciaLineaMetroDTO();
		ServiceCalendar service = store.getCalendarForId(1);
		List<StopTime> stopTimes = metroGTFS.findStopTimeByRouteAndService(route, service);

		double maxPicoAM = 0;
		double maxPicoPM = 0;
		double maxDiurno = 0;
		double maxNocturno = 0;

		for (StopTime stopTime : stopTimes) {
			if (stopTime.getStopSequence() == 1) {
				int hora = stopTime.getArrivalTime();
				if (estaHoraEnRango(horaStringAInt(horarioDTO.getHoraInicioPicoAM()),
						horaStringAInt(horarioDTO.getHoraFinPicoAM()), hora)) {
					maxPicoAM++;
				}
				if (estaHoraEnRango(horaStringAInt(horarioDTO.getHoraInicioPicoPM()),
						horaStringAInt(horarioDTO.getHoraFinPicoPM()), hora)) {
					maxPicoPM++;
				}
				if (estaHoraEnRango(horaStringAInt(horarioDTO.getHoraInicioOperacion()), MITAD_DIA, hora)) {
					maxDiurno++;
				}
				if (estaHoraEnRango(MITAD_DIA, horaStringAInt(horarioDTO.getHoraFinOperacion()), hora)) {
					maxNocturno++;
				}
			}
		}

		frecuenciaDTO.setFrecuenciaMinimaPicoAM(maxPicoAM);
		frecuenciaDTO.setIntervaloMaximoPicoAM(maxPicoAM);
		frecuenciaDTO.setFrecuenciaMinimaPicoPM(maxPicoPM);
		frecuenciaDTO.setIntervaloMaximoPicoPM(maxPicoPM);
		frecuenciaDTO.setFrecuenciaMinimaValleDiurno(maxDiurno);
		frecuenciaDTO.setIntervaloMaximoValleDiurno(maxDiurno);
		frecuenciaDTO.setFrecuenciaMinimaValleNocturno(maxNocturno);
		frecuenciaDTO.setIntervaloMaximoValleNocturno(maxNocturno);

		return frecuenciaDTO;
	}
	
	// METODOS PARA OBTENER LA INFORMACION DE LAS RUTAS
	
	public List<RutaMetroDTO> obtenerRutasMetroDTO() {
		
		orientacionNorteDTO = new TipoOrientacionDTO();
		orientacionNorteDTO.setNombre("NORTE");
		orientacionNorteDTO.setDescripcion("Norte");
		
		List<RutaMetroDTO> rutasDTO = new ArrayList<>();

		for (Route route : routes) {
			if (route.getId().getId().contains("_")) {
				rutasDTO.add(obtenerRutaMetroDTO(route));
			}
		}

		return rutasDTO;
	}
	
	public RutaMetroDTO obtenerRutaMetroDTO(Route route) {

		List<Coordenada> coordenadas = toCoordenadas(route);
		List<ParaderoRutaMetroDTO> paraderosDTO = obtenerParaderosRutaMetroDTO(route);
		List<HorarioRutaMetroDTO> horariosDTO = obtenerHorariosRutaMetroDTO(route);
		List<FrecuenciaRutaMetroDTO> frecuenciasDTO = obtenerFrecuenciasRutaMetroDTO(route, horariosDTO);
		List<InfoViajesRutaDTO> infoViajesDTO = obtenerInfoViajesRuta(route);
		// Por ahora cada ruta o linea del metro esta asociada a una sola empresa
		// Pero hay que manejarlo como una lista ya que un ruta de GTPC puede manejar muchas empresas 
		List<EmpresaTransporteDTO> empresasTransporteDTO = Lists.newArrayList(obtenerEmpresaTransporteDTO(route.getAgency()));

		RutaMetroDTO rutaDTO = new RutaMetroDTO();
		rutaDTO.setCodigo(route.getId().getId());
		rutaDTO.setDescripcion(route.getLongName());
		rutaDTO.setLongitudRuta(1000.0);
		rutaDTO.setCoordenadas(GeometryUtil.obtenerLineString(coordenadas));
		rutaDTO.setPrimerPunto(coordenadas.isEmpty() ? null : GeometryUtil.obtenerPunto(coordenadas.get(0)));
		rutaDTO.setUltimoPunto(coordenadas.isEmpty() ? null : GeometryUtil.obtenerPunto(coordenadas.get(coordenadas.size() - 1)));
		rutaDTO.setTiempoEstimado(0l); // TODO por calcular a partir de stop_times
		rutaDTO.setModoRutaDTO(ModoRecorrido.valueOf(route.getType()));
		rutaDTO.setRutaActiva(String.valueOf(ACTIVO));
		rutaDTO.setParaderosDTO(paraderosDTO);
		rutaDTO.setHorariosDTO(horariosDTO);
		rutaDTO.setFrecuenciasDTO(frecuenciasDTO);
		rutaDTO.setInfoViajesDTO(infoViajesDTO);
		rutaDTO.setEmpresasTransporteDTO(empresasTransporteDTO);

		return rutaDTO;
	}
	
	public List<ParaderoRutaMetroDTO> obtenerParaderosRutaMetroDTO(Route route) {

		List<ParaderoRutaMetroDTO> paraderosDTO = new ArrayList<>();
		metroGTFS.findStopsByRoute(route).iterator().forEachRemaining(stop -> {
			paraderosDTO.add(obtenerParaderoRutaMetroDTO(stop));
		});
		
		return paraderosDTO;
	}
	
	public ParaderoRutaMetroDTO obtenerParaderoRutaMetroDTO(Stop stop) {

		ParaderoRutaMetroDTO paraderoDTO = new ParaderoRutaMetroDTO();

		paraderoDTO.setCodigo(stop.getId().getId());
		paraderoDTO.setDescripcion(stop.getName());
		paraderoDTO.setLatitud(stop.getLat());
		paraderoDTO.setLongitud(stop.getLon());
		// paradero.setTipoParadero(TIPO_PARADERO); // Es obligatorio pero no
		// hay de donde obtener esta informacion
		// paradero.setTipoOrientacion(TIPO_ORIENTACION); // Es obligatorio pero
		// no hay de donde obtener esta informacion
		paraderoDTO.setActivo(ACTIVO);
		paraderoDTO.setTipoOrientacionDTO(orientacionNorteDTO);

		return paraderoDTO;
	}
	
	public List<HorarioRutaMetroDTO> obtenerHorariosRutaMetroDTO(Route route) {
		List<HorarioRutaMetroDTO> horariosDTO = new ArrayList<>();
		horariosDTO.add(obtenerHorarioRutaMetroDTO(route));
		return horariosDTO;
	}
	
	public HorarioRutaMetroDTO obtenerHorarioRutaMetroDTO(Route route) {

		HorarioRutaMetroDTO horarioDTO = new HorarioRutaMetroDTO();

		ServiceCalendar service = store.getCalendarForId(1);

		horarioDTO.setActivo(String.valueOf(ACTIVO));
		horarioDTO.setHoraInicioOperacion(formatearSegundos(metroGTFS
				.getStopTimeByRouteAndServiceAndArrivalTime(route, service, MIN_SEGUNDO, null).getArrivalTime()));
		horarioDTO.setHoraFinOperacion(formatearSegundos(metroGTFS
				.getStopTimeByRouteAndServiceAndArrivalTime(route, service, MAX_SEGUNDO, null).getArrivalTime()));
		horarioDTO.setHoraInicioPicoAm(formatearSegundos(metroGTFS
				.getStopTimeByRouteAndServiceAndArrivalTime(route, service, MIN_SEGUNDO_PICO_AM, 1).getArrivalTime()));
		horarioDTO.setHoraFinPicoAm(formatearSegundos(metroGTFS
				.getStopTimeByRouteAndServiceAndArrivalTime(route, service, MAX_SEGUNDO_PICO_AM, 1).getArrivalTime()));
		horarioDTO.setHoraInicioPicoPm(formatearSegundos(metroGTFS
				.getStopTimeByRouteAndServiceAndArrivalTime(route, service, MIN_SEGUNDO_PICO_PM, 1).getArrivalTime()));
		horarioDTO.setHoraFinPicoPm(formatearSegundos(metroGTFS
				.getStopTimeByRouteAndServiceAndArrivalTime(route, service, MAX_SEGUNDO_PICO_PM, 1).getArrivalTime()));

		return horarioDTO;
	}
	
	public Long obtenerTiempoRecorrido(Route route) {
		ServiceCalendar service = store.getCalendarForId(1);
		List<StopTime> paradas = metroGTFS.findStopTimeByRouteAndService(route, service);
		Trip trip = paradas.get(0).getTrip();
		paradas = metroGTFS.findStopTimeByRouteServiceAndTrip(route, service, trip);
		Collections.sort(paradas,
				(p1, p2) -> Integer.valueOf(p1.getArrivalTime()).compareTo(Integer.valueOf(p2.getArrivalTime())));
		return Long
				.valueOf((paradas.get(paradas.size() - 1).getArrivalTime()) - (paradas.get(0).getArrivalTime()));
	}
	
	public List<FrecuenciaRutaMetroDTO> obtenerFrecuenciasRutaMetroDTO(Route route, List<HorarioRutaMetroDTO> horariosDTO) {
		List<FrecuenciaRutaMetroDTO> frecuenciasDTO = new ArrayList<>();
		for (HorarioRutaMetroDTO horarioDTO : horariosDTO) {
			frecuenciasDTO.add(obtenerFrecuenciaRutaMetroDTO(route, horarioDTO));
		}

		return frecuenciasDTO;
	}
	
	public FrecuenciaRutaMetroDTO obtenerFrecuenciaRutaMetroDTO(Route route, HorarioRutaMetroDTO horarioDTO) {
		FrecuenciaRutaMetroDTO frecuenciaDTO = new FrecuenciaRutaMetroDTO();
		ServiceCalendar service = store.getCalendarForId(1);// Calendario Laboral
		List<StopTime> stopTimes = metroGTFS.findStopTimeByRouteAndService(route, service);

		double maxPicoAM = 0;
		double maxPicoPM = 0;
		double maxDiurno = 0;
		double maxNocturno = 0;

		for (StopTime stopTime : stopTimes) {
			if (stopTime.getStopSequence() == 1) {
				int hora = stopTime.getArrivalTime();
				if (estaHoraEnRango(horaStringAInt(horarioDTO.getHoraInicioPicoAm()),
						horaStringAInt(horarioDTO.getHoraFinPicoAm()), hora)) {
					maxPicoAM++;
				}
				if (estaHoraEnRango(horaStringAInt(horarioDTO.getHoraInicioPicoPm()),
						horaStringAInt(horarioDTO.getHoraFinPicoPm()), hora)) {
					maxPicoPM++;
				}
				if (estaHoraEnRango(horaStringAInt(horarioDTO.getHoraInicioOperacion()), MITAD_DIA, hora)) {
					maxDiurno++;
				}
				if (estaHoraEnRango(MITAD_DIA, horaStringAInt(horarioDTO.getHoraFinOperacion()), hora)) {
					maxNocturno++;
				}
			}
		}

		frecuenciaDTO.setFrecuenciaMinimaPicoAm(maxPicoAM);
		frecuenciaDTO.setIntervaloMaximoPicoAm(maxPicoAM);
		frecuenciaDTO.setFrecuenciaMinimaPicoPm(maxPicoPM);
		frecuenciaDTO.setIntervaloMaximoPicoPm(maxPicoPM);
		frecuenciaDTO.setFrecuenciaMinimaValleDiurno(maxDiurno);
		frecuenciaDTO.setIntervaloMaximoValleDiurno(maxDiurno);
		frecuenciaDTO.setFrecuenciaMinimaValleNocturno(maxNocturno);
		frecuenciaDTO.setIntervaloMaximoValleNocturno(maxNocturno);

		return frecuenciaDTO;
	}
		
	public EmpresaTransporteDTO obtenerEmpresaTransporteDTO(Agency agency) {

		EmpresaTransporteDTO empresaTransporteDTO = new EmpresaTransporteDTO();
		empresaTransporteDTO.setNombre(agency.getName());
		empresaTransporteDTO.setActivo(String.valueOf(ACTIVO));

		return empresaTransporteDTO;
	}
	
	/**
	 * Si los viajes son de ida y vuelta por el mismo camino devuelve verdadero
	 * en caso contrario devuelve falso
	 * 
	 * @param trips
	 *            - viajes a validar
	 * @return <code>true</code> en caso de que la ruta regrese por el mismo
	 *         camino, caso contrario <code>false</code>
	 */
	private boolean tieneIdaYVuelta(Set<Trip> trips) {
		for (Trip trip: trips) {
			if (trip.getDirectionId().contains("1"))
				return true;
		}
		return false;
	}
	
	public List<InfoViajesRutaDTO> obtenerInfoViajesRuta(Route route) {
		
		InfoViajesRutaDTO infoViajeDTO = new InfoViajesRutaDTO();
		ServiceCalendar service = store.getCalendarForId(1);
		
		infoViajeDTO.setTiempoVuelta(obtenerTiempoRecorrido(route));
		infoViajeDTO.setHoraInicio(formatearSegundos(metroGTFS
				.getStopTimeByRouteAndServiceAndArrivalTime(route, service, MIN_SEGUNDO, null).getArrivalTime()));
		infoViajeDTO.setHoraFin(formatearSegundos(metroGTFS
				.getStopTimeByRouteAndServiceAndArrivalTime(route, service, MAX_SEGUNDO, null).getArrivalTime()));
		Set<Trip> trips = metroGTFS.findTripsByRouteAndService(route, service);
		infoViajeDTO.setNumSalidas((long)trips.size());
		infoViajeDTO.setIdaVuelta(tieneIdaYVuelta(trips));
		infoViajeDTO.setFrecuencia(obtenerPromedioFrecuenciaDeDespacho(route));
		
		return Lists.newArrayList(infoViajeDTO);
	}
	
	public List<InfoViajesLineaDTO> obtenerInfoViajesLinea(Route route) {
		
		InfoViajesLineaDTO infoViajeDTO = new InfoViajesLineaDTO();
		ServiceCalendar service = store.getCalendarForId(1);
		
		infoViajeDTO.setTiempoVuelta(obtenerTiempoRecorrido(route));
		infoViajeDTO.setHoraInicio(formatearSegundos(metroGTFS
				.getStopTimeByRouteAndServiceAndArrivalTime(route, service, MIN_SEGUNDO, null).getArrivalTime()));
		infoViajeDTO.setHoraFin(formatearSegundos(metroGTFS
				.getStopTimeByRouteAndServiceAndArrivalTime(route, service, MAX_SEGUNDO, null).getArrivalTime()));
		Set<Trip> trips = metroGTFS.findTripsByRouteAndService(route, service);
		infoViajeDTO.setNumSalidas((long)trips.size());
		infoViajeDTO.setIdaVuelta(tieneIdaYVuelta(trips));
		infoViajeDTO.setFrecuencia(obtenerPromedioFrecuenciaDeDespacho(route));
		
		return Lists.newArrayList(infoViajeDTO);
	}
	
	public long obtenerPromedioFrecuenciaDeDespacho(Route route) {

		ServiceCalendar service = store.getCalendarForId(1);
		List<StopTime> stopTimes = metroGTFS.findStopTimeByRouteAndService(route, service);
		// Ordenamos las paradas por tiempo de menor a mayor para calcular el promedio mejor
		Comparator<StopTime> ordenarPorTiempo = (p1, p2) -> ((Integer) p1.getArrivalTime())
				.compareTo(((Integer) p2.getArrivalTime()));
		Collections.sort(stopTimes, ordenarPorTiempo);
		// Recorremos el mapa para obtener la primera parada de cada viaje
		StopTime aux = null;
		int sumaDiferencias = 0;
		int numParadas = 0;
		for (StopTime parada : stopTimes) {
			if (parada.getStopSequence() == 1) {
				if (null != aux) {
					// Obtenemos la diferencia de tiempo entre las paradas
					int diferencia = parada.getArrivalTime() - aux.getArrivalTime();
					sumaDiferencias = sumaDiferencias + diferencia;
					aux = parada;
					numParadas++;
				} else {
					aux = parada;
				}
			}
		}
		// y por ultimo calculamos el promedio
		if (numParadas > 0)
			return sumaDiferencias / numParadas;
		else 
			return sumaDiferencias;
		
	}
	
	/**
	 * Lee el archivo GTFS para obtener la informacion de este
	 * 
	 * @param archivoGTFS
	 *            - URL o PATH donde se encuentra el archivo GTFS
	 */
	public void cargarInfoGTFS(File archivoGTFS) {
		try {
			GtfsReader reader = new GtfsReader();
			reader.setInputLocation(archivoGTFS);
			reader.addEntityHandler((EntityHandler) new GtfsEntityHandler());
			store = new GtfsRelationalDaoImpl();
			reader.setEntityStore(store);
			reader.run();

			routes = store.getAllRoutes();
			metroGTFS = new GtfsServiceImpl(store);

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
	
	private static boolean isURL(String arg) {
		return arg.startsWith("http");
	}
	
	/**
	 * Formatea un numero de segundos a un formato de hora hh:mm:ss
	 * @param totalSegundos - numero de segundos a formatear
	 * @return una cadena con la hora en formato HH:mm:ss
	 */
	public static String formatearSegundos(int totalSegundos) {
		int hora = totalSegundos / 3600;
		int minutos = (totalSegundos - (3600 * hora)) / 60;
		int segundos = totalSegundos - ((hora * 3600) + (minutos * 60));
		return String.format(PATRON_HORA, hora, minutos, segundos);
	}
	
	/**
	 * Convierte una cadena que contiene una hora a segundos 
	 * @param hora - cadena que contiene una hora en formato hh:mm:ss
	 * @return numero de segundos que representa la hora de la cadena
	 * @see MetroGtfsServiceImpl#formatearSegundos(int)
	 */
	private static int horaStringAInt(String hora) {
		Time horaTime = Time.valueOf(hora);
		return ((horaTime.getHours() * 3600) + (horaTime.getMinutes() * 60) + horaTime.getSeconds());
	}
	
	/**
	 * Verifica que una hora haga parte del rango de dos horas, todas estas
	 * definidas como parametros y representadas como segundos
	 * 
	 * @param rangoMenor - hora
	 * @param rangoMayor - hora
	 * @param hora - hora a validar
	 * @return <code>true</code> si esta dentro del rango de lo contrario <code>false</code>
	 */
	private static boolean estaHoraEnRango(int rangoMenor, int rangoMayor, int hora) {
		return (rangoMenor < hora && hora < rangoMayor);
	}
	
	private static class GtfsEntityHandler implements EntityHandler {

		public void handleEntity(Object bean) {
			if (bean instanceof Stop) {
				Stop stop = (Stop) bean;
			}
		}
	}

}
