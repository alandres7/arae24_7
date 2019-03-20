package co.gov.metropol.area247.entorno.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.contenedora.dao.IContenedoraIconoRepository;
import co.gov.metropol.area247.contenedora.model.Capa;
import co.gov.metropol.area247.contenedora.model.Icono;
import co.gov.metropol.area247.contenedora.model.Marcador;
import co.gov.metropol.area247.contenedora.service.IContenedoraCapaService;
import co.gov.metropol.area247.contenedora.service.IContenedoraMarcadorService;
import co.gov.metropol.area247.core.gateway.MarkersProvider;
import co.gov.metropol.area247.core.gateway.siata.dto.RecomendacionAireJson;
import co.gov.metropol.area247.core.gateway.siata.dto.StationAgua;
import co.gov.metropol.area247.core.gateway.siata.dto.StationAire;
import co.gov.metropol.area247.core.gateway.siata.dto.StationClima;
import co.gov.metropol.area247.core.gateway.siata.dto.StationsDataAgua;
import co.gov.metropol.area247.core.gateway.siata.dto.StationsDataAire;
import co.gov.metropol.area247.core.gateway.siata.dto.StationsDataClima;
import co.gov.metropol.area247.core.ordenamiento.dto.CoordenadaDto;
import co.gov.metropol.area247.core.util.GeometryUtil;
import co.gov.metropol.area247.entorno.dao.IEntornoEstacionRepository;
import co.gov.metropol.area247.entorno.dao.IEntornoPronosticoRepository;
import co.gov.metropol.area247.entorno.dao.IEntornovVentanaPronosticoRepository;
import co.gov.metropol.area247.entorno.model.Capas;
import co.gov.metropol.area247.entorno.model.Estacion;
import co.gov.metropol.area247.entorno.model.Medicion;
import co.gov.metropol.area247.entorno.model.Pronostico;
import co.gov.metropol.area247.entorno.model.RecomenaireEstacion;
import co.gov.metropol.area247.entorno.model.RecomendacionAire;
import co.gov.metropol.area247.entorno.repository.EstacionRepository;
import co.gov.metropol.area247.entorno.service.IEntornoEstacionService;
import co.gov.metropol.area247.entorno.service.IEntornoMedicionService;
import co.gov.metropol.area247.entorno.service.IEntornoRecomenaireEstacionService;
import co.gov.metropol.area247.entorno.service.IEntornoRecomendacionAireService;
import co.gov.metropol.area247.seguridad.service.ISeguridadMunicipioService;

@Service
public class IEntornoEstacionServiceImpl implements IEntornoEstacionService {
	@Autowired
	private MarkersProvider markersProvider;
	
	@Autowired
	private IEntornoEstacionRepository estacionDao;
	
	@Autowired
	private IContenedoraCapaService capaSvc;
	
	@Autowired
	private IContenedoraMarcadorService marcadorSvc;
	
	@Autowired
	private IContenedoraIconoRepository iconoDao;
	
	@Autowired
	private IEntornoMedicionService medicionSvc;
	
	@Autowired
	private IEntornovVentanaPronosticoRepository ventanaPronosticoDao;
	
	@Autowired
	private IEntornoPronosticoRepository pronosticoDao;
	
	@Autowired
	private ISeguridadMunicipioService municipioSvc;
	
	@Autowired
	private EstacionRepository estacionDaoJDBC;
	
	@Autowired
	private IEntornoRecomendacionAireService recomendacionAireSvc;
	
	@Autowired
	private IEntornoRecomenaireEstacionService recomenaireEstacionSvc;
	
	
	private Boolean updatedCapa = Boolean.FALSE;
	private Boolean isPoligono;
	private Estacion estacionAux;
	private Marcador marcadorAux;
	private Icono iconoAux;
	private Medicion MedicionAux;
	private static final String FORMATO_FECHA = "yyyy-MM-dd HH:mm:ss";
	
	private static final long ID_VENTANA_RANGO_ERROR = 3L; 
	
	@Override
	public boolean updateEstaciones(long idCapa) {
		try {
			Capa capaEntorno = capaSvc.capaGetById(idCapa);
			boolean updatedMarkers = (capaEntorno.getMarcadores() != null && !capaEntorno.getMarcadores().isEmpty());
			if(updatedMarkers) {
				List<Marcador> marcadoresCapa = capaEntorno.getMarcadores();
				capaEntorno.setMarcadores(new ArrayList<>());
			    marcadorSvc.eliminarMarcadores(marcadoresCapa);
			}
			Capas tipoCapaEntorno = Capas.obtenerPorId((int) idCapa);
			String urlRecurso = capaSvc.capaGetById(idCapa).getUrlRecurso();
			if (capaEntorno.isPoligono()) {
				isPoligono = Boolean.TRUE;
			} else {
				isPoligono = Boolean.FALSE;
			}
			updatedCapa = Boolean.FALSE;
			switch (tipoCapaEntorno) {
			case AIRE:
				List<?> stationsAire = markersProvider.getStationsDataDirect(urlRecurso, new StationsDataAire());
				 //else {
				// stationsAire =
				// markersProvider.getStationsDataDirect(capaEntorno.getUrlRecursoAlterno(), new
				// StationsDataAire());
				// }
				
				recomenaireEstacionSvc.recomenaireEstacionEliminarTodos();	

				stationsAire.forEach(stationAire -> {
					StationAire aux = (StationAire) stationAire;
					estacionAux = new Estacion();
					marcadorAux = new Marcador();
					if (!updatedCapa) {
						SimpleDateFormat format = new SimpleDateFormat(FORMATO_FECHA);
						String dateFormated = format.format(aux.getUltimaActualizacion());
						try {
							capaEntorno.setUltimaActualizacion(format.parse(dateFormated));
						} catch (ParseException e) {}
						capaSvc.capaActualizar(capaEntorno);
						updatedCapa = !updatedCapa;
					}
					medicionSvc.medicionObtenerPorIdCapa(capaEntorno.getId()).forEach(medicion->{
						if(medicion.getColor().equalsIgnoreCase(aux.getColorIconoHex())) {
							marcadorAux.setIcono(medicion.getIcono());
						}
					});
					
					// if(!updatedMarkers) {
					marcadorAux.setNombre(aux.getNombre());
					marcadorAux.setColorFondo(aux.getColorIconoHex());
					marcadorAux.setCapa(capaEntorno);	
					if(!isPoligono) {
						List<CoordenadaDto> coordenadas = aux.getCoordenadas();
						CoordenadaDto parCoordena = coordenadas.get(0);
						marcadorAux.setCoordenadaPunto(GeometryUtil.obtenerPunto(parCoordena.getLatitud(), parCoordena.getLongitud()));
						estacionAux.setMunicipio(municipioSvc.municipioXUbicacion(parCoordena.getLatitud(), parCoordena.getLongitud()));
					}
					marcadorSvc.marcadorCrear(marcadorAux);
					estacionAux.setMarcador(marcadorAux);
					estacionAux.setNombre(aux.getNombre());
					estacionAux.setCategoria(aux.getCategoria());
					estacionAux.setColorHexadecimal(aux.getColorIconoHex());
					estacionAux.setValorICA(aux.getValorICA());
					estacionAux.setColorRGB(aux.getColorIconoRGB());
																			
					String recomendaciones = "";
					for (RecomendacionAireJson recomendacion : aux.getRecomendaciones()) { 
						RecomendacionAire recomendacionBD = recomendacionAireSvc.recomendacionPorCodigo(recomendacion.getCodigo());						
						if(recomendacionBD==null) {
							RecomendacionAire recomendacionBD2 = new RecomendacionAire();
							recomendacionBD2.setCodigo(recomendacion.getCodigo());
							recomendacionBD2.setTexto(recomendacion.getDescripcion());
							recomendacionAireSvc.recomendacionCrear(recomendacionBD2);
						} else {
							if(!recomendacionBD.getTexto().equals(recomendacion.getDescripcion())) {
								recomendacionBD.setTexto(recomendacion.getDescripcion());
								recomendacionAireSvc.recomendacionActualizar(recomendacionBD);								
							}
						}	
						recomendaciones = recomendaciones + recomendacion.getCodigo() + ",";
					}

					estacionAux.setRecomendaciones(recomendaciones);
					//estacionAux.setRecomendaciones(Arrays.deepToString(aux.getRecomendaciones().toArray()));
					estacionDao.save(estacionAux);
										
					for (RecomendacionAireJson recomendacion : aux.getRecomendaciones()) {
						RecomenaireEstacion recomenaireEstacion = new RecomenaireEstacion();
						recomenaireEstacion.setIdEstacion(estacionAux.getId());
						recomenaireEstacion.setIdRecomendacion(
								recomendacionAireSvc.recomendacionPorCodigo(recomendacion.getCodigo()).getId());
						recomenaireEstacionSvc.recomenaireEstacionCrear(recomenaireEstacion);
					}
															
					// }
				});
				break;
			case AGUA:
				List<?> stationsAgua = markersProvider.getStationsDataDirect(urlRecurso, new StationsDataAgua());
				if(updatedMarkers) {
					 marcadorSvc.eliminarMarcadores(capaEntorno.getMarcadores());
					 }
				stationsAgua.forEach(stationAgua -> {
					StationAgua aux = (StationAgua)stationAgua;
					estacionAux = new Estacion();
					marcadorAux = new Marcador();
					if (!updatedCapa) {
						SimpleDateFormat format = new SimpleDateFormat(FORMATO_FECHA);
						String dateFormated = format.format(aux.getUltimaActualizacion());
						try {
							capaEntorno.setUltimaActualizacion(format.parse(dateFormated));
						} catch (ParseException e) { 
							//Solo captura
						}
						capaSvc.capaActualizar(capaEntorno);
						updatedCapa = !updatedCapa;
					}
					medicionSvc.medicionObtenerPorIdCapa(capaEntorno.getId()).forEach(medicion->{
						if(medicion.getColor().equalsIgnoreCase(aux.getColorIconoHex())) {
							marcadorAux.setIcono(medicion.getIcono());
						}
					});
					marcadorAux.setNombre(aux.getNombre());
					marcadorAux.setColorFondo(aux.getColorIconoHex());
					marcadorAux.setCapa(capaEntorno);
					if(isPoligono) {
						//No sería necesario
					}else {
						List<CoordenadaDto> coordenadas = aux.getCoordenadas();
						CoordenadaDto parCoordena = coordenadas.get(0);
						marcadorAux.setCoordenadaPunto(GeometryUtil.obtenerPunto(parCoordena.getLatitud(), parCoordena.getLongitud()));
						estacionAux.setMunicipio(municipioSvc.municipioXUbicacion(parCoordena.getLatitud(), parCoordena.getLongitud()));
					}
					marcadorSvc.marcadorCrear(marcadorAux);
					estacionAux.setMarcador(marcadorAux);
					estacionAux.setNombre(aux.getNombre());
					estacionAux.setCategoria(aux.getCategoria());
					estacionAux.setColorHexadecimal(aux.getColorIconoHex());
					estacionAux.setPorcentajeNivel(aux.getPorcentajeNivel());
					estacionAux.setSubCuenca(aux.getSubCuenca());
					estacionAux.setColorRGB(aux.getColorIconoRGB());
					estacionDao.save(estacionAux);
					// }
				});
				break;
			case CLIMA:
				Date now = new Date();
//				List<?> stationsClima;
				if(updatedMarkers) {
					 marcadorSvc.eliminarMarcadores(capaEntorno.getMarcadores());
					 }
//				if(updatedMarkers) {
//					stationsClima = markersProvider.getStationsDataDirect(capaEntorno.getUrlRecursoAlterno(), new StationsDataClima());
//				}else {
				List<?> stationsClima = markersProvider.getStationsDataDirect(urlRecurso, new StationsDataClima());
//				}
				stationsClima.forEach(stationClima->{
					StationClima aux = (StationClima) stationClima;
					if (!updatedCapa) {
						SimpleDateFormat format = new SimpleDateFormat(FORMATO_FECHA);
						String dateFormated = format.format(aux.getUltimaActualizacion());
						try {
							capaEntorno.setUltimaActualizacion(format.parse(dateFormated));
						} catch (ParseException e) {}
						capaSvc.capaActualizar(capaEntorno);
						updatedCapa = !updatedCapa;
					}
					// if(!updatedMarkers) {
					marcadorAux = new Marcador();
					estacionAux = new Estacion();
					List<Pronostico> pronosticos = new ArrayList<>();
					aux.getPronosticos().forEach(pronostico->{
						Pronostico pronosticoEnt = new Pronostico();
						pronosticoEnt.setDescripcion(pronostico.getDescripcion());
						pronosticoEnt.setEstacion(estacionAux);
						pronosticoEnt.setVentanaPronostico(ventanaPronosticoDao.findOne(pronostico.getCodigoVentana()));
						pronosticoEnt.setTiempoInicial(pronostico.getTiempoInicial());
						pronosticoEnt.setTiempoFinal(pronostico.getTiempoFinal());
//						pronosticoEnt.se
						medicionSvc.medicionObtenerPorIdCapa(capaEntorno.getId()).forEach(medicion->{
							if((medicion.getNombre().toUpperCase().contains(pronostico.getNombreVentana().toUpperCase())) 
								&&(medicion.getNombre().toUpperCase().contains(pronostico.getDescripcion()))) {
								if(pronostico.getCodigoVentana().equals(ID_VENTANA_RANGO_ERROR)) {
									if(now.after(pronostico.getTiempoInicial()) && now.after(pronostico.getTiempoFinal())) {
										marcadorAux.setIcono(medicion.getIcono());
									}
								}else if(now.after(pronostico.getTiempoInicial()) && now.before(pronostico.getTiempoFinal())) {
									marcadorAux.setIcono(medicion.getIcono());
								} 
								pronosticoEnt.setIcono(medicion.getIcono()); 
							}
						});
						pronosticos.add(pronosticoEnt);
					});
					marcadorAux.setNombre(aux.getNombre());
					marcadorAux.setCapa(capaEntorno);	
					if(isPoligono) {
						marcadorAux.setPoligono(isPoligono);
						marcadorAux.setCoordenadaPolygon(GeometryUtil.obtenerPuntosPolygon(aux.getCoordenadas()));
						marcadorAux.setCoordenadaPunto(marcadorAux.getPolygon().getCentroid());
					}
					if(aux.getNombre().contains("Medellin") || aux.getNombre().equals("Santa Elena")) {
						estacionAux.setMunicipio("MEDELLIN");
					}else {
						estacionAux.setMunicipio(aux.getNombre().toUpperCase());
					}
					marcadorSvc.marcadorCrear(marcadorAux);
					estacionAux.setRangoTemperatura(aux.getMedicion());
					estacionAux.setMarcador(marcadorAux);
					estacionAux.setNombre(aux.getNombre());
										
					String[] partes = aux.getMedicion().split(" \\| ");
					estacionAux.setRangoTemperatura(aux.getMedicion());
					if(partes!=null) {
					    estacionAux.setTemperaturaMinima(partes[0]);
					    estacionAux.setTemperaturaMaxima(partes[1]);
				    }					
					estacionDao.save(estacionAux);
					pronosticoDao.save(pronosticos);
					// }
				});
				break;
			default:
			}
			return Boolean.TRUE;
		} catch (Exception e) {
			return Boolean.FALSE;
		}
	}
	
	@Override
	public String getDetailXLocation(Long idCapa, double lat, double lng) {
		
		return estacionDaoJDBC.getDetailXMarker(estacionDaoJDBC.getDetailNearestMarker(idCapa, lat, lng));
		
	}

}
