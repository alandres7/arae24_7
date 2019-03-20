package co.gov.metropol.area247.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;

import co.gov.metropol.area247.annotations.LogReturnValue;
import co.gov.metropol.area247.logging.LoggingUtil;
import co.gov.metropol.area247.model.CiclorutaDTO;
import co.gov.metropol.area247.model.EstacionLineaMetroDTO;
import co.gov.metropol.area247.model.LineaMetroDTO;
import co.gov.metropol.area247.model.ParaderoRutaDTO;
import co.gov.metropol.area247.model.ParaderoRutaMetroDTO;
import co.gov.metropol.area247.model.PuntoTarjetaCivicaDTO;
import co.gov.metropol.area247.model.RutaConcretaDTO;
import co.gov.metropol.area247.model.RutaMetroDTO;
import co.gov.metropol.area247.model.TareviaEstacionEnciclaDTO;
import co.gov.metropol.area247.model.mapper.RutaConcretaMapper;
import co.gov.metropol.area247.model.mapper.RutaMetroMapper;
import co.gov.metropol.area247.repository.RutaRepository;
import co.gov.metropol.area247.repository.custom.RutaRepositoryCustom;
import co.gov.metropol.area247.repository.domain.Ruta;
import co.gov.metropol.area247.repository.domain.support.enums.ClasificacionInformacion;
import co.gov.metropol.area247.repository.domain.support.enums.ModoRecorrido;
import co.gov.metropol.area247.service.IEmpresaTransporteService;
import co.gov.metropol.area247.service.IEnciclaService;
import co.gov.metropol.area247.service.IEstacionLineaMetroService;
import co.gov.metropol.area247.service.IFrecuenciaRutaMetroService;
import co.gov.metropol.area247.service.IGtpcService;
import co.gov.metropol.area247.service.IHorarioRutaService;
import co.gov.metropol.area247.service.IParaderoRutaService;
import co.gov.metropol.area247.service.IPuntoTarjetaCivicaService;
import co.gov.metropol.area247.service.IRutaService;
import co.gov.metropol.area247.service.ITipoRutaService;
import co.gov.metropol.area247.util.Utils;
import co.gov.metropol.area247.util.constantes.Constantes;
import co.gov.metropol.area247.util.ex.Area247Exception;
import co.gov.metropol.area247.util.web.Coordenada;

@Service
public class RutaServiceImpl implements IRutaService {

	@Autowired
	private IEnciclaService enciclaService;

	@Autowired
	private IGtpcService gtpcService;

	@Autowired
	private IPuntoTarjetaCivicaService tarjetaCivicaService;

	@Autowired
	private ITipoRutaService tipoRutaService;

	@Autowired
	private RutaRepository rutaRepository;

	@Autowired
	private RutaRepositoryCustom rutaRepositoryCustom;

	@Autowired
	private RutaMetroMapper rutaMapper;

	@Autowired
	private RutaConcretaMapper rutaConcretaMapper;

	@Autowired
	private IEstacionLineaMetroService estacionLineaMetroService;

	@Autowired
	private IParaderoRutaService paraderoRutaService;

	@Autowired
	private IFrecuenciaRutaMetroService frecuenciaRutaMetroService;

	@Autowired
	private IEmpresaTransporteService empresaTransporteService;
	
	@Autowired
	private IHorarioRutaService horarioRutaService;

	@Override
	@LogReturnValue
	public List<TareviaEstacionEnciclaDTO> obtenerEstacionesCercanas(Coordenada coordenada) {
		try {
			return enciclaService.obtenerEstacionesCercanas(coordenada.getLatitud(), coordenada.getLongitud(),
					coordenada.getRadio());
		} catch (Exception e) {
			LoggingUtil.logException(
					String.format("Error al tratar de obtener las rutas para la coordenada %s.", coordenada), e);
			throw new Area247Exception(
					String.format("Error al tratar de obtener las rutas para la coordenada %s.", coordenada), e);
		}
	}

	@Override
	public List<CiclorutaDTO> obtenerCiclorutasCercanas(Coordenada coordenada) {
		try {
			return enciclaService.obtenerCiclorutasCercanas(coordenada.getLatitud(), coordenada.getLongitud(),
					coordenada.getRadio());
		} catch (Exception e) {
			LoggingUtil.logException(
					String.format("Error al tratar de obtener las ciclorutas para la coordenada %s.", coordenada), e);
			throw new Area247Exception(
					String.format("Error al tratar de obtener las ciclorutas para la coordenada %s.", coordenada), e);
		}
	}

	@Override
	public List<ParaderoRutaDTO> obtenerParaderos(Long idRuta) {
		try {
			return gtpcService.obtenerParaderos(idRuta);
		} catch (Exception e) {
			LoggingUtil.logException(
					String.format("Error al tratar de obtener los paraderos para el parametro %s.", idRuta), e);
			throw new Area247Exception(
					String.format("Error al tratar de obtener los paraderos para la coordenada %s.", idRuta), e);
		}
	}

	@Override
	public List<RutaMetroDTO> findByCodigoOrDescripcion(String parametro) {
		try {

			List<RutaMetroDTO> rutasMetroDTO = rutaMapper
					.mapToRutaMetroDTO(rutaRepository.findByCodigoOrDescripcion(parametro));

			rutasMetroDTO.iterator().forEachRemaining(rutaMetroDTO -> {

				rutaMetroDTO.setFrecuenciasDTO(frecuenciaRutaMetroService.findByIdRuta(rutaMetroDTO.getId()));
				rutaMetroDTO.setHorariosDTO(horarioRutaService.findByIdRuta(rutaMetroDTO.getId()));
				rutaMetroDTO.setParaderosDTO(paraderoRutaService.findByIdRuta(rutaMetroDTO.getId(), false));
				rutaMetroDTO.setEmpresasTransporteDTO(empresaTransporteService.findByIdRuta(rutaMetroDTO.getId()));
				
			});

			return rutasMetroDTO;

		} catch (Exception e) {
			LoggingUtil.logException(String
					.format("Error al tratar de obtener las rutas gtpc cercanas para el parametro %s.", parametro), e);
			throw new Area247Exception(String
					.format("Error al tratar de obtener las rutas gtpc cercanas para el parametro %s.", parametro), e);
		}
	}

	@Override
	@LogReturnValue
	public List<PuntoTarjetaCivicaDTO> obtenerPuntosTarjetaCivicaCercanos(Coordenada coordenada) {
		try {
			return tarjetaCivicaService.obtenerEstacionesCercanas(coordenada.getLatitud(), coordenada.getLongitud(),
					coordenada.getRadio());
		} catch (Exception e) {
			LoggingUtil.logException(String.format(
					"Error al tratar de obtener los puntos de tarjeta civica para la coordenada %s.", coordenada), e);
			throw new Area247Exception(String.format(
					"Error al tratar de obtener los puntos de tarjeta civica para la coordenada %s.", coordenada), e);
		}
	}

	@Override
	public List<RutaMetroDTO> obtenerRutasCercanas(Coordenada coordenada, List<ModoRecorrido> modosRecorrido) {
		Assert.notNull(coordenada, "El argumento coordenada no debe ser nulo");

		try {

			List<Ruta> rutasCercanas = null;
			List<RutaMetroDTO> rutasCercanasDTO = Lists.newArrayList();

			if (Utils.isNull(modosRecorrido)) {
				rutasCercanas = rutaRepository.rutaCercana(coordenada.getLatitud(), coordenada.getLongitud(),
						coordenada.getRadio());
			} else {
				List<Long> modosRecorridoLong = obtenerIdentificadoresModoRecorrido(modosRecorrido);

				rutasCercanas = rutaRepository.rutaCercana(coordenada.getLatitud(), coordenada.getLongitud(),
						coordenada.getRadio(), modosRecorridoLong);
			}

			if (Utils.isNotEmpty(rutasCercanas)) {
				rutasCercanasDTO = rutaMapper.mapToRutaMetroDTO(rutasCercanas);

				for (RutaMetroDTO rutaMetroDTO : rutasCercanasDTO) {
					Long idRuta = rutaMetroDTO.getId();
					rutaMetroDTO.setFrecuenciasDTO(frecuenciaRutaMetroService.findByIdRuta(idRuta));
					rutaMetroDTO.setHorariosDTO(horarioRutaService.findByIdRuta(idRuta));
					rutaMetroDTO.setEmpresasTransporteDTO(gtpcService.findEmpresaTransporteByIdRuta(idRuta));
				}
			}

			return rutasCercanasDTO;

		} catch (Exception e) {

			throw new Area247Exception("Ocurrió un error al momento de obtener las lineas cercanas");

		}
	}

	/**
	 * A partir de los paraderos que se encontraron en la busqueda por radio de
	 * accion estos paraderos tienen rutas asociadas, a partir de ahi se obtienen
	 * las informacion completa de las rutas
	 * 
	 * @param paraderosRutaMetroDTO - de donde se obtienen las rutas
	 * 
	 * @return una lista con las rutas encontradas y ordenadas por codigo de ruta
	 */
	private List<RutaMetroDTO> obtenerRutasDesdeParaderos(List<ParaderoRutaMetroDTO> paraderosRutaMetroDTO) {

		if (Utils.isNotEmpty(paraderosRutaMetroDTO)) {
			List<RutaMetroDTO> rutasDTO = paraderosRutaMetroDTO.stream().map(ParaderoRutaMetroDTO::getRutaDTO)
					.collect(Collectors.toList());

			Map<Long, RutaMetroDTO> mapRutasDTO = new HashMap<>(paraderosRutaMetroDTO.size());

			for (RutaMetroDTO rutaDTO : rutasDTO) {
				mapRutasDTO.put(rutaDTO.getId(), rutaDTO);
			}

			rutasDTO = new ArrayList<>(mapRutasDTO.values());
			Collections.sort(rutasDTO, (o1, o2) -> o1.getCodigo().compareTo(o2.getCodigo()));

			return rutasDTO;
		}

		return new ArrayList<>();

	}

	/**
	 * A partir de los estaciones que se encontraron en la busqueda por radio de
	 * accion estas estaciones tienen rutas asociadas, a partir de ahi se obtienen
	 * las informacion completa de las lineas
	 * 
	 * @param estacionesLineaMetroDTO - de donde se obtienen las lineas
	 * 
	 * @return una lista con las lineas encontradas y ordenadas por codigo de linea
	 */
	private List<LineaMetroDTO> obtenerLineasDesdeEstaciones(List<EstacionLineaMetroDTO> estacionesLineaMetroDTO) {

		if (Utils.isNotEmpty(estacionesLineaMetroDTO)) {
			List<LineaMetroDTO> lineasDTO = estacionesLineaMetroDTO.stream().map(EstacionLineaMetroDTO::getLineaDTO)
					.collect(Collectors.toList());

			Map<Long, LineaMetroDTO> mapLineasDTO = new HashMap<>(estacionesLineaMetroDTO.size());

			for (LineaMetroDTO lineaDTO : lineasDTO) {
				mapLineasDTO.put(lineaDTO.getId(), lineaDTO);
			}

			lineasDTO = new ArrayList<>(mapLineasDTO.values());
			Collections.sort(lineasDTO, (o1, o2) -> o1.getCodigo().compareTo(o2.getCodigo()));

			return lineasDTO;
		}

		return new ArrayList<>();

	}

	@Override
	public Map<ClasificacionInformacion, List<Object>> obtenerInformacionTransporteCercano(Coordenada coordenada,
			List<Integer> modosTransporte) {

		Assert.notNull(modosTransporte, "Debe definir los modos de transporte para obtener las rutas cercanas");

		Map<Integer, List<ModoRecorrido>> modosRecorridoClasificados = clasificarTiposTransporte(
				ModoRecorrido.valuesOf(modosTransporte.stream().mapToInt(i -> i).toArray()));

		EnumMap<ClasificacionInformacion, List<Object>> informacion = new EnumMap<>(ClasificacionInformacion.class);

		if (modosRecorridoClasificados.containsKey(Constantes.TIPO_TRANSPORTE_LINEA)) {

			List<EstacionLineaMetroDTO> estacionesDTO = estacionLineaMetroService.obtenerEstacionesCercanas(coordenada,
					obtenerIdentificadoresModoRecorrido(
							modosRecorridoClasificados.get(Constantes.TIPO_TRANSPORTE_LINEA)));
			List<LineaMetroDTO> lineasDTO = obtenerLineasDesdeEstaciones(estacionesDTO);
			List<PuntoTarjetaCivicaDTO> civicasDTO = obtenerPuntosTarjetaCivicaCercanos(coordenada);

			// Almacenamos la informacion obtenida de las lineas
			informacion.computeIfAbsent(ClasificacionInformacion.RUTAS, k -> Lists.newArrayList()).addAll(lineasDTO);
			informacion.computeIfAbsent(ClasificacionInformacion.ESTACIONES, k -> Lists.newArrayList())
					.addAll(estacionesDTO);
			informacion.computeIfAbsent(ClasificacionInformacion.RECARGAS, k -> Lists.newArrayList())
					.addAll(civicasDTO);
		}

		if (modosRecorridoClasificados.containsKey(Constantes.TIPO_TRANSPORTE_RUTA)) {
			List<ParaderoRutaMetroDTO> paraderosDTO = paraderoRutaService.obtenerParaderosCercanos(coordenada,
					obtenerIdentificadoresModoRecorrido(
							modosRecorridoClasificados.get(Constantes.TIPO_TRANSPORTE_RUTA)));
			List<RutaMetroDTO> rutasDTO = obtenerRutasDesdeParaderos(paraderosDTO);

			// Almacenamos la informacion obtenida de las rutas
			informacion.computeIfAbsent(ClasificacionInformacion.RUTAS, k -> Lists.newArrayList()).addAll(rutasDTO);
			informacion.computeIfAbsent(ClasificacionInformacion.ESTACIONES, k -> Lists.newArrayList())
					.addAll(paraderosDTO);
		}

		if (modosRecorridoClasificados.containsKey(Constantes.TIPO_TRANSPORTE_CICLA)) {
			informacion.computeIfAbsent(ClasificacionInformacion.RUTAS, k -> Lists.newArrayList())
					.addAll(obtenerCiclorutasCercanas(coordenada));
			informacion.computeIfAbsent(ClasificacionInformacion.ESTACIONES, k -> Lists.newArrayList())
					.addAll(obtenerEstacionesCercanas(coordenada));
		}

		return informacion;

	}

	/**
	 * Crea un mapa con maximo 3 llaves que son de tipo entero, estos valores
	 * pueden ser: (1) Si los modos de recorrido son Tranvia, Metro, Metro
	 * Cable, Metro Plus. (2) Si los modos de recorrido son Autobus, Integrado,
	 * Alimentador. (3) Si el modo de recorrido es ENCICLA
	 * 
	 * @param modosRecorrido
	 *            - Modos de recorrido para clasificar
	 * 
	 * @return un mapa ya clasificado segun los modos de recorrido
	 */
	private Map<Integer, List<ModoRecorrido>> clasificarTiposTransporte(ModoRecorrido... modosRecorrido) {

		Map<Integer, List<ModoRecorrido>> modoTransporteClasificados = new HashMap<>();

		for (ModoRecorrido modo : modosRecorrido) {
			switch (modo) {
			case TRANVIA:
			case METRO:
			case METRO_CABLE:
			case METRO_PLUS:
				modoTransporteClasificados.computeIfAbsent(Constantes.TIPO_TRANSPORTE_LINEA, k -> Lists.newArrayList())
						.add(modo);
				break;

			case AUTOBUS:
			case INTEGRADO:
			case ALIMENTADOR:
				modoTransporteClasificados.computeIfAbsent(Constantes.TIPO_TRANSPORTE_RUTA, k -> Lists.newArrayList())
						.add(modo);
				break;

			case ENCICLA:
				modoTransporteClasificados.computeIfAbsent(Constantes.TIPO_TRANSPORTE_CICLA, k -> Lists.newArrayList())
						.add(modo);
				break;

			default:
				modoTransporteClasificados.computeIfAbsent(Constantes.TIPO_TRANSPORTE_PIE, k -> Lists.newArrayList())
						.add(modo);
				break;
			}
		}

		return modoTransporteClasificados;
	}

	/**
	 * De una lista que contiene modos de recorrido obtiene una lista con los
	 * indices de estos modos de recorrido
	 * 
	 * @param modosRecorrido
	 *            - lista de la cual se obtendran los indices de los modos de
	 *            recorrido
	 *            
	 * @return una lista con valores enteros los cuales son los indices de los
	 *         modos de recorrido
	 */
	private List<Long> obtenerIdentificadoresModoRecorrido(List<ModoRecorrido> modosRecorrido) {
		List<Long> identificadores = Lists.newArrayList();

		if (Utils.isNotEmpty(modosRecorrido)) {
			for (ModoRecorrido modo : modosRecorrido) {
				Long identificador = Long.valueOf(modo.indice());
				identificadores.add(identificador);
			}
		}

		return identificadores;
	}

	@Override
	public List<RutaConcretaDTO> findInfoLineasAndRutasByCodigoOrDescripcion(String parametro) {
		return rutaConcretaMapper
				.mapToRutaConcretaDTO(rutaRepositoryCustom.findInfoLineasAndRutasByCodigoOrDescripcion(parametro));
	}

	@Override
	public RutaMetroDTO findById(Long id, boolean m2o, boolean o2m) {

		try {

			RutaMetroDTO rutaMetroDTO = rutaMapper.toRutaMetroDTO(rutaRepository.findOne(id));

			if (m2o)
				conProfundizacion(rutaMetroDTO);

			if (o2m)
				conListasSecundarias(rutaMetroDTO);

			return rutaMetroDTO;

		} catch (Exception e) {
			LoggingUtil.logException(String.format("Error al consultar la ruta por el id %s", id), e);
			throw new Area247Exception(String.format("Error al consultar la ruta por el id %s", id), e);
		}
	}

	private void conProfundizacion(RutaMetroDTO rutaMetroDTO) {

		if (!Utils.isNull(rutaMetroDTO.getTipoRutaDTO()) && !Utils.isNull(rutaMetroDTO.getTipoRutaDTO().getId())) {
			rutaMetroDTO.setTipoRutaDTO(tipoRutaService.findById(rutaMetroDTO.getTipoRutaDTO().getId()));
		}

	}
	
	private void conListasSecundarias(RutaMetroDTO rutaMetroDTO) {
		
		rutaMetroDTO.setFrecuenciasDTO(frecuenciaRutaMetroService.findByIdRuta(rutaMetroDTO.getId()));
		rutaMetroDTO.setHorariosDTO(horarioRutaService.findByIdRuta(rutaMetroDTO.getId()));
		rutaMetroDTO.setParaderosDTO(paraderoRutaService.findByIdRuta(rutaMetroDTO.getId(), false));
		rutaMetroDTO.setEmpresasTransporteDTO(empresaTransporteService.findByIdRuta(rutaMetroDTO.getId()));
		
	}

}
