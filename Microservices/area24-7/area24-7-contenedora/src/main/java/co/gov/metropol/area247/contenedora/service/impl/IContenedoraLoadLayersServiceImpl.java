package co.gov.metropol.area247.contenedora.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

import co.gov.metropol.area247.contenedora.dao.IContenedoraCapaRepository;
import co.gov.metropol.area247.contenedora.dao.IContenedoraCategoriaRepository;
import co.gov.metropol.area247.contenedora.dao.IContenedoraMarcadorRepository;
import co.gov.metropol.area247.contenedora.model.Capa;
import co.gov.metropol.area247.contenedora.model.Categoria;
import co.gov.metropol.area247.contenedora.model.Marcador;
import co.gov.metropol.area247.contenedora.service.IContenedoraCategoriaService;
import co.gov.metropol.area247.contenedora.service.IContenedoraCoordenadaService;
import co.gov.metropol.area247.contenedora.service.IContenedoraLoadMarkersService;
import co.gov.metropol.area247.contenedora.service.IContenedoraMarcadorService;
import co.gov.metropol.area247.contenedora.util.logger.Logger;
import co.gov.metropol.area247.core.gateway.MarkersProvider;
import co.gov.metropol.area247.core.gateway.geo.dto.Feature;
import co.gov.metropol.area247.core.ordenamiento.dto.CoordenadaDto;
import co.gov.metropol.area247.core.util.GeometryUtil;

@Service("loadLayersSvc")
public class IContenedoraLoadLayersServiceImpl implements IContenedoraLoadMarkersService {
	
	private static final Logger LOGGER = Logger.getInstance(IContenedoraLoadLayersServiceImpl.class); 
	
	@Autowired
	IContenedoraCoordenadaService coordenadaSvc;
	
	@Autowired
	IContenedoraMarcadorService marcadorSvc;
	
	@Autowired
	IContenedoraCategoriaService categoriaSvc;
	
	@Autowired
	IContenedoraMarcadorRepository marcadorDao;
	
	@Autowired
	IContenedoraCategoriaRepository categoriaDao;
	
	@Autowired
	IContenedoraCapaRepository capaDao;
	
	@Autowired
	MarkersProvider proveedorMarkers;
	
	private Marcador marcadorAux; 
	private Categoria categoriaAux;
	private Capa capaAux;
	private Boolean isPoligono;
	
	@Override
	public boolean updateMarkersCategoria(Long idCategoria) {
		try {
			categoriaAux = categoriaDao.findOne(idCategoria);
			StringBuilder fieldsQuery = new StringBuilder("");
			if(!validateRequiredFieldsCategoria())
				return Boolean.FALSE;
			fieldsQuery.append(getItemsCategoriaWS());
			final String aliasNombreMarcador = categoriaAux.getAliasNombre();
			List<Feature> features = proveedorMarkers.getFeatures(categoriaAux.getUrlRecurso(), fieldsQuery.toString());
			if (features==null)
				return Boolean.FALSE;
			List<Marcador> marcadores = new ArrayList<>();
			if (!categoriaAux.getMarcadores().isEmpty()) {
				marcadores = categoriaAux.getMarcadores();
				categoriaAux.setMarcadores(new ArrayList<>());
//				marcadorSvc.eliminarMarcadores(marcadores);
				marcadorDao.delete(marcadores);
				marcadores = null;
			}
			isPoligono = categoriaAux.isPoligono();
			features.forEach(feature -> {
				if (!"".equals(feature.getAttributes().get(aliasNombreMarcador).getAsString().trim())) {
					crearMarcador(feature, aliasNombreMarcador, 
							      categoriaAux.getAliasDescripcion(), 
							      categoriaAux.getAliasMunicipio(),
							      categoriaAux.getAliasDireccion(),
							      categoriaAux.getAliasImagen());
				}
			});
			return Boolean.TRUE;
		} catch (Exception e) {
			LOGGER.error("Error actualizando los marcadores desde el AMVA", e);
		}
		return Boolean.FALSE;

	}
	
	private void crearMarcador(Feature feature, String aliasNombre, String aliasDescripcion, 
			                   String aliasMunicipio, String aliasDireccion, String aliasImagen) {
		Marcador marcador = new Marcador();
		marcador.setNombre(feature.getAttributes().get(aliasNombre).getAsString());
		try {
			marcador.setDescripcion(
					feature.getAttributes().get(aliasDescripcion).getAsString());
			marcador.setNombreMunicipio(
					feature.getAttributes().get(aliasMunicipio).getAsString());
			marcador.setDireccion(
					feature.getAttributes().get(aliasDireccion).getAsString());
			marcador.setRutaImagen(
					feature.getAttributes().get(aliasImagen).getAsString());
		} catch (Exception e) {
			marcador.setDescripcion(" ");
			marcador.setNombreMunicipio(" ");
		}
		if (isPoligono) {
			marcador.setPoligono(Boolean.TRUE);
			marcador.setCoordenadaPolygon(polygonCreate(feature));
		} else {
			marcador.setPoligono(Boolean.FALSE);
			marcador.setCoordenadaPunto(pointCreate(feature));
		}
		marcador.setCategoria(categoriaAux);
		marcadorDao.save(marcador);
	}
	
	@Override
	public boolean updateMarkersCapa(Long idCapa) {
		try {
			capaAux = capaDao.findOne(idCapa);
			isPoligono = capaAux.isPoligono();
			Assert.notNull(capaAux.getAliasNombre(), "Se requiere Alias Nombre");
			Assert.notNull(capaAux.getUrlRecurso(), "Se requiere la URL del recurso a consumir");
			final String aliasNombreMarcador = capaAux.getAliasNombre();
			if (!validateRequiredFieldsCapa())
				return Boolean.FALSE;
			StringBuilder fieldsQuery = new StringBuilder(getItemsCapaWS());
			List<Feature> features = proveedorMarkers.getFeatures(capaAux.getUrlRecurso(), fieldsQuery.toString());

			if (!capaAux.getCategorias().isEmpty()) {
				capaAux.getCategorias().forEach(categoria->{
					List<Marcador> marcadores =  categoria.getMarcadores();
					categoria.setMarcadores(new ArrayList<>());
					marcadorDao.delete(marcadores);
					marcadores = null;
					categoriaAux = categoria;
					features
						.stream()
						.filter(feature->categoria.getNombre().equalsIgnoreCase(feature.getAttributes().get(capaAux.getAliasCategoria()).getAsString().trim()))
						.filter(feature->!"".equals(feature.getAttributes().get(aliasNombreMarcador).getAsString().trim()))
						.collect(Collectors.toList())
							.forEach(feature->
									crearMarcador(feature, aliasNombreMarcador, 
											      capaAux.getAliasDescripcion(),
											      capaAux.getAliasMunicipio(),
											      capaAux.getAliasDireccion(),
											      capaAux.getAliasImagen()));
				});
				return Boolean.TRUE;
			}else {
				return Boolean.FALSE;
			}
		} catch (Exception e) {
			return Boolean.FALSE;
		}
	}

	
	@Override
	public boolean updateMarkers() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	private Polygon polygonCreate(Feature feature) {
		List<CoordenadaDto> coordenadasModel = proveedorMarkers.guardarCoordenadas(feature);
		return GeometryUtil.obtenerPuntosPolygon(coordenadasModel);
		
	}
	
	private Point pointCreate(Feature feature) {
		if (isPoligono) {
			List<CoordenadaDto> coordenadasModel = proveedorMarkers.guardarCoordenadas(feature);
			if (coordenadasModel.size() == 1) {
				CoordenadaDto punto = coordenadasModel.get(0);
				return GeometryUtil.obtenerPunto(punto.getLatitud(), punto.getLongitud());
			} else if (coordenadasModel.size() > 1) {
				Polygon polygon = GeometryUtil.obtenerPuntosPolygon(coordenadasModel);
				return polygon.getCentroid();
			} else {
				return null;
			}
		} else {
			double lat = feature.getGeometry().getY();
			double lng = feature.getGeometry().getX();
			return GeometryUtil.obtenerPunto(lat, lng);
		}
	}
	
	private boolean validateRequiredFieldsCategoria() {
		
		if(categoriaAux.getAliasNombre() == null || "".equals(categoriaAux.getAliasNombre().trim())) 
			return Boolean.FALSE;
		
		if(categoriaAux.getUrlRecurso() == null || "".equals(categoriaAux.getUrlRecurso().trim()))
			return Boolean.FALSE;
		
		return Boolean.TRUE;
	}
	
	private boolean validateRequiredFieldsCapa() {
		
		if(capaAux.getAliasNombre() == null || "".equals(capaAux.getAliasNombre().trim())) 
			return Boolean.FALSE;
		
		if(capaAux.getUrlRecurso() == null || "".equals(capaAux.getUrlRecurso().trim()))
			return Boolean.FALSE;
		
		return Boolean.TRUE;
	}
	
	private String getItemsCapaWS() {
		StringBuilder itemsCapa = new StringBuilder(capaAux.getAliasNombre());
		if(capaAux.getAliasDescripcion() != null && !"".equals(capaAux.getAliasDescripcion().trim())) {
			itemsCapa.append(",").append(capaAux.getAliasDescripcion());
		}
		if(capaAux.getAliasMunicipio() != null && !"".equals(capaAux.getAliasMunicipio().trim())) {
			itemsCapa.append(",").append(capaAux.getAliasMunicipio());
		}
		if(capaAux.getAliasCategoria() != null && !"".equals(capaAux.getAliasCategoria().trim())) {
			itemsCapa.append(",").append(capaAux.getAliasCategoria());
		}
		return itemsCapa.toString();
	}
	
	private String getItemsCategoriaWS() {
		StringBuilder itemsCategoria = new StringBuilder(categoriaAux.getAliasNombre());
		if(categoriaAux.getAliasDescripcion() != null && !"".equals(categoriaAux.getAliasDescripcion().trim())) {
			itemsCategoria.append(",").append(categoriaAux.getAliasDescripcion());
		}
		if(categoriaAux.getAliasMunicipio() != null && !"".equals(categoriaAux.getAliasMunicipio().trim())) {
			itemsCategoria.append(",").append(categoriaAux.getAliasMunicipio());
		}
		if(categoriaAux.getAliasTipo() != null && !"".equals(categoriaAux.getAliasTipo().trim())) {
			itemsCategoria.append(",").append(categoriaAux.getAliasTipo());
		}
		return itemsCategoria.toString();
	}

}
