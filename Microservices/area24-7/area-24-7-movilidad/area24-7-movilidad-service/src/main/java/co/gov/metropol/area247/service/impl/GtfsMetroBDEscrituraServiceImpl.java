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

import co.gov.metropol.area247.model.EmpresaTransporteDTO;
import co.gov.metropol.area247.model.InfoViajesRutaDTO;
import co.gov.metropol.area247.model.ParaderoRutaMetroDTO;
import co.gov.metropol.area247.model.RutaMetroDTO;
import co.gov.metropol.area247.service.IEmpresaTransporteService;
import co.gov.metropol.area247.service.IGtfsEscrituraService;
import co.gov.metropol.area247.service.IParaderoRutaService;
import co.gov.metropol.area247.service.IRutaMetroService;
import co.gov.metropol.area247.service.IViajesRutaService;
import co.gov.metropol.area247.util.Utils;
import co.gov.metropol.area247.util.constantes.Constantes;
import co.gov.metropol.area247.util.ex.Area247Exception;

/**
 * Esta implementacion crea un archivo GTFS a partir de la informacion que se
 * encuentra persistida en base de datos.
 */
@Service("gtfsMetroBDEscrituraService")
public class GtfsMetroBDEscrituraServiceImpl implements IGtfsEscrituraService {

	private static final int DIA_ACTIVO = 1;
	private static final String CALENDARIO_LABORAL = "Laboral";
	private static final int MAX_IDA_Y_VUELTA = 2;
	
	@Autowired
	private IEmpresaTransporteService empresaTransporteService;
	@Autowired
	private IParaderoRutaService paraderoRutaService;
	@Autowired
	private IRutaMetroService rutaMetroService;
	@Autowired
	private IViajesRutaService viajesRutaService;

	private List<RutaMetroDTO> rutasDTO;

	private Map<Long, Agency> mapAgencias = new HashMap<>();
	private Map<String, AgencyAndId> mapAgenciasYId = new HashMap<>();
	private ServiceCalendar calendarioLaboralRutas;

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.gov.metropol.area247.service.IGtfsEscrituraService#escribirGTFS()
	 */
	@Override
	public void escribirGTFS() {
		GtfsWriter writer = new GtfsWriter();
		// TODO PARAMETRIZAR LA RUTA DONDE SE CREARA EL GTFS CON LA INFO DE LAS LINEAS DEL METRO
		writer.setOutputLocation(new File("C://GTFS/gtfsRutas.zip"));

		writer.handleEntity(getCalendarioLaboralRutas());
		
		obtenerAgencias().iterator().forEachRemaining(agencia -> {
			writer.handleEntity(agencia);
		});
		
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
			throw new Area247Exception("Error al generar GTFS con la informacion de las rutas del metro y GTPC");
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
			// Cargamos las rutas del metro y GTPC
			getRutasDTO();
			// Cargamos las agencias en caso de que no hayan sido cargadas
			getMapAgencias();

			for (RutaMetroDTO rutaDTO : rutasDTO) {
				codigo = rutaDTO.getCodigo();
				List<EmpresaTransporteDTO> empresasTransporteDTO = empresaTransporteService
						.findByIdRuta(rutaDTO.getId());
				Long idEmpresa = empresasTransporteDTO.get(0).getId();
				Agency agencia = mapAgencias.get(idEmpresa);
				AgencyAndId agencyAndId = new AgencyAndId(idEmpresa.toString(), codigo);
				mapAgenciasYId.put(codigo, agencyAndId);
				Route ruta = new Route();
				ruta.setId(agencyAndId);
				ruta.setAgency(agencia);
				ruta.setShortName(codigo);
				ruta.setLongName(rutaDTO.getDescripcion());
				ruta.setDesc(rutaDTO.getDescripcion());
				ruta.setType(rutaDTO.getModoRutaDTO().indice());
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
	 * @see
	 * co.gov.metropol.area247.service.IGtfsEscrituraService#obtenerParaderos()
	 */
	@Override
	public Set<Stop> obtenerParaderos() {

		Set<Stop> paraderos = new HashSet<>();
		String codigo = null;

		try {

			List<ParaderoRutaMetroDTO> paraderosDTO = paraderoRutaService.findAllActivas();

			for (ParaderoRutaMetroDTO paraderoDTO : paraderosDTO) {

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
					String.format("Error al convertir el paradero %s del metro o GTPC a paradero GTFS", codigo));
		}

		return paraderos;
		
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

			if (Utils.isEmpty(rutasDTO)) {
				// Cargamos rutas, agencias y agenciasYIds
				obtenerRutas();
			}
			
			for (RutaMetroDTO rutaDTO : rutasDTO) {

				codigo = rutaDTO.getCodigo();
				Coordinate[] coordenadas = rutaDTO.getCoordenadas().getCoordinates();

				int posicion = 1;

				for (Coordinate coordenada : coordenadas) {
					ShapePoint shapePoint = new ShapePoint();
					AgencyAndId agencia = mapAgenciasYId.get(codigo);
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
					String.format("Error al convertir las coordenadas de la ruta %s a coordenadas GTFS", codigo));

		}

		return shapePoints;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.gov.metropol.area247.service.IGtfsEscrituraService#
	 * obtenerViajesConTiemposParada()
	 */
	@Override
	public Map<Trip, List<StopTime>> obtenerViajesConTiemposParada() {
		Comparator<Trip> viajesComparator = (v1,v2) -> v1.getId().getId().compareTo(v2.getId().getId());
		Map<Trip, List<StopTime>> viajesConTiemposParada = new TreeMap<>(viajesComparator);

		String codigo = null;

		try {

			if (Utils.isEmpty(rutasDTO))
				obtenerRutas();

			for (RutaMetroDTO rutaDTO : rutasDTO) {

				codigo = rutaDTO.getCodigo();
				List<InfoViajesRutaDTO> infoViajesDTO = viajesRutaService.findByIdRuta(rutaDTO.getId()); 
				InfoViajesRutaDTO viajesDTO = infoViajesDTO.get(0);
				int numVecesCiclo = 0;

				List<ParaderoRutaMetroDTO> paraderos = paraderoRutaService.findByIdRuta(rutaDTO.getId(),
						false);
				// TODO INSERTAR CAMPO SECUENCIA EN TABLA T247VIA_ESTACION_METRO
				//  Eliminar esta ordenacion cuando se realize la ordenacion
				// por secuencia desde la base de datos
				Collections.sort(paraderos, new Comparator() {
					@Override
					public int compare(Object o1, Object o2) {
						ParaderoRutaMetroDTO paradero1 = (ParaderoRutaMetroDTO) o1;
						ParaderoRutaMetroDTO paradero2 = (ParaderoRutaMetroDTO) o2;
						return paradero1.getSequencia().compareTo(paradero2.getSequencia());
					}
				});

				ParaderoRutaMetroDTO paraderoOrigen = paraderos.get(0);
				ParaderoRutaMetroDTO paraderoDestino = paraderos.get(paraderos.size() - 1);

				long despachos = viajesDTO.getNumSalidas();
				String claveViaje = paraderoOrigen.getCodigo().concat(paraderoDestino.getCodigo());
				String nombreRuta = paraderoDestino.getDescripcion();
				LocalTime inicio = LocalTime.parse(viajesDTO.getHoraInicio());

				while (numVecesCiclo < MAX_IDA_Y_VUELTA) {
					for (long i = 1; i <= despachos; i++) {
						Trip viaje = new Trip();
						Route route = new Route();
						route.setId(mapAgenciasYId.get(rutaDTO.getCodigo()));
						viaje.setRoute(route);
						viaje.setRouteShortName(rutaDTO.getCodigo());
						viaje.setServiceId(new AgencyAndId("Dia", "Laboral"));
						viaje.setId(new AgencyAndId(String.class.newInstance(), claveViaje.concat(String.valueOf(i))));
						viaje.setTripHeadsign(nombreRuta);
						viaje.setDirectionId(String.valueOf(numVecesCiclo));
						viaje.setShapeId(new AgencyAndId(rutaDTO.getCodigo(), String.valueOf(numVecesCiclo)));
						LocalTime fin = inicio.plusSeconds(viajesDTO.getTiempoVuelta());
						List<StopTime> timposDeParadas = obtenerTiemposParada(viaje, inicio, fin, paraderos);
						viajesConTiemposParada.put(viaje, timposDeParadas);
					}

					if (viajesDTO.isIdaVuelta()) {
						claveViaje = paraderoDestino.getCodigo().concat(paraderoOrigen.getCodigo());
						nombreRuta = paraderoOrigen.getDescripcion();
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
			final List<ParaderoRutaMetroDTO> paraderos) {

		List<StopTime> tiemposParada = new ArrayList<>();
		long tiempoViaje = (inicio.until(fin, SECONDS) / (paraderos.size()));
		LocalTime horaInicio = inicio;

		for (ParaderoRutaMetroDTO paradero : paraderos) {
			int tiempoEnSegundos = horaInicio.toSecondOfDay();
			StopTime tiempoParada = new StopTime();
			tiempoParada.setTrip(viaje);
			tiempoParada.setArrivalTime(tiempoEnSegundos);
			tiempoParada.setDepartureTime(tiempoEnSegundos);
			Stop stop = new Stop();
			stop.setId(new AgencyAndId("", paradero.getCodigo()));
			tiempoParada.setStop(stop);
			tiempoParada.setStopHeadsign(paradero.getDescripcion());
			tiempoParada.setStopSequence(paradero.getSequencia().intValue());
			tiemposParada.add(tiempoParada);
			horaInicio = horaInicio.plusSeconds(tiempoViaje);
		}

		return tiemposParada;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.gov.metropol.area247.service.IGtfsEscrituraService#obtenerAgencia()
	 */
	@Override
	public Set<Agency> obtenerAgencias() {
		try {
			return new HashSet<>(getMapAgencias().values());
		} catch (Exception e) {
			throw new Area247Exception("Error al convertir los tipos de sistemas a agencias GTFS");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * co.gov.metropol.area247.service.IGtfsEscrituraService#obtenerCalendario()
	 */
	@Override
	public Collection<ServiceCalendar> obtenerCalendarios() {
		List<ServiceCalendar> calendario = new ArrayList<>();
		calendario.add(getCalendarioLaboralRutas());
		return calendario;
	}

	private Map<Long, Agency> getMapAgencias() {
		if (mapAgencias.isEmpty()) {

			List<EmpresaTransporteDTO> empresasTransporteDTO = empresaTransporteService.findAllActivas();

			for (EmpresaTransporteDTO empresaTransporteDTO : empresasTransporteDTO) {
				Agency agencia = new Agency();
				agencia.setId(empresaTransporteDTO.getId().toString());
				agencia.setName(empresaTransporteDTO.getNombre());
				agencia.setUrl("http://www.google.com");
				agencia.setTimezone(Constantes.TIME_ZONE);
				mapAgencias.put(empresaTransporteDTO.getId(), agencia);
			}
		}

		return mapAgencias;
	}
	
	private List<RutaMetroDTO> getRutasDTO() {
		if (Utils.isEmpty(rutasDTO)) {
			rutasDTO = rutaMetroService.findAllActivas();			
		}
		return rutasDTO;
	}
	
	private ServiceCalendar getCalendarioLaboralRutas() {
		if (null == calendarioLaboralRutas) {
			calendarioLaboralRutas = new ServiceCalendar();
			calendarioLaboralRutas.setServiceId(new AgencyAndId("", CALENDARIO_LABORAL));
			calendarioLaboralRutas.setMonday(DIA_ACTIVO);
			calendarioLaboralRutas.setTuesday(DIA_ACTIVO);
			calendarioLaboralRutas.setWednesday(DIA_ACTIVO);
			calendarioLaboralRutas.setThursday(DIA_ACTIVO);
			calendarioLaboralRutas.setFriday(DIA_ACTIVO);
			calendarioLaboralRutas.setSaturday(DIA_ACTIVO);
			calendarioLaboralRutas.setSunday(DIA_ACTIVO);
			calendarioLaboralRutas.setStartDate(new ServiceDate(Calendar.getInstance().get(Calendar.YEAR), 1, 1));
			calendarioLaboralRutas.setEndDate(new ServiceDate(Calendar.getInstance().get(Calendar.YEAR), 12, 31));
		}
		
		return calendarioLaboralRutas;
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
