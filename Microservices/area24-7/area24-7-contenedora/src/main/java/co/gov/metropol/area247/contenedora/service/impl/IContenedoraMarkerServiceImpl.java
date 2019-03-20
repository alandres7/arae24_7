package co.gov.metropol.area247.contenedora.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.maps.internal.PolylineEncoding;
import com.google.maps.model.LatLng;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Polygon;

import co.gov.metropol.area247.contenedora.dao.IContenedoraCapaRepository;
import co.gov.metropol.area247.contenedora.dao.IContenedoraCategoriaRepository;
import co.gov.metropol.area247.contenedora.dao.IContenedoraMarcadorRepository;
import co.gov.metropol.area247.contenedora.mapper.IContenedoraMapper;
import co.gov.metropol.area247.contenedora.model.Capa;
import co.gov.metropol.area247.contenedora.model.Categoria;
import co.gov.metropol.area247.contenedora.model.Marcador;
import co.gov.metropol.area247.contenedora.service.IContenedoraMarkerService;
import co.gov.metropol.area247.contenedora.util.NivelCapa;
import co.gov.metropol.area247.contenedora.util.logger.Logger;
import co.gov.metropol.area247.core.domain.capa.dto.CapaGeometries;
import co.gov.metropol.area247.core.domain.capa.dto.CapaMarkerList;
import co.gov.metropol.area247.core.domain.capa.dto.CategoriaMarkerList;
import co.gov.metropol.area247.core.domain.marker.dto.LandMessage;
import co.gov.metropol.area247.core.domain.marker.dto.Marker;
import co.gov.metropol.area247.core.domain.marker.dto.MarkerGeometry;
import co.gov.metropol.area247.core.domain.marker.dto.MarkerInfo;
import co.gov.metropol.area247.core.domain.marker.dto.MarkerPoint;
import co.gov.metropol.area247.core.domain.marker.dto.MarkerPolygon;
import co.gov.metropol.area247.core.domain.marker.dto.MarkersCharacterTab;
import co.gov.metropol.area247.core.domain.marker.dto.Point;
import co.gov.metropol.area247.core.ordenamiento.dto.CategoryRelationship;
import co.gov.metropol.area247.core.repository.MarcadorRepository;
import co.gov.metropol.area247.core.svc.ICoreIconoSvc;
import co.gov.metropol.area247.core.util.GeometryUtil;
import co.gov.metropol.area247.seguridad.dao.ISeguridadMunicipioRepositoryCustom;
import co.gov.metropol.area247.seguridad.model.Municipio;

@Service
public class IContenedoraMarkerServiceImpl implements IContenedoraMarkerService {
	
	private static final int NIVEL_CAPA = 2;
	private static final int NIVEL_CATEGORIA = 3;
	
	private static final String TIPO_RESP_FICHA_NOMBRE = "NOMBRE";
	private static final String TIPO_RESP_FICHA_NOMBRE_DESC = "NOMBRE-DESCRIPCION";
	private static final String TIPO_RESP_FICHA_SINO = "SI/NO";
	
	private static final Logger LOGGER = Logger.getInstance(IContenedoraMarkerServiceImpl.class); 
	
	private  List<CategoriaMarkerList> categoriasFicha;
	
	@Autowired
	IContenedoraCapaRepository capaDao;
	
	@Autowired
	IContenedoraCategoriaRepository categoriaDao;
	
	@Autowired
	@Qualifier("municipioDao")
	ISeguridadMunicipioRepositoryCustom municipioDao;
	
	@Autowired
	IContenedoraMarcadorRepository marcadorDao;
	
	@Autowired
	@Qualifier("markerLteMapper")
	IContenedoraMapper<Marcador, Marker> markerLteMapper;
	
	@Autowired
	MarcadorRepository marcadorJDBC;
	
	@Autowired
	ICoreIconoSvc iconoSvc;
	
	@Value("${iconos.server.url}")
	String urlIconos;
	
	@Override
	public List<Marker> getMarkers(NivelCapa nivelCapa, String idsCapas, String query) {
		int nivel = nivelCapa.getNivel();
		List<Long> idsCapasList = new ArrayList<>();
		List<Marker> markersLte = new ArrayList<>();
		Arrays.asList(idsCapas.split(",")).forEach(idCapa -> idsCapasList.add(Long.valueOf(idCapa)));
		switch(nivel) {
			case NIVEL_CAPA:
				List<Capa> capas = (List<Capa>) capaDao.findAll(idsCapasList);
				List<Marcador> marcadoresCapa = new ArrayList<>();
				capas.forEach(capa -> {
					if(capa.getMarcadores() != null) {
						capa.getMarcadores().forEach(marcador->{
							if(marcador.getIcono()==null) {
								marcador.setIcono(capa.getIconoMarcador());
							}
						});
						marcadoresCapa.addAll(capa.getMarcadores());
					}
				});

				marcadoresCapa.stream()
						.sorted((o1, o2)->o1.getNombre().compareTo(o2.getNombre()))
						.filter(marcador -> marcador.getNombre().toUpperCase().contains(query.toUpperCase()))
						.limit(3).collect(Collectors.toList())
						.forEach(filterMarker->{
							Marker markerTemp = new Marker();
							markerTemp.setId(filterMarker.getId());
							markerTemp.setNombre(filterMarker.getNombre());
							markerTemp.setRutaWebIcono(urlIconos+filterMarker.getIcono().getId());
							markersLte.add(markerTemp);
						});
				break;
			case NIVEL_CATEGORIA:
				List<Categoria> categorias = (List<Categoria>) categoriaDao.findAll(idsCapasList);
				List<Marcador> marcadores = new ArrayList<>();
				categorias.forEach(categoria->{
					if(categoria.getMarcadores() != null) {
						categoria.getMarcadores().forEach(marcador->marcador.setIcono(categoria.getIconoMarcador()));
						marcadores.addAll(categoria.getMarcadores());
					}
				});
				marcadores.stream()
						.sorted((o1, o2)->o1.getNombre().compareTo(o2.getNombre()))
						.filter(marcador -> marcador.getNombre().toUpperCase().contains(query.toUpperCase()))
						.limit(3).collect(Collectors.toList())
						.forEach(filterMarker->{
							Marker markerTemp = markerLteMapper.entityToModel(filterMarker);
							markerTemp.setRutaWebIcono(urlIconos+filterMarker.getIcono().getId());
							markersLte.add(markerTemp);
						});
				break;
			default:
				break;
		}
		return markersLte;
	}
	
		
	@Override
	public MarkerInfo  getMarkerInfo(Long idMarker) {
		Marcador marcadorAux = marcadorDao.findOne(idMarker);
		MarkerInfo markerInfoDto = new MarkerInfo();
		markerInfoDto.setNombre(marcadorAux.getNombre());
		markerInfoDto.setDescripcion(marcadorAux.getDescripcion());
		markerInfoDto.setRutaImagen(marcadorAux.getRutaImagen());
		markerInfoDto.setDireccion(marcadorAux.getDireccion());
		markerInfoDto.setNombreMunicipio(marcadorAux.getNombreMunicipio());
		return markerInfoDto;
	}
	
	@Override
	public MarkerGeometry getMarker(Long idMarker){
		Marcador marcadorAux = marcadorDao.findOne(idMarker);
		MarkerGeometry geometryLte = new MarkerGeometry();
		geometryLte.setMarcadorId(idMarker);
		if(marcadorAux.isPoligono()) {
			if(marcadorAux.getPolygon() != null) {
				List<LatLng> coordenadasTest = GeometryUtil.conversorCoordenadasMaps(marcadorAux.getPolygon());
				String polyLineEnconding = PolylineEncoding.encode(coordenadasTest);
				geometryLte.setEncodedPolygon(polyLineEnconding);
			}
		}else {
			if(marcadorAux.getPoint() != null) {
				geometryLte.setCoordenadaPunto(marcadorAux.getPoint());
			}
		}
		return geometryLte;		
	}

	@Override
	public CapaGeometries getMarkers(NivelCapa nivelCapa, Long idCapa) {
		int nivel = nivelCapa.getNivel();
		CapaGeometries dto = new CapaGeometries();
		dto.setIdCapa(idCapa);
		List<MarkerPolygon> markersPolygon = new ArrayList<>();
		List<MarkerPoint> markersPoint = new ArrayList<>();
		switch (nivel) {
		case NIVEL_CAPA:
			Capa capa = capaDao.findOne(idCapa);
			if(capa!=null) {
			    if(capa.getMarcadores()!=null) {	
				    long idIconoCapa = capa.getIconoMarcador()==null?0L:capa.getIconoMarcador().getId();				    
			        markersPolygon = capa.getMarcadores().stream().filter(marcador -> marcador.isPoligono())
				    .map(marcadorCapa->
					    new MarkerPolygon(marcadorCapa.getId(),
						    PolylineEncoding.encode(GeometryUtil.conversorCoordenadasMaps(marcadorCapa.getPolygon()))
					    )
				    ).collect(Collectors.toList());			        
			        markersPoint = capa.getMarcadores().stream().filter(marcador -> marcador.getPoint()!=null)
				    .map(marcadorCapa-> 
				        new MarkerPoint(marcadorCapa.getId(),
						    new Point( marcadorCapa.getPoint().getY(),marcadorCapa.getPoint().getX()),
						        rutaWebIconoMarkerPoint(marcadorCapa, idIconoCapa)
					    )
				    ).collect(Collectors.toList());
			    }
			}
			break;
		case NIVEL_CATEGORIA:
			Categoria cat = categoriaDao.findOne(idCapa);	
			if(cat!=null) {
			    if(cat.getMarcadores()!=null) {				    	
				    long idIconoCategoria = 0L;					    
				    if(cat.getIconoMarcador()!=null) {
				    	idIconoCategoria = cat.getIconoMarcador().getId();
				    }else {
				    	idIconoCategoria = cat.getIcono().getId();
				    }				    				    				    
			        markersPolygon = mapperMarkersPoly(cat.getMarcadores().stream().filter(marcador -> marcador.isPoligono())
					        .collect(Collectors.toList()));			    
			        markersPoint = mapperMarkersPoint(cat.getMarcadores().stream().filter(marcador -> !marcador.isPoligono())
					        .collect(Collectors.toList()), idIconoCategoria);
			    }  
			}
			break;
		default:
			break;
		}
		dto.setMarkersPoint(markersPoint);
		dto.setMarkersPolygon(markersPolygon);
		return dto;
	}
	
	
	
	private String rutaWebIconoMarkerPoint(Marcador marcadorCapa, long idIconoCapa) {
		long capaEntornoClima = 12;
		if(marcadorCapa.getCapa().getId() == capaEntornoClima) {
			return urlIconos+iconoSvc.getIdIconoPronostico(marcadorCapa.getId());
		}
		if(marcadorCapa.getIcono()==null) {
			return urlIconos+idIconoCapa;
		}
		return marcadorCapa.getIcono().getNombre()==null?
				(marcadorCapa.getIcono().getRutaLogo()):(urlIconos+marcadorCapa.getIcono().getId());
	}
	
	private List<MarkerPolygon> mapperMarkersPoly(List<Marcador> marcadoresPoly) {
		List<MarkerPolygon> markersPolygon = new ArrayList<>();
		marcadoresPoly.forEach(marcador -> {
			MarkerPolygon markerPolygon = new MarkerPolygon();
			markerPolygon.setId(marcador.getId());
			if (marcador.getPolygon() != null) {
				markerPolygon.setEncodedPolygon(PolylineEncoding
						.encode(GeometryUtil.conversorCoordenadasMaps(
								marcador.getPolygon())));
				markersPolygon.add(markerPolygon);
			}
		});
		return markersPolygon;
	}
	
	
	private List<MarkerPoint> mapperMarkersPoint(List<Marcador> marcadoresPoint, Long idIcono) {
		List<MarkerPoint> markersPoint = new ArrayList<>();
		marcadoresPoint.forEach(marcador -> {
			MarkerPoint markerPoint = new MarkerPoint();
			markerPoint.setIdMarker(marcador.getId());
			if(marcador.getIcono()==null) {
				markerPoint.setRutaWebIcono(urlIconos+idIcono);
			}else {
				markerPoint.setRutaWebIcono(urlIconos+marcador.getIcono().getId());
			}
			if (marcador.getPoint() != null) {
				markerPoint.setPoint(new Point((float) marcador.getPoint().getY(),(float) marcador.getPoint().getX()));
				markersPoint.add(markerPoint);
			}
		});
		return markersPoint;
	}
	
	/**
	 * 
	 * 
	 **/
	@Override
	public MarkersCharacterTab getCharacterTab(double lat, double lng){
		String nombreMunicipio = "";
		try {
		List<Capa> capas = capaDao.findByFichaCaracterizacion(Boolean.TRUE);
		List<Municipio> municipios = municipioDao.coordenadaInterceptoMunicipio(lat, lng);
		nombreMunicipio = municipios.stream().count() == 0L?"Estás fuera del Área Metropolitana del Valle de Aburrá!!":municipios.get(0).getNombre();
		List<CapaMarkerList> capasFicha = new ArrayList<>();
		List<Long> idsCategorias = new ArrayList<>();
		capas.forEach(capa->{
			CapaMarkerList capaFicha = new CapaMarkerList();
			capaFicha.setNombreCapa(capa.getNombre());
			capaFicha.setUrlIcono(urlIconos+capa.getIcono().getId());
			List<Categoria> categorias = capa.getCategorias();
			categoriasFicha = new ArrayList<>();
			categorias.forEach(categoria->{
				if(categoria.isFichaCaracterizacion()) {
					idsCategorias.add(categoria.getId());
					CategoriaMarkerList categoriaFicha = new CategoriaMarkerList();
					categoriaFicha.setNombreCategoria(categoria.getNombre());
					categoriaFicha.setUrlIcono(urlIconos+categoria.getIcono().getId());
					categoriaFicha.setNombreMarcador(categoria.getRespuestaFichaCarac());
					categoriasFicha.add(categoriaFicha);
				}
			});
			List<CategoryRelationship> catsRelation = marcadorJDBC.getPolygonsByLatLng(lat, lng, idsCategorias);
			configCategoriasFicha(catsRelation);
			capaFicha.setCategorias(categoriasFicha);
			capasFicha.add(capaFicha);
		});
			return new MarkersCharacterTab(nombreMunicipio, capasFicha);
		}catch(Exception ex) {
			LOGGER.error("No se pudo generar la ficha de caracterización -->> latitud:" +lat+";;Longitud: "+lng, ex);
			return new MarkersCharacterTab(nombreMunicipio, new ArrayList<>());
		}
	}
	
	private void configCategoriasFicha(List<CategoryRelationship> catsRelation) {
		categoriasFicha.forEach(categoriaFicha->{
			catsRelation.forEach(catRelacion->compareRelationsCategories(categoriaFicha, catRelacion));
			if(categoriaFicha.getNombreMarcador()!=null) {
			switch(categoriaFicha.getNombreMarcador()) {
				case TIPO_RESP_FICHA_NOMBRE:
					categoriaFicha.setNombreMarcador("");
					break;
				case TIPO_RESP_FICHA_NOMBRE_DESC:
					categoriaFicha.setNombreMarcador("");
					break;
				case TIPO_RESP_FICHA_SINO:
					categoriaFicha.setNombreMarcador("NO");
					break;
				default:
					break;
		}
			}else {
				categoriaFicha.setNombreMarcador("");
			}
		});
	}
	
	private void compareRelationsCategories(CategoriaMarkerList categoriaFicha, CategoryRelationship catRelacion) {
		if (categoriaFicha.getNombreCategoria().equals(catRelacion.getNombreCategoria())) {
			switch (categoriaFicha.getNombreMarcador()) {
			case TIPO_RESP_FICHA_NOMBRE:
				if (catRelacion.getNombre() != null && !catRelacion.getNombre().equals("")) {
					categoriaFicha.setNombreMarcador(catRelacion.getNombre());
				}
				break;
			case TIPO_RESP_FICHA_NOMBRE_DESC:
				if (catRelacion.getNombre() != null && !catRelacion.getNombre().equals("")) {
					StringBuilder nombreMarcador = new StringBuilder(catRelacion.getNombre());
					if (catRelacion.getDescripcion() != null) {
						nombreMarcador.append(" - ").append(catRelacion.getDescripcion());
					}
					categoriaFicha.setNombreMarcador(nombreMarcador.toString());
				}
				break;
			case TIPO_RESP_FICHA_SINO:
				if (catRelacion.getNombre() != null && !catRelacion.getNombre().equals("")) {
					categoriaFicha.setNombreMarcador("SI");
				}
				break;
			default:
				break;
			}
		}
	}
	
	@Override
	public boolean validateInsideAMVA(double lat, double lng) {
		List<Municipio> municipios = municipioDao.coordenadaInterceptoMunicipio(lat, lng);
		if(municipios.isEmpty()) {
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
	
	@Override
	public LandMessage getLandMessage(double lat, double lng) {
		List<Municipio> municipios = municipioDao.coordenadaInterceptoMunicipio(lat, lng);
		String nombreMunicipio = municipios.stream().count() == 0L
				? "Estás fuera del Área Metropolitana del Valle de Aburrá!!"
				: municipios.get(0).getNombre();
		if (nombreMunicipio.contains("fuera")) {
			return new LandMessage(nombreMunicipio, "UNDETERMINED");
		}
		StringBuilder clasSuelo = new StringBuilder("");
		List<Long> idsCategorias = new ArrayList<>();
		List<Categoria> categMsgOrdenam = categoriaDao.findByMsgOrdenamiento(Boolean.TRUE);
		for (Categoria categoria : categMsgOrdenam) {
			idsCategorias = new ArrayList<>();
			idsCategorias.add(categoria.getId());
			List<CategoryRelationship> catsRelation = marcadorJDBC.getPolygonsByLatLng(lat, lng, idsCategorias);
			if (!catsRelation.isEmpty()) {
				if (categoria.isAreaProtegida()) {
					clasSuelo = new StringBuilder("ÁREAS PROTEGIDAS");
				} else if (categoria.isSueloProteccion()) {
					clasSuelo = new StringBuilder("SUELO DE PROTECCIÓN");
				} else {
					if (clasSuelo.length() > 0) {
						clasSuelo.append(" - ").append(catsRelation.get(0).getNombre());
					} else {
						clasSuelo.append(catsRelation.get(0).getNombre());
					}
				}
			}
		}
		return new LandMessage(nombreMunicipio, clasSuelo.toString());
	}
	
	@Override
	public String getGeometry(Long idMarker){
		String geometry = "";
		Marcador marcadorAux = marcadorDao.findOne(idMarker);
		MarkerGeometry geometryLte = new MarkerGeometry();
		geometryLte.setMarcadorId(idMarker);
		if(marcadorAux.isPoligono()) {
			if(marcadorAux.getPolygon() != null) {
				List<LatLng> coordenadasTest = GeometryUtil.conversorCoordenadasMaps(marcadorAux.getPolygon());
				//System.out.println(coordenadasTest);
				Polygon polygon = GeometryUtil.obtenerPolygonCoordInvert(coordenadasTest);
				marcadorAux.setCoordenadaPolygon(polygon);
				coordenadasTest = GeometryUtil.conversorCoordenadasMaps(marcadorAux.getPolygon());
				//System.out.println(coordenadasTest);
				marcadorDao.save(marcadorAux);
				geometry = PolylineEncoding.encode(coordenadasTest);
			}
		}else {
			if(marcadorAux.getPoint() != null) {
				geometryLte.setCoordenadaPunto(marcadorAux.getPoint());
				geometry =  ""+marcadorAux.getPoint().getX()+","+marcadorAux.getPoint().getY();
			}
		}
		return geometry;	
	}
	
	
	@Override
	public boolean invertirCoordenadasCategoria(Long idCategoria) {
		try{
			List<Marcador> markers = marcadorDao.obtenerPorIdCategoria(idCategoria);
			markers.forEach(marker->{
				getGeometry(marker.getId());
			});
			return true;
		}catch (Exception e) {
			LOGGER.error("Error intercambiando coordenadas: ", e);
		}
		return false;
		
	}
	
	
	
	public CapaGeometries getMarkersCapaByRadio(Long idCapa, Double latitud, Double longitud, int radioAccion) {

		CapaGeometries dto = new CapaGeometries();
		dto.setIdCapa(idCapa);
		List<Marcador> marcadoresPoint = new ArrayList<>();
		
		Capa capa = capaDao.findOne(idCapa);

		List<MarkerPoint>  markersPoint = mapperMarkersPoint(marcadoresPoint, capa.getIcono().getId());
		dto.setMarkersPoint(markersPoint);
		
		return dto;
	}	
	
}
