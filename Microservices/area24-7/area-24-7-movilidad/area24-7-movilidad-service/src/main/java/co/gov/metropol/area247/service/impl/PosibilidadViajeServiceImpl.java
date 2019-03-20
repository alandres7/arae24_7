package co.gov.metropol.area247.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;

import co.gov.metropol.area247.gateway.IPosibilidadViajeServiceGateway;
import co.gov.metropol.area247.geometry.GeometryUtil;
import co.gov.metropol.area247.model.TareviaEstacionEnciclaDTO;
import co.gov.metropol.area247.repository.domain.support.enums.ModoRecorrido;
import co.gov.metropol.area247.service.IEnciclaService;
import co.gov.metropol.area247.service.IPosibilidadViajeService;
import co.gov.metropol.area247.service.ITarifaIntegradaMetroService;
import co.gov.metropol.area247.services.rest.opt.ItinerarioWSDTO;
import co.gov.metropol.area247.services.rest.opt.PlanWSDTO;
import co.gov.metropol.area247.services.rest.opt.PosibilidadViajeWSDTO;
import co.gov.metropol.area247.services.rest.opt.TrayectoWSDTO;
import co.gov.metropol.area247.util.Utils;
import co.gov.metropol.area247.util.constantes.Constantes;
import co.gov.metropol.area247.util.ex.Area247Exception;
import co.gov.metropol.area247.util.web.Coordenada;

@Service
public class PosibilidadViajeServiceImpl implements IPosibilidadViajeService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PosibilidadViajeServiceImpl.class);

	@Autowired
	private IPosibilidadViajeServiceGateway posibilidadViajeGateway;

	@Autowired
	private ITarifaIntegradaMetroService tarifaIntegradaMetroService;

	@Autowired
	private IEnciclaService enciclaService;

	private static final int MAX_DISTANCIA_ESTACION = 200;

	@Override
	public PosibilidadViajeWSDTO consultarPosiblesViajes(Coordenada origen, Coordenada destino, Date fecha,
			List<Long> modosTransporte) {

		try {
			PosibilidadViajeWSDTO wsdto = null;
			String modosRecorrido = "";
				
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			// Convertimos la lista de los ids de los modos de transporte a
			// array de int, Si es domingo se elimina el modo de encicla  
			
			
			if(cal.HOUR_OF_DAY < 06 || cal.HOUR_OF_DAY > 21) {
				List<Long> modosTransporteFiltrado = eliminarModoEncicla(modosTransporte);
				int[] indices = modosTransporteFiltrado.stream().mapToInt(Long::intValue).toArray();
				modosRecorrido = estandarizarTipoRutas(ModoRecorrido.valuesOf(indices));
				
			}else {
				if(cal.get(Calendar.DAY_OF_WEEK)==cal.SUNDAY) {
					List<Long> modosTransporteFiltrado = eliminarModoEncicla(modosTransporte);
					int[] indices = modosTransporteFiltrado.stream().mapToInt(Long::intValue).toArray();
					modosRecorrido = estandarizarTipoRutas(ModoRecorrido.valuesOf(indices));
					
				}
				else {
					int[] indices = modosTransporte.stream().mapToInt(Long::intValue).toArray();
					modosRecorrido = estandarizarTipoRutas(ModoRecorrido.valuesOf(indices));
				}
			}
			
			
			
			// Si el modo recorrido es caminar sugerimos un viaje utilizando
			// todos los medios de transporte
			if (modosRecorrido != null && modosRecorrido.equals(ModoRecorrido.CAMINAR.getModoOTP())) {
				wsdto = recomendarOtraOpcionViaje(origen, destino, fecha);
			} else {
				wsdto = posibilidadViajeGateway.consultarPosiblesViajes(fecha, origen.getLatitud(),
						origen.getLongitud(), destino.getLatitud(), destino.getLongitud(), modosRecorrido);
				// En caso de que no encuentre informacion con los modos de
				// recorrido indicados sugerimos un viaje utilizando todos los
				// medios de transporte
				if (wsdto.getCodigo() == HttpStatus.NOT_FOUND.value()) {
					wsdto = recomendarOtraOpcionViaje(origen, destino, fecha);
				}

				fijarDistanciaRecorridoAutomovil(origen, destino, fecha, wsdto);
			}

			return ajustarPosibilidadViajeWSDTO(wsdto);

		} catch (NullPointerException | IllegalArgumentException e) {
			throw new Area247Exception("Modo transporte no válido");
		} catch (Area247Exception e) {
			throw new Area247Exception(e.getMessage());
		}

	}

	/**
	 * Este metodo completa informacion faltante requerida por medio de
	 * validaciones:<br>
	 * <br>
	 * * Filtra los dos caminos mejores, el camino con menor distancia y el
	 * camino con mejor tiempo o un solo camino en caso de que este cumpla con
	 * los dos items(distancia y tiempo).<br>
	 * * Obtiene el tramo de coordenadas de cada trayecto<br>
	 * * En en trayecto de un camino indica en la variable
	 * <code>tipoTransporte</code> si se recorre en linea, ruta, o caminando.
	 * <br>
	 * 
	 * @param wsdto
	 *            - objeto {@link PosibilidadViajeWSDTO} el cual se le
	 *            completara la informacion.<br>
	 * 
	 * @return el objeto {@link PosibilidadViajeWSDTO} ya con la informacion.
	 */
	public PosibilidadViajeWSDTO ajustarPosibilidadViajeWSDTO(PosibilidadViajeWSDTO wsdto) {
		try {

			PlanWSDTO plan = wsdto.getPlan();
			if (!Utils.isNull(plan)) {
				List<ItinerarioWSDTO> itinerarios = plan.getItinerarios();
				if (Utils.isNotEmpty(itinerarios)) {
					eliminarCaminosNoDeseables(itinerarios);
					// Con los caminos optimos indicamos sus tipos de transporte
					itinerarios.iterator().forEachRemaining(itinerario -> {
						List<TrayectoWSDTO> trayectos = itinerario.getTrayectos();
						if (Utils.isNotEmpty(trayectos)) {
							calcularTarifaItinerario(itinerario);
							elimimarConDistanciaCero(trayectos);
							trayectos.iterator().forEachRemaining(trayecto -> {
								fijarInfoEstacionesBicicletas(trayecto);
								fijarTipoTransporte(trayecto);
								fijarCoordenadasRuta(trayecto);
							});
						}
					});
				}
			}

			return wsdto;

		} catch (Area247Exception e) {
			throw new Area247Exception(e.getLocalizedMessage());
		} catch (Exception e) {
			throw new Area247Exception("Error al ajustar la posibilidad del viaje", e.getCause());
		}
	}

	public void fijarInfoEstacionesBicicletas(TrayectoWSDTO trayectoWSDTO) {

		try {
			if (trayectoWSDTO.getModo().equals(ModoRecorrido.BICICLETA_PARTICULAR.getModoOTP())) {
				String estacionEnciclaIdDesde = trayectoWSDTO.getDesde().getEstacionEnciclaId();
				if (estacionEnciclaIdDesde != null && !estacionEnciclaIdDesde.isEmpty()) {
					TareviaEstacionEnciclaDTO estacionDesde = enciclaService
							.findByEstacionEnciclaId(Long.valueOf(estacionEnciclaIdDesde.replace("\"", "")));
					if (!Utils.isNull(estacionDesde)) {
						trayectoWSDTO.getDesde().setCantidadBicicletasDisponibles(estacionDesde.getCantidadBicicletas());
						trayectoWSDTO.getDesde().setCantidadPuestosDisponibles(estacionDesde.getLugares());
					}
				}

				String estacionEnciclaIdHasta = trayectoWSDTO.getHasta().getEstacionEnciclaId();
				if (estacionEnciclaIdHasta != null && !estacionEnciclaIdHasta.isEmpty()) {
					TareviaEstacionEnciclaDTO estacionHasta = enciclaService
							.findByEstacionEnciclaId(Long.valueOf(estacionEnciclaIdHasta.replace("\"", "")));
					if (!Utils.isNull(estacionHasta)) {
						trayectoWSDTO.getHasta().setCantidadBicicletasDisponibles(estacionHasta.getCantidadBicicletas());
						trayectoWSDTO.getHasta().setCantidadPuestosDisponibles(estacionHasta.getLugares());
					}
				}
			}
		} catch (Exception e) {
			LOGGER.info("Error al obtener informacion de las bicicletas y lugares disponibles para encicla",
					e.getCause());
		}

	}

	public void calcularTarifaItinerario(ItinerarioWSDTO itinerario) {

		List<TrayectoWSDTO> trayectos = itinerario.getTrayectos();

		if (Utils.isNotEmpty(trayectos)) {

			long tarifa = 0;

			List<List<Long>> idsAgrupados = agruparModosRecorridoParaTafifas(trayectos);

			for (List<Long> idsModosTransporte : idsAgrupados) {
				tarifa += tarifaIntegradaMetroService.obtenerTarifaPorCombinaciones(idsModosTransporte);
			}

			itinerario.setTarifa(tarifa);

		}

	}

	public static List<List<Long>> agruparModosRecorridoParaTafifas(List<TrayectoWSDTO> trayectos) {

		List<List<Long>> idsAgrupados = new ArrayList<>();
		List<Long> idsModosTransporte = new ArrayList<>();
		boolean anteriorTransportePublico = false;
		int tamano = trayectos.size();

		for (int i = 0; i < tamano; i++) {

			TrayectoWSDTO trayecto = trayectos.get(i);
			ModoRecorrido modo = ModoRecorrido.valueOfModoOtp(trayecto.getModo());
			// Si inicia el recorrido caminando o si camina desde un punto de la
			// estacion hasta otro punto de la misma estacion no afecta en nada
			// en el cobro de la tarifa
			if (modo.equals(ModoRecorrido.CAMINAR) && ((i == 0 || i == (tamano - 1))
					|| (anteriorTransportePublico && trayecto.getDistancia() <= MAX_DISTANCIA_ESTACION))) {
				continue;
				// Si el modo de transporte es de linea entonces se agrupa
			} else if (modo.isLinea() || modo.equals(ModoRecorrido.ALIMENTADOR)
					|| modo.equals(ModoRecorrido.METRO_PLUS)) {
				// El cable tiene la misma tarifa del metro, de igual manera
				// para
				// las combinaciones de los modos de transporte del metro
				// entonces en este caso de hace pasar cable por modo metro
				Long idModoTransporte = modo.equals(ModoRecorrido.METRO_CABLE)
						? Long.valueOf(ModoRecorrido.METRO.indice()) : Long.valueOf(modo.indice());
				idsModosTransporte.add(idModoTransporte);
				anteriorTransportePublico = true;
				// Si es ruta de GTPC no agrupa, es individual la tarifa o Si es
				// caminar pero su distancia es mas de 50 entonces inicia otra
				// agrupacion
			} else if (modo.equals(ModoRecorrido.AUTOBUS)
					|| (modo.equals(ModoRecorrido.CAMINAR) && trayecto.getDistancia() > MAX_DISTANCIA_ESTACION)) {
				idsAgrupados.add(idsModosTransporte);
				idsModosTransporte = new ArrayList<>();
				idsModosTransporte.add(Long.valueOf(modo.indice()));
				idsAgrupados.add(idsModosTransporte);
				idsModosTransporte = new ArrayList<>();
				anteriorTransportePublico = false;
			}
		}

		if (Utils.isNotEmpty(idsModosTransporte)) {
			eliminarModosTransporteRepetidosContinuo(idsModosTransporte);
			idsAgrupados.add(idsModosTransporte);
		}

		return idsAgrupados;
	}

	/**
	 * Elimina los modos de transporte que se encuentren repetidos de manera
	 * continua, ejemplo: 9, 2, 2, 2, 9 entonces retorna 9, 2, 9
	 * 
	 * @param idsModosTransporte
	 */
	private static final void eliminarModosTransporteRepetidosContinuo(List<Long> idsModosTransporte) {
		if (Utils.isNotEmpty(idsModosTransporte) && idsModosTransporte.size() > 1) {
			Long idAux = idsModosTransporte.get(0);
			for (int i = 1; i < idsModosTransporte.size(); i++) {
				if (idAux.equals(idsModosTransporte.get(i))) {
					idsModosTransporte.remove(i);
					i--;
				} else {
					idAux = idsModosTransporte.get(i);
				}
			}
		}
	}

	private static final void estandarizarCodigo(TrayectoWSDTO trayecto) {
		if (!Utils.isNull(trayecto) && !Utils.isNull(trayecto.getCodigoRuta())) {
			String codigoRuta = trayecto.getCodigoRuta();
			String codigo = codigoRuta;
			if (codigoRuta.contains(":")) {
				codigo = codigoRuta.split(":")[1].trim();
			}
			trayecto.setCodigoRuta(codigo);
		}
	}

	/**
	 * Decodifica los puntos del trayecto que estan codificados en un tipo de
	 * dato legGeometry y los fija en el campo coordenadas del trayecto, estas
	 * coordenadas son de tipo coordinate con su respectiva latitud y longitud
	 * 
	 * @param trayecto
	 *            - objeto al cual se le fijaran las rutas en el campo
	 *            coordenadas.
	 */
	public void fijarCoordenadasRuta(TrayectoWSDTO trayecto) {
		try {
			estandarizarCodigo(trayecto);
			String puntos = trayecto.getPuntosCodificados().values().iterator().next();
			trayecto.setCoordenadas(GeometryUtil.decodificarPolilinea(puntos));
		} catch (Area247Exception e) {
			throw new Area247Exception(e.getLocalizedMessage());
		} catch (Exception e) {
			throw new Area247Exception("Error al fijar las coordenadas de las rutas", e.getCause());
		}
	}

	/**
	 * Entre la lista de caminos ordena de menor a mayor tiempo de recorrido del
	 * camino, para luego obtener de la primera posicion de la lista el camino
	 * con el menor tiempo.
	 * 
	 * @param itinerarios
	 *            - contiene todos los caminos seleccionados
	 * @return el camino con menor tiempo
	 */
	public static ItinerarioWSDTO obtnerIndiceMenorTiempo(List<ItinerarioWSDTO> itinerarios) {
		Collections.sort(itinerarios, new Comparator<ItinerarioWSDTO>() {

			@Override
			public int compare(ItinerarioWSDTO o1, ItinerarioWSDTO o2) {
				return o1.getTiempoViaje().compareTo(o2.getTiempoViaje());
			}

		});

		return itinerarios.get(0);

	}

	/**
	 * Entre la lista de caminos ordena de menor a mayor distancia de recorrido
	 * del camino, para luego obtener de la primera posicion de la lista el
	 * camino con el menor distancia.
	 * 
	 * @param itinerarios
	 *            - contiene todos los caminos seleccionados
	 * @return el camino con menor distancia.
	 */
	public static ItinerarioWSDTO obtnerIndiceMenorDistancia(List<ItinerarioWSDTO> itinerarios) {
		Collections.sort(itinerarios, new Comparator<ItinerarioWSDTO>() {

			@Override
			public int compare(ItinerarioWSDTO o1, ItinerarioWSDTO o2) {
				return o1.getDistanciaViaje().compareTo(o2.getDistanciaViaje());
			}

		});

		return itinerarios.get(0);

	}

	/**
	 * Elimina los caminos que no tengan ya sea el menor tiempo o la menor
	 * distancia.
	 * 
	 * @param itinerarios
	 *            - contiene todos los caminos
	 * @param caminoMenorDistancia
	 *            - camino con menor distancia
	 * @param caminoMenorTiempo
	 *            - camino con menor tiempo
	 */
	private static void eliminarCaminosNoDeseables(List<ItinerarioWSDTO> itinerarios) {

		ItinerarioWSDTO caminoMenorTiempo = obtnerIndiceMenorTiempo(itinerarios);
		ItinerarioWSDTO caminoMenorDistancia = obtnerIndiceMenorDistancia(itinerarios);

		Predicate<ItinerarioWSDTO> condicion = itinerario -> (itinerario != caminoMenorDistancia
				&& itinerario != caminoMenorTiempo);
		itinerarios.removeIf(condicion);
	}

	private static void fijarTipoTransporte(TrayectoWSDTO trayecto) {

		ModoRecorrido modo = ModoRecorrido.valueOfModoOtp(trayecto.getModo());

		if (modo.isLinea())
			trayecto.setTipoTransporte(Constantes.TIPO_TRANSPORTE_LINEA);
		else if (modo.isRuta())
			trayecto.setTipoTransporte(Constantes.TIPO_TRANSPORTE_RUTA);
		else
			trayecto.setTipoTransporte(Constantes.TIPO_TRANSPORTE_PIE);
	}

	/**
	 * Este metodo transforma los tipo de rutas de la aplicacion de movilidad
	 * (METR,BUS,TRAN,PLUS,CABLE...) al tipo de rutas de OpenTripPlanner
	 * (RAIL,BUS,TRAM,SUBWAY,GONDOLA...)
	 * 
	 * @param modosRecorrido
	 *            - contiene los tipos de rutas manejados en la aplicacion movil
	 * 
	 * @return cadena estandarizada con los tipos de ruta que estan configurados
	 *         en el OpenTripPlanner
	 */
	public static String estandarizarTipoRutas(ModoRecorrido... modosRecorrido) {
		String resultado = null;
		if (!Utils.isNull(modosRecorrido) && modosRecorrido.length > 0) {
			StringBuilder bld = new StringBuilder();
			for (ModoRecorrido modo : modosRecorrido) {
				bld.append(Constantes.COMA + modo.getModoOTP());
			}
			// Siempre va a tener una coma al inicio para eliminar
			resultado = bld.toString().substring(1);
		}
		return resultado;
	}

	/**
	 * Este metodo busca posibles viajes con la opcion de transporte publico,
	 * quiere decir que busca filtrando con todos los medios de transporte, si
	 * encuentra posibles viajes entonces fija el objeto wsdto el codigo 4(Viaje
	 * Sugerido), como mensaje fija los nombres de los modos de viaje que se
	 * utilizan para el recorrido y el plan de viaje.
	 * 
	 * @param origen
	 *            - punto de origen
	 * @param destino
	 *            - punto de destino
	 * @param fecha
	 *            - fecha en la que se consume el servicio
	 * 
	 * @return un objecto {@link PosibilidadViajeWSDTO} con la informacion de
	 *         los posibles viajes encontrados en caso de que encuentre, sino
	 *         retorna un objeto con su respectivo codigo y mensaje.
	 */
	public PosibilidadViajeWSDTO recomendarOtraOpcionViaje(Coordenada origen, Coordenada destino, Date fecha) {

		Assert.notNull(origen, "El argumento origen no puede ser nulo");
		Assert.notNull(destino, "El argumento destino no puede ser nulo");
		Assert.notNull(fecha, "El argumento fecha no puede ser nulo");

		String modosRecorrido = estandarizarTipoRutas(ModoRecorrido.TRANSPORTE_PUBLICO, ModoRecorrido.CAMINAR);
		PosibilidadViajeWSDTO wsdto = posibilidadViajeGateway.consultarPosiblesViajes(fecha, origen.getLatitud(),
				origen.getLongitud(), destino.getLatitud(), destino.getLongitud(), modosRecorrido);

		if (wsdto.getCodigo() == HttpStatus.OK.value()) {
			StringBuilder resultado = new StringBuilder();
			List<ModoRecorrido> resultados = Lists.newArrayList();
			wsdto.getPlan().getItinerarios().get(0).getTrayectos().iterator().forEachRemaining(trayecto -> {
				ModoRecorrido modo = ModoRecorrido.valueOfModoOtp(trayecto.getModo());
				if (modo != ModoRecorrido.CAMINAR && !resultados.contains(modo)) {
					resultados.add(modo);
				}
			});

			if (Utils.isNotEmpty(resultados)) {
				resultados.iterator().forEachRemaining(r -> {
					resultado.append(Constantes.COMA + r.toString().toLowerCase());
				});

				wsdto.setCodigo(HttpStatus.SEE_OTHER.value());
				wsdto.setMensaje(resultado.toString().substring(1));
			}
		}

		return wsdto;
	}

	/**
	 * Realiza otro calculo utilizando como medio de tranporte
	 * {@link ModoRecorrido#AUTOMOVIL} y este resultado lo fija en el viaje
	 * 
	 * @param origen
	 *            - punto de origen
	 * @param destino
	 *            - punto de destino
	 * @param fecha
	 *            - fecha de consulta
	 * @param wsdto
	 *            - objeto al cual se le va a fijar la distancia recorrida en
	 *            automovil
	 */
	private void fijarDistanciaRecorridoAutomovil(Coordenada origen, Coordenada destino, Date fecha,
			PosibilidadViajeWSDTO wsdto) {

		if (!Utils.isNull(wsdto.getPlan())) {

			try {
				String modoRecorridoAutomovil = estandarizarTipoRutas(
						ModoRecorrido.valuesOf(ModoRecorrido.AUTOMOVIL.indice()));
				PosibilidadViajeWSDTO wsdtoAutomovil = posibilidadViajeGateway.consultarPosiblesViajes(fecha,
						origen.getLatitud(), origen.getLongitud(), destino.getLatitud(), destino.getLongitud(),
						modoRecorridoAutomovil);

				if (!Utils.isNull(wsdtoAutomovil) && !Utils.isNull(wsdtoAutomovil.getPlan())
						&& Utils.isNotEmpty(wsdtoAutomovil.getPlan().getItinerarios())) {

					wsdto.getPlan().setDistanciaAutomovil(
							wsdtoAutomovil.getPlan().getItinerarios().get(0).getTrayectos().get(0).getDistancia());

				}
			} catch (Exception e) {
				throw new Area247Exception("No se puede calcular la distancia del carro particular, ", e);
			}
		}
	}

	private void elimimarConDistanciaCero(List<TrayectoWSDTO> trayectos) {
		Predicate<TrayectoWSDTO> trayectoPredicated = p -> p.getDistancia() == 0
				&& p.getModo().equals(ModoRecorrido.BICICLETA_PARTICULAR.getModoOTP());
		trayectos.removeIf(trayectoPredicated);
		Collection<TrayectoWSDTO> aEliminar = Lists.newArrayList();
		TrayectoWSDTO trayectoWSDTOAux = null;
		for (TrayectoWSDTO trayectoWSDTO : trayectos) {
			if (trayectoWSDTOAux != null && (trayectoWSDTO.getModo().equals(ModoRecorrido.CAMINAR.getModoOTP())
					&& trayectoWSDTOAux.getModo().equals(ModoRecorrido.CAMINAR.getModoOTP()))) {
				double distancia = Double.sum(trayectoWSDTO.getDistancia(), trayectoWSDTOAux.getDistancia());
				trayectoWSDTO.setDistancia(distancia);
				aEliminar.add(trayectoWSDTOAux);
			}
			trayectoWSDTOAux = trayectoWSDTO;
		}

		trayectos.removeAll(aEliminar);
	}
	
	public List<Long> eliminarModoEncicla(List<Long> modosTransporte) {
		List<Long> nuevosIndices = new ArrayList<Long>();
		for(int i=0; i<modosTransporte.size(); i++) {
			if(modosTransporte.get(0)!=5) {
				nuevosIndices.add(modosTransporte.get(i));
			}
		}		
		return nuevosIndices;
	}

}
