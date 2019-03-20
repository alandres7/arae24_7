package co.gov.metropol.area247.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

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

import co.gov.metropol.area247.gateway.IGtpcServiceGateway;
import co.gov.metropol.area247.model.EmpresaTransporteDTO;
import co.gov.metropol.area247.model.ParaderoRutaMetroDTO;
import co.gov.metropol.area247.model.RutaMetroDTO;
import co.gov.metropol.area247.service.IEmpresaTransporteService;
import co.gov.metropol.area247.service.IGtfsEscrituraService;
import co.gov.metropol.area247.service.IParaderoRutaService;
import co.gov.metropol.area247.service.IRutaMetroService;
import co.gov.metropol.area247.service.ITipoParametroService;
import co.gov.metropol.area247.services.rest.gtpc.TiempoParadaGtpcWSDTO;
import co.gov.metropol.area247.services.rest.gtpc.ViajesGtpcWSDTO;
import co.gov.metropol.area247.util.Utils;
import co.gov.metropol.area247.util.constantes.Constantes;
import co.gov.metropol.area247.util.constantes.Constantes.Gtpc;
import co.gov.metropol.area247.util.constantes.Constantes.TipoParametro;
import co.gov.metropol.area247.util.ex.Area247Exception;

@Service("gtfsRutaGtpcEscrituraService")
public class GtfsRutaGtpcEscrituraServiceImpl implements IGtfsEscrituraService {

	private static final int DIA_ACTIVO = 1;
	private static final String CALENDARIO_LABORAL = "Laboral";
	private static final String ANONIMA = "NA";
	
	@Autowired
	private IEmpresaTransporteService empresaTransporteService;
	@Autowired
	private IParaderoRutaService paraderoRutaService;
	@Autowired
	private IRutaMetroService rutaMetroService;
	@Autowired
	private IGtpcServiceGateway gtpcServiceGateway;
	@Autowired
	private ITipoParametroService tipoParametroService;

	private List<RutaMetroDTO> rutasDTO;
	private Set<Route> rutas;

	private Map<Long, Agency> mapAgencias = new HashMap<>();
	private Map<String, AgencyAndId> mapAgenciasYId = new HashMap<>();
	private ServiceCalendar calendarioLaboralRutas;
	private Map<Trip, List<StopTime>> viajesConTiemposDeParada = new HashMap<>();
	List<ViajesGtpcWSDTO> viajesWSDTO = new ArrayList<>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.gov.metropol.area247.service.IGtfsEscrituraService#escribirGTFS()
	 */
	@Override
	public void escribirGTFS() {

		try {

			GtfsWriter writer = new GtfsWriter();
			
			String url = tipoParametroService.obtenerValorCadena(TipoParametro.URL_FOLDER_OTP);
			
			Calendar c = Calendar.getInstance();
			SimpleDateFormat formato = new SimpleDateFormat("ddMMyyyy");
			
			String urlAbsoluta = url + formato.format(c.getTime()) + ".zip";
			
			writer.setOutputLocation(new File(urlAbsoluta));

			writer.handleEntity(getCalendarioLaboralRutas());

			obtenerAgencias().iterator().forEachRemaining(agencia -> {
				writer.handleEntity(agencia);
			});

			obtenerRutas().iterator().forEachRemaining(linea -> writer.handleEntity(linea));

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

			writer.close();
			viajesWSDTO.clear();
			
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

		rutas = new HashSet<>();
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
				Long idEmpresa = null;
				if (Utils.isNotEmpty(empresasTransporteDTO)) {
					idEmpresa = empresasTransporteDTO.get(0).getId();
				} else {
					idEmpresa = Long.MIN_VALUE;
				}
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

			List<ParaderoRutaMetroDTO> paraderosDTO = paraderoRutaService.findByFuenteDatos(2);

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
					String.format("Error al convertir el paradero %s GTPC a paradero GTFS", codigo));
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
	public List<ShapePoint> obtenerCoordenadas() {
//		Comparator<ShapePoint> comparator = (sh1, sh2) -> {
//			int comparacion1 = sh1.toString().compareTo(sh2.toString());
//			if (comparacion1 != 0) {
//				return comparacion1;
//			}
//			return Integer.valueOf(sh1.getSequence()).compareTo(Integer.valueOf(sh2.getSequence()));
//			};
			
		List<ShapePoint> shapePoints = new ArrayList<>();
		String codigo = null;

		try {

			if (Utils.isEmpty(rutasDTO)) {
				// Cargamos rutas, agencias y agenciasYIds
				obtenerRutas();
			}

			for (RutaMetroDTO rutaDTO : rutasDTO) {

				codigo = rutaDTO.getCodigo();
				AgencyAndId agencia = mapAgenciasYId.get(codigo);
				if (!Utils.isNull(agencia)) {

					Coordinate[] coordenadas = rutaDTO.getCoordenadas().getCoordinates();
					int posicion = 1;

					for (Coordinate coordenada : coordenadas) {
						ShapePoint shapePoint = new ShapePoint();
						shapePoint.setShapeId(agencia);
						shapePoint.setLat(coordenada.x);
						shapePoint.setLon(coordenada.y);
						shapePoint.setSequence(posicion);
						shapePoints.add(shapePoint);
						posicion++;
					}
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

	@Override
	public Collection<Trip> obtenerViajes() {
		if (viajesConTiemposDeParada.isEmpty()) {
			obtenerViajesConTiemposParada();
		}
		return viajesConTiemposDeParada.keySet();
	}

	@Override
	public Collection<StopTime> obtenerTiemposDeParada() {
		
		List<StopTime> tiemposDeParada = new ArrayList<>();
		
		if (viajesConTiemposDeParada.isEmpty()) {
			obtenerViajesConTiemposParada();
		}
		
		for (List<StopTime> tiemposDeParadaAux : viajesConTiemposDeParada.values()) {
			tiemposDeParada.addAll(tiemposDeParadaAux);
		}
		
		return tiemposDeParada;
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

			cargarInformacionViajesRuta();			
			
			int i = 0;
			
			for (ViajesGtpcWSDTO viajeWSDTO : viajesWSDTO) {
				
				Route route = buscarPorCodigo(viajeWSDTO.getCodigoRuta());
				
				if (!Utils.isNull(route)) {
					Trip viaje = new Trip();	
					viaje.setRoute(route);
					viaje.setRouteShortName(viajeWSDTO.getCodigoRuta());
					viaje.setServiceId(new AgencyAndId("Dia", "Laboral"));
					viaje.setId(new AgencyAndId(String.class.newInstance(), viajeWSDTO.getParaderoInicio()
							.concat(viajeWSDTO.getParaderoFin().concat(String.valueOf(i)))));
					viaje.setTripHeadsign(viajeWSDTO.getParaderoFin());
					viaje.setDirectionId(String.valueOf(0));
					viaje.setShapeId(new AgencyAndId(viajeWSDTO.getCodigoRuta(), String.valueOf(0)));
					
					List<StopTime> tiemposParada = new ArrayList<>();
					
					int j = 1;
					
					// Ordenamos la lista por hora de llegada
					viajeWSDTO.getTiempoParada().sort(Comparator.comparing(TiempoParadaGtpcWSDTO::getTiempoInInt));				
					
					for (TiempoParadaGtpcWSDTO tiempoParadaWSDTO : viajeWSDTO.getTiempoParada()) {
						
						StopTime tiempoParada = new StopTime();
						tiempoParada.setTrip(viaje);
						tiempoParada.setArrivalTime(tiempoParadaWSDTO.getTiempoInInt());
						tiempoParada.setDepartureTime(tiempoParadaWSDTO.getTiempoOutInt());
						Stop stop = new Stop();
						stop.setId(new AgencyAndId("", tiempoParadaWSDTO.getParadero()));
						tiempoParada.setStop(stop);
						tiempoParada.setStopHeadsign(tiempoParadaWSDTO.getParadero());
						tiempoParada.setStopSequence(j);
						tiemposParada.add(tiempoParada);
						j++;
					}
					
					viajesConTiemposParada.put(viaje, tiemposParada);
					i++;
				}
				
			}

		} catch (Exception e) {
			throw new Area247Exception(
					String.format("Error al convertir los viajes y tiempos de parada de la linea %s a GTFS", codigo));
		}

		viajesConTiemposDeParada = viajesConTiemposParada;
		
		return viajesConTiemposParada;
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
				agencia.setName(empresaTransporteDTO.getNombre().trim());
				agencia.setUrl("http://www.google.com");
				agencia.setTimezone(Constantes.TIME_ZONE);
				mapAgencias.put(empresaTransporteDTO.getId(), agencia);
			}
			
			Agency anonima = new Agency();
			anonima.setId(String.valueOf(Long.MIN_VALUE));
			anonima.setName(ANONIMA);
			anonima.setUrl("http://www.google.com");
			anonima.setTimezone(Constantes.TIME_ZONE);
			
			mapAgencias.put(Long.MIN_VALUE, anonima);
		}

		return mapAgencias;
	}
	
	private List<RutaMetroDTO> getRutasDTO() {
		if (Utils.isEmpty(rutasDTO)) {

			cargarInformacionViajesRuta();

			Set<String> codigos = new HashSet<>();

			for (ViajesGtpcWSDTO wsdto : viajesWSDTO) {
				if (Utils.isNotEmpty(wsdto.getTiempoParada()) && wsdto.getTiempoParada().size() > 1) {
					codigos.add(wsdto.getCodigoRuta());
				}
			}

			rutasDTO = rutaMetroService.findByCodigoAndFuenteDatos(new ArrayList<>(codigos), Gtpc.FUENTE_DATOS);
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
	
	private Route buscarPorCodigo(String codigo) {
		if (Utils.isEmpty(rutas))
			obtenerRutas();
		
		for (Route route : rutas) {
			if (route.getShortName().equals(codigo)) 
				return route;
		}
		
		return null;
	}
	
	private void cargarInformacionViajesRuta() {
		if (viajesWSDTO.isEmpty()) {
			Calendar c = Calendar.getInstance();
			c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
			c.add(Calendar.WEEK_OF_YEAR, -1);
			SimpleDateFormat format1 = new SimpleDateFormat("ddMMyyyy");
			String fecha = format1.format(c.getTime());
			viajesWSDTO = gtpcServiceGateway.cargarInformacionViajesRuta(fecha);
		}
	}

}
