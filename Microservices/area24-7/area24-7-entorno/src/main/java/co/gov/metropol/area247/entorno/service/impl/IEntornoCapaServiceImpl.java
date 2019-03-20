package co.gov.metropol.area247.entorno.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

import co.gov.metropol.area247.contenedora.model.Capa;
import co.gov.metropol.area247.contenedora.model.Coordenada;
import co.gov.metropol.area247.contenedora.model.Marcador;
import co.gov.metropol.area247.contenedora.service.IContenedoraCapaService;
import co.gov.metropol.area247.contenedora.service.IContenedoraCoordenadaService;
import co.gov.metropol.area247.contenedora.service.IContenedoraIconoService;
import co.gov.metropol.area247.contenedora.service.IContenedoraMarcadorService;
import co.gov.metropol.area247.core.gateway.siata.dto.Forecast;
import co.gov.metropol.area247.core.gateway.siata.dto.IStation;
import co.gov.metropol.area247.core.gateway.siata.dto.StationsDataClima;
import co.gov.metropol.area247.core.ordenamiento.dto.CoordenadaDto;
import co.gov.metropol.area247.core.util.GeometryUtil;
import co.gov.metropol.area247.entorno.model.Medicion;
import co.gov.metropol.area247.entorno.service.IEntornoCapaService;
import co.gov.metropol.area247.entorno.service.IEntornoMedicionService;

@Deprecated
@Service
public class IEntornoCapaServiceImpl implements IEntornoCapaService {
	
	@Autowired
	IEntornoMedicionService medicionService;
	
	@Autowired
	IContenedoraCoordenadaService coordenadaService;
	
	@Autowired
	IContenedoraMarcadorService marcadorService;
	
	@Autowired
	IContenedoraCapaService capaService;
	
	
	@Autowired
	IContenedoraIconoService iconoService;
	

	
	private static final Logger LOGGER = LoggerFactory.getLogger(IEntornoCapaServiceImpl.class);
	
	@Override
	/**
	 * Punto entrada servicio para obtener las mediciones y almacenarlas
	 */
	public void capaObtenerMedicionEstaciones(Long idCapa, Object tipoMedicion) {
		List<Marcador> marcadoresCapa = new ArrayList<>();
		Capa capaAuxiliar = capaService.capaGetById(idCapa);
		marcadoresCapa = capaObtenerMedicion(idCapa,tipoMedicion,capaAuxiliar);
		capaAuxiliar.setMarcadores(marcadoresCapa);
		capaService.capaActualizar(capaAuxiliar);
	}
	/**
	 * Metodo para obtener los datos del SIATA
	 * @param idCapa
	 * @param tipoMedicion objeto que representa el tipo de medicion para saber si es actualizar aire o nivel agua
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Marcador> capaObtenerMedicion(Long idCapa, Object tipoMedicion, Capa capaAuxiliar){
		List<IStation> marcadores = null;
		List<Marcador> marcadoresCapa = new ArrayList<>();
		if(!(tipoMedicion instanceof StationsDataClima)) {
			for(IStation marcador : marcadores) {
				for(Medicion medicion : medicionService.medicionObtenerPorIdCapa(idCapa)) {
					String valorICA = marcador.getMedicion();
					float valorNumericoICA = Float.NaN;
					try{
						valorNumericoICA= Float.parseFloat(valorICA);
					}catch(Exception e){
						LOGGER.info("Valor ICA no definido");
					}
					if (valorNumericoICA >= medicion.getEscalaInicial() && valorNumericoICA <= medicion.getEscalaFinal()){
						Marcador marcadorCapa = capaActualizarMarcador(marcador, medicion, capaAuxiliar);
						marcadoresCapa.add(marcadorCapa);
					}
				}
			}
		}else {
			for(IStation marcador : marcadores) {
				for(Medicion medicion : medicionService.medicionObtenerPorIdCapa(idCapa)) {
					for(Forecast pronosticoDto : marcador.getPronosticos()) {
						Calendar calendarioInicial = GregorianCalendar.getInstance(); // creates a new calendar instance 
						Date periodoInicialPronostico = pronosticoDto.getTiempoInicial();
						calendarioInicial.setTime(periodoInicialPronostico);
						Date periodoFinalPronostico = pronosticoDto.getTiempoFinal();
						Calendar calendarioFinal = GregorianCalendar.getInstance(); 
						calendarioFinal.setTime(periodoFinalPronostico);
						if(calendarioInicial.get(Calendar.HOUR_OF_DAY)>= medicion.getEscalaInicial()
								&& calendarioFinal.get(Calendar.HOUR_OF_DAY)<= medicion.getEscalaFinal()
								&& pronosticoDto.getDescripcion().compareTo(medicion.getDescripcion())==0) {
								Marcador aux = capaClimaActualizarMarcador(pronosticoDto,marcador, capaAuxiliar, medicion);
								marcadoresCapa.add(aux);
						}
					}
				}
			}
		}
		return marcadoresCapa;
	}
	private Marcador capaClimaActualizarMarcador(Forecast pronosticoDto, IStation marcador, Capa capaAux, Medicion medicion) {
		Marcador marcadorCapa = recuperarMarcador(marcador, capaAux );
		marcadorCapa.setNombre(marcador.getNombre());
		marcadorCapa.setCodigo(marcador.getCodigo());
		marcadorCapa.setDescripcion(pronosticoDto.getTiempoInicial().getTime()+"");
		marcadorCapa.setVentanaInformacion(medicion.getVentanaInformacion());
		marcadorCapa.setIcono(medicion.getIcono());
		marcadorCapa.setActivo(Boolean.TRUE);
		marcadorCapa.setPoligono(false);
		//marcadorService.marcadorCrear(marcadorCapa);
		LOGGER.info("Marcador creado con id:"+marcadorCapa.getId());
		//List<Coordenada> coordenadas= capaCrearCoordenadasConCentroide(marcador, marcadorCapa);
		//marcadorCapa.setCoordenadaPunto(GeometryUtil.obtenerPunto(0.0, 0.0));
		//marcadorService.marcadorCrear(marcadorCapa);
		LOGGER.info("Marcador guardado con id:"+marcadorCapa.getId());
		return marcadorCapa;
	}
	private Marcador recuperarMarcador(IStation marcador, Capa capaAuxilair) {
		Marcador marcadorCapa = null;
		List<Marcador> marcadoresCapa = new ArrayList<>();
				//marcadorService.obtenerMarcadorPorCodigo(marcador.getCodigo(),capaAuxilair.getId());
		if(marcadoresCapa.size()==1) {
			//marcadorCapa = marcadoresCapa.get(0);
		}else {
			marcadoresCapa.forEach(marcadorLista->{
				marcadorService.marcadorEliminar(marcadorLista.getId());
			});
			marcadorCapa = new Marcador();
		}
		return marcadorCapa;
	}
	/**
	 * Metodo para actualizar el marcador y coordenadas de la capa
	 * @param marcador
	 * @param medicion
	 * @return
	 */
	private Marcador capaActualizarMarcador(IStation marcador, Medicion medicion, Capa capaAuxilair){
			Marcador marcadorCapa = recuperarMarcador(marcador, capaAuxilair);
			marcadorCapa.setNombre(marcador.getNombre());
			marcadorCapa.setCodigo(marcador.getCodigo());
			marcadorCapa.setDescripcion(marcador.getDescripcion());
			//marcadorService.marcadorCrear(marcadorCapa);
			LOGGER.info("Marcador creado con id:"+marcadorCapa.getId());
			List<Coordenada> coordenadasActuales = new ArrayList<>();
			if(coordenadasActuales!=null){
				coordenadasActuales.forEach((elemento)->{
					Long idCoordenadaActual = elemento.getId();
					boolean resultadoBorrado = coordenadaService.coordenadaEliminar(idCoordenadaActual);
					if(resultadoBorrado){
						LOGGER.info("Coordenada borrada con id: "+idCoordenadaActual);
					}else{
						LOGGER.info("No se pudo borrar la coordenada con id: "+idCoordenadaActual);
					}
				});
			}
		marcadorCapa.setIcono(medicion.getIcono());
		marcadorCapa.setVentanaInformacion(medicion.getVentanaInformacion());
		marcadorCapa.setActivo(Boolean.TRUE);
		marcadorCapa.setPoligono(false);
		//List<Coordenada>coordenadasMarcador = capaCrearCoordenadas(marcador, marcadorCapa);
//		marcadorCapa.setCoordenadas(coordenadasMarcador);
		//marcadorService.marcadorCrear(marcadorCapa);
		LOGGER.info("Marcador guardado con id:"+marcadorCapa.getId());
		return marcadorCapa;
	}
	/**
	 * Metodo para crear nuevas  coordenadas para un marcador
	 * @param marcador
	 * @param marcadorCapa
	 * @return
	 */
	private List<Coordenada> capaCrearCoordenadas(IStation marcador, Marcador marcadorCapa){
		List<Coordenada> coordenadasMarcador = new ArrayList<Coordenada>();
		CoordenadaDto coordenada = marcador.getCoordenadas().get(0); 
		Point punto = GeometryUtil.obtenerPunto(coordenada.getLongitud(),coordenada.getLatitud());					
		Coordenada coordenadaAuxiliar = new Coordenada();
		coordenadaAuxiliar.setCoordenadaPunto(punto);
		coordenadaAuxiliar.setMarcador(marcadorCapa);
		coordenadaService.coordeanadaCrear(coordenadaAuxiliar);
		coordenadasMarcador.add(coordenadaAuxiliar);
		return coordenadasMarcador;
	}
	private List<Coordenada> capaCrearCoordenadasConCentroide(IStation marcador, Marcador marcadorCapa){
		List<CoordenadaDto> coordenadasDto = new ArrayList<>();
		List<Coordenada> coordenadasExistentes =  new ArrayList<>();
		if(coordenadasExistentes!=null) {
			coordenadasExistentes.forEach(coordenadaABorrar->{
				coordenadaService.coordenadaEliminar(coordenadaABorrar.getId());
			});
		}
//		marcador.getCoordenadas().forEach(coordenada->{
//			CoordenadaDto coordenadaDtoAux = new CoordenadaDto();
//			coordenadaDtoAux.setLatitud(coordenada.getLatitud());
//			coordenadaDtoAux.setLongitud(coordenada.getLongitud());
//			coordenadasDto.add(coordenadaDtoAux);
//		});
		Polygon poligono = GeometryUtil.obtenerPuntosPolygon(coordenadasDto);
		Point centroide = poligono.getCentroid();
		double latitudAux = centroide.getY();
		double longitudAux = centroide.getX();
		centroide  = GeometryUtil.obtenerPunto(latitudAux,longitudAux);
		Coordenada coordenadaAuxiliar = new Coordenada();
		coordenadaAuxiliar.setCoordenadaPunto(centroide);
		coordenadaAuxiliar.setMarcador(marcadorCapa);
		coordenadaService.coordeanadaCrear(coordenadaAuxiliar);
		List<Coordenada> coordenadasADevolver = new ArrayList<>();
		coordenadasADevolver.add(coordenadaAuxiliar);
		return coordenadasADevolver;
	}
}
