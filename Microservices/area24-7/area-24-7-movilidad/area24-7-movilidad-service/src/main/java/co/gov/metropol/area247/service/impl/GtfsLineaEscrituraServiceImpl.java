package co.gov.metropol.area247.service.impl;

import static java.time.temporal.ChronoUnit.SECONDS;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.assertj.core.util.Sets;
import org.onebusaway.gtfs.model.Agency;
import org.onebusaway.gtfs.model.AgencyAndId;
import org.onebusaway.gtfs.model.Route;
import org.onebusaway.gtfs.model.ServiceCalendar;
import org.onebusaway.gtfs.model.ShapePoint;
import org.onebusaway.gtfs.model.Stop;
import org.onebusaway.gtfs.model.StopTime;
import org.onebusaway.gtfs.model.Trip;
import org.onebusaway.gtfs.model.calendar.ServiceDate;
import org.onebusaway.gtfs.serialization.GtfsWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vividsolutions.jts.geom.Coordinate;

import co.gov.metropol.area247.model.EstacionLineaMetroDTO;
import co.gov.metropol.area247.model.InfoViajesLineaDTO;
import co.gov.metropol.area247.model.LineaMetroDTO;
import co.gov.metropol.area247.service.IEstacionLineaMetroService;
import co.gov.metropol.area247.service.IGtfsLineaEscrituraService;
import co.gov.metropol.area247.service.ILineaMetroService;
import co.gov.metropol.area247.service.IViajesLineaService;
import co.gov.metropol.area247.util.constantes.Constantes;
import co.gov.metropol.area247.util.ex.Area247Exception;

@Service
public class GtfsLineaEscrituraServiceImpl implements IGtfsLineaEscrituraService {

	private static final String AGENCY_METRO = "METRO_MEDELLIN";
	private static final String CALENDARIO_LABORAL = "Laboral";
	private static final int MAX_IDA_Y_VUELTA = 2;
	private static final int DIA_ACTIVO = 1;
	

	@Autowired
	private ILineaMetroService lineaMetroService;

	@Autowired
	private IEstacionLineaMetroService estacionLineaMetroService;

	@Autowired
	private IViajesLineaService viajesLineaService;

	private List<LineaMetroDTO> lineasDTO;

	private Map<String, AgencyAndId> agenciasMap = new HashMap<>();
	private Agency agenciaMetroMedellin;
	private ServiceCalendar calendarioLaboralMetroMedellin;

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.gov.metropol.area247.service.IGtfsEscrituraService#escribirGTFS()
	 */
	@Override
	public void escribirGTFS() {

		GtfsWriter writer = new GtfsWriter();
		// TODO PARAMETRIZAR LA RUTA DONDE SE CREARA EL GTFS CON LA INFO DE LAS LINEAS DEL METRO
		writer.setOutputLocation(new File("C://GTFS/gtfs.zip"));

		writer.handleEntity(getCalendarioLaboralMetroMedellin());
		
		writer.handleEntity(getAgenciaMetroMedellin());

		obtenerRutas().iterator().forEachRemaining(linea -> 
			writer.handleEntity(linea)
		);

		obtenerParaderos().iterator().forEachRemaining(paradero -> {
			writer.handleEntity(paradero);
		});

		obtenerCoordenadas().iterator().forEachRemaining(coordenada -> {
			writer.handleEntity(coordenada);
		});

		Map<Trip, List<StopTime>> viajesConTiemposParada = obtenerViajesConTiemposParada();
		Set<Trip> viajes = viajesConTiemposParada.keySet();
		Collection<List<StopTime>> tiemposParadaCollection = viajesConTiemposParada.values();

		viajes.iterator().forEachRemaining(viaje -> {
			writer.handleEntity(viaje);
		});

		tiemposParadaCollection.iterator().forEachRemaining(tiemposParadaConjunto -> {
			tiemposParadaConjunto.iterator().forEachRemaining(tiemposParada -> {
				writer.handleEntity(tiemposParada);
			});
		});

		try {
			writer.close();
		} catch (IOException e) {
			throw new Area247Exception("Error al generar GTFS con la informacion de las lineas del metro");
		}

	}

	

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.gov.metropol.area247.service.IGtfsEscrituraService#obtenerRutas()
	 */
	@Override
	public Set<Route> obtenerRutas() {

		Set<Route> rutas = new HashSet<>();
		String codigo = null;

		try {

			lineasDTO = lineaMetroService.findAllActivas();

			for (LineaMetroDTO lineaDTO : lineasDTO) {
				Route ruta = new Route();
				codigo = lineaDTO.getCodigo();
				AgencyAndId agency = new AgencyAndId(AGENCY_METRO, codigo);
				agenciasMap.put(codigo, agency);
				ruta.setId(agency);
				ruta.setAgency(agenciaMetroMedellin);
				ruta.setShortName(codigo);
				ruta.setLongName(lineaDTO.getDescripcion());
				ruta.setDesc(lineaDTO.getDescripcion());
				ruta.setType(lineaDTO.getModoLinea().indice());
				rutas.add(ruta);
			}

		} catch (Area247Exception e) {

			throw new Area247Exception(e.getMessage());

		} catch (Exception e) {

			throw new Area247Exception(String.format("Error al convertir la linea %s a ruta GTFS", codigo));

		}

		return rutas;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.gov.metropol.area247.service.IGtfsEscrituraService#
	 * obtenerCoordenadasRuta()
	 */
	@Override
	public Set<ShapePoint> obtenerCoordenadas() {

		Comparator<ShapePoint> comparator = (sh1, sh2) -> sh1.toString().compareTo(sh2.toString());
		SortedSet<ShapePoint> shapePoints = new TreeSet<>(comparator);
		String codigo = null;

		try {

			if (null == lineasDTO)
				lineasDTO = lineaMetroService.findAllActivas();

			for (LineaMetroDTO lineaDTO : lineasDTO) {

				codigo = lineaDTO.getCodigo();
				Coordinate[] coordenadas = lineaDTO.getCoordenadas().getCoordinates();

				int posicion = 1;

				for (Coordinate coordenada : coordenadas) {
					ShapePoint shapePoint = new ShapePoint();
					AgencyAndId agencia = agenciasMap.computeIfAbsent(codigo,
							k -> new AgencyAndId(AGENCY_METRO, lineaDTO.getCodigo()));
					shapePoint.setShapeId(agencia);
					shapePoint.setLat(coordenada.x);
					shapePoint.setLon(coordenada.y);
					shapePoint.setSequence(posicion);
					shapePoints.add(shapePoint);
					posicion++;
				}
			}

		} catch (Area247Exception e) {

			throw new Area247Exception(e.getMessage());

		} catch (Exception e) {
			throw new Area247Exception(
					String.format("Error al convertir las coordenadas de la linea %s a coordenadas GTFS", codigo));
		}

		return shapePoints;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.gov.metropol.area247.service.IGtfsEscrituraService#obtenerParaderos()
	 */
	@Override
	public Set<Stop> obtenerParaderos() {

		Set<Stop> paraderos = new HashSet<>();
		String codigo = null;

		try {

			List<EstacionLineaMetroDTO> paraderosDTO = estacionLineaMetroService.getAllActivas();

			for (EstacionLineaMetroDTO paraderoDTO : paraderosDTO) {

				Stop paradero = new Stop();
				codigo = paraderoDTO.getCodigo();
				paradero.setId(new AgencyAndId("", codigo));
				paradero.setCode(codigo);
				paradero.setName(paraderoDTO.getDescripcion());
				paradero.setDesc(paraderoDTO.getDescripcion());
				paradero.setLat(paraderoDTO.getLatitud());
				paradero.setLon(paraderoDTO.getLongitud());
				paradero.setPlatformCode("1");
				paraderos.add(paradero);

			}

		} catch (Exception e) {

			throw new Area247Exception(
					String.format("Error al convertir el paradero %s del metro a paradero GTFS", codigo));

		}

		return paraderos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.gov.metropol.area247.service.IGtfsEscrituraService#
	 * obtenerViajesConTiemposParada()
	 */
	@Override
	public Map<Trip, List<StopTime>> obtenerViajesConTiemposParada() {
		// Ordenamos los viajes segun el id del trip e.g NIQSTA1, NIQSTA2...
		Comparator<Trip> viajesComparator = (v1,v2) -> v1.getId().getId().compareTo(v2.getId().getId());
		Map<Trip, List<StopTime>> viajesConTiemposParada = new TreeMap<>(viajesComparator);

		String codigo = null;

		try {

			if (null == lineasDTO)
				lineasDTO = lineaMetroService.findAllActivas();

			for (LineaMetroDTO lineaDTO : lineasDTO) {

				codigo = lineaDTO.getCodigo();
				InfoViajesLineaDTO viajesDTO = viajesLineaService.findByIdLinea(lineaDTO.getId()).get(0);
				int numVecesCiclo = 0;

				List<EstacionLineaMetroDTO> estaciones = estacionLineaMetroService.findByIdLinea(lineaDTO.getId(),
						false);
				// TODO INSERTAR CAMPO SECUENCIA EN TABLA T247VIA_ESTACION_METRO
				//  Eliminar esta ordenacion cuando se realize la ordenacion
				// por secuencia desde la base de datos
				Collections.sort(estaciones, new Comparator() {
					@Override
					public int compare(Object o1, Object o2) {
						EstacionLineaMetroDTO estacion1 = (EstacionLineaMetroDTO) o1;
						EstacionLineaMetroDTO estacion2 = (EstacionLineaMetroDTO) o2;
						return estacion1.getSequencia().compareTo(estacion2.getSequencia());
					}
				});

				EstacionLineaMetroDTO estacionOrigen = estaciones.get(0);
				EstacionLineaMetroDTO estacionDestino = estaciones.get(estaciones.size() - 1);

				long despachos = viajesDTO.getNumSalidas();
				String claveViaje = estacionOrigen.getCodigo().concat(estacionDestino.getCodigo());
				String nombreRuta = estacionDestino.getDescripcion();
				LocalTime inicio = LocalTime.parse(viajesDTO.getHoraInicio());

				while (numVecesCiclo < MAX_IDA_Y_VUELTA) {
					for (long i = 1; i <= despachos; i++) {
						Trip viaje = new Trip();
						Route route = new Route();
						route.setId(agenciasMap.get(lineaDTO.getCodigo()));
						viaje.setRoute(route);
						viaje.setRouteShortName(lineaDTO.getCodigo());
						viaje.setServiceId(new AgencyAndId("Dia", "Laboral"));
						viaje.setId(new AgencyAndId(String.class.newInstance(), claveViaje.concat(String.valueOf(i))));
						viaje.setTripHeadsign(nombreRuta);
						viaje.setDirectionId(String.valueOf(numVecesCiclo));
						viaje.setShapeId(new AgencyAndId(lineaDTO.getCodigo(), String.valueOf(numVecesCiclo)));
						LocalTime fin = inicio.plusSeconds(viajesDTO.getTiempoVuelta());
						List<StopTime> timposDeParadas = obtenerTiemposParada(viaje, inicio, fin, estaciones);
						viajesConTiemposParada.put(viaje, timposDeParadas);
					}

					if (viajesDTO.isIdaVuelta()) {
						claveViaje = estacionDestino.getCodigo().concat(estacionOrigen.getCodigo());
						nombreRuta = estacionOrigen.getDescripcion();
						numVecesCiclo++;
					}

				}

			}

		} catch (Exception e) {
			throw new Area247Exception(
					String.format("Error al convertir los viajes y tiempos de parada de la linea %s a GTFS", codigo));
		}

		return viajesConTiemposParada;
	}

	private List<StopTime> obtenerTiemposParada(final Trip viaje, final LocalTime inicio, final LocalTime fin,
			final List<EstacionLineaMetroDTO> estaciones) {

		List<StopTime> tiemposParada = new ArrayList<>();
		long tiempoViaje = (inicio.until(fin, SECONDS) / (estaciones.size()));
		LocalTime horaInicio = inicio;

		for (EstacionLineaMetroDTO estacion : estaciones) {
			int tiempoEnSegundos = horaInicio.toSecondOfDay();
			StopTime tiempoParada = new StopTime();
			tiempoParada.setTrip(viaje);
			tiempoParada.setArrivalTime(tiempoEnSegundos);
			tiempoParada.setDepartureTime(tiempoEnSegundos);
			Stop stop = new Stop();
			stop.setId(new AgencyAndId("", estacion.getCodigo()));
			tiempoParada.setStop(stop);
			tiempoParada.setStopHeadsign(estacion.getDescripcion());
			tiempoParada.setStopSequence(estacion.getSequencia().intValue());
			tiemposParada.add(tiempoParada);
			horaInicio = horaInicio.plusSeconds(tiempoViaje);
		}

		return tiemposParada;
	}

	@Override
	public Set<Agency> obtenerAgencias() {
		return Sets.newLinkedHashSet(getAgenciaMetroMedellin());
	}
	
	@Override
	public Collection<ServiceCalendar> obtenerCalendarios() {
		List<ServiceCalendar> calendario = new ArrayList<>();
		calendario.add(getCalendarioLaboralMetroMedellin());
		return calendario;
	}

	private Agency getAgenciaMetroMedellin() {

		if (null == agenciaMetroMedellin) {
			agenciaMetroMedellin = new Agency();
			agenciaMetroMedellin.setId(AGENCY_METRO);
			agenciaMetroMedellin.setName("Metro de Medellin");
			agenciaMetroMedellin.setUrl("http://www.metrodemedellin.gov.co");
			agenciaMetroMedellin.setTimezone(Constantes.TIME_ZONE);
		}

		return agenciaMetroMedellin;
	}
	
	private ServiceCalendar getCalendarioLaboralMetroMedellin() {
		if (null == calendarioLaboralMetroMedellin) {
			calendarioLaboralMetroMedellin = new ServiceCalendar();
			calendarioLaboralMetroMedellin.setServiceId(new AgencyAndId(AGENCY_METRO, CALENDARIO_LABORAL));
			calendarioLaboralMetroMedellin.setMonday(DIA_ACTIVO);
			calendarioLaboralMetroMedellin.setTuesday(DIA_ACTIVO);
			calendarioLaboralMetroMedellin.setWednesday(DIA_ACTIVO);
			calendarioLaboralMetroMedellin.setThursday(DIA_ACTIVO);
			calendarioLaboralMetroMedellin.setFriday(DIA_ACTIVO);
			calendarioLaboralMetroMedellin.setSaturday(DIA_ACTIVO);
			calendarioLaboralMetroMedellin.setSunday(DIA_ACTIVO);
			calendarioLaboralMetroMedellin.setStartDate(new ServiceDate(Calendar.getInstance().get(Calendar.YEAR), 1, 1));
			calendarioLaboralMetroMedellin.setEndDate(new ServiceDate(Calendar.getInstance().get(Calendar.YEAR), 12, 31));
		}
		
		return calendarioLaboralMetroMedellin;
	}



	@Override
	public Set<Trip> obtenerViajes() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Set<StopTime> obtenerTiemposDeParada() {
		// TODO Auto-generated method stub
		return null;
	}

}
