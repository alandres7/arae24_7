package co.gov.metropol.area247.contenedora.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.contenedora.service.IContenedoraLoadMarkersService;
import co.gov.metropol.area247.core.gateway.MarkersProvider;
import co.gov.metropol.area247.core.gateway.geo.dto.Feature;
import co.gov.metropol.area247.core.ordenamiento.dto.CoordenadaDto;
import co.gov.metropol.area247.core.util.GeometryUtil;
import co.gov.metropol.area247.seguridad.model.Municipio;
import co.gov.metropol.area247.seguridad.service.ISeguridadMunicipioService;

@Service("MunicipiosSvc")
public class IContenedoraLoadMarkersMunicipiosServiceImpl implements IContenedoraLoadMarkersService {

	private static final String VALLE_ABURRA = "BARBOSA;GIRARDOTA;COPACABANA;BELLO;MEDELLÍN;ENVIGADO;ITAGÜÍ,SABANETA;LA ESTRELLA;CALDAS";
	private static final String MUNICIPIOS_LIST = "BARBOSA;GIRARDOTA;COPACABANA;BELLO;MEDELLIN;ENVIGADO;ITAGUI,SABANETA;LA ESTRELLA;CALDAS";
	private static final String VALUE_FIELDS_MUNICIPIOS = "S_MUNICIPIO";
	private static final String URL_MUNICIPIOS = "http://geografico.metropol.gov.co:6080/arcgis/rest/services/Division_Politica/Division_Politica/MapServer/2/query";

	@Autowired
	MarkersProvider proveedorMarkers;

	@Autowired
	ISeguridadMunicipioService municipioSvc;

	
	@Override
	public boolean updateMarkers() {
		try {
			List<Feature> features = proveedorMarkers.getFeatures(URL_MUNICIPIOS, VALUE_FIELDS_MUNICIPIOS);
			List<Municipio> municipios = municipioSvc.getByValleAburra();
			municipios.forEach(municipio -> {
				String nombreMunicipio = municipio.getNombre();
				Feature eureka = features.stream()
						.filter(feat -> nombreMunicipio
								.equalsIgnoreCase(feat.getAttributes().get(VALUE_FIELDS_MUNICIPIOS).getAsString()))
						.findFirst().orElse(null);
				if (eureka == null) {
					if ("ITAGUI".equalsIgnoreCase(nombreMunicipio)) {
						eureka = features.stream()
								.filter(feat -> "ITAGÜÍ".equalsIgnoreCase(
										feat.getAttributes().get(VALUE_FIELDS_MUNICIPIOS).getAsString()))
								.findFirst().orElse(null);
					}
					if ("MEDELLIN".equalsIgnoreCase(nombreMunicipio)) {
						eureka = features.stream()
								.filter(feat -> "MEDELLÍN".equalsIgnoreCase(
										feat.getAttributes().get(VALUE_FIELDS_MUNICIPIOS).getAsString()))
								.findFirst().orElse(null);
					}
				}
				if (eureka != null) {
					List<CoordenadaDto> coordenadas = proveedorMarkers.guardarCoordenadas(eureka);
					municipio.setPolygon(GeometryUtil.obtenerPuntosPolygon(coordenadas));
					municipioSvc.guardarMunicipio(municipio);
				}
			});
			return Boolean.TRUE;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Boolean.FALSE;
	}

	@Override
	public boolean updateMarkersCategoria(Long idCategoria) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateMarkersCapa(Long idCapa) {
		// TODO Auto-generated method stub
		return false;
	}

}
