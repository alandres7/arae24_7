package co.gov.metropol.area247.contenedora.service;

import java.util.List;

import co.gov.metropol.area247.contenedora.util.NivelCapa;
import co.gov.metropol.area247.core.domain.capa.dto.CapaGeometries;
import co.gov.metropol.area247.core.domain.marker.dto.LandMessage;
import co.gov.metropol.area247.core.domain.marker.dto.Marker;
import co.gov.metropol.area247.core.domain.marker.dto.MarkerGeometry;
import co.gov.metropol.area247.core.domain.marker.dto.MarkerInfo;
import co.gov.metropol.area247.core.domain.marker.dto.MarkersCharacterTab;

public interface IContenedoraMarkerService {
	
	List<Marker> getMarkers(NivelCapa nivelCapa, String idsCapas, String query);

	MarkerInfo getMarkerInfo(Long idMarker);

	MarkerGeometry getMarker(Long idMarker);

	CapaGeometries getMarkers(NivelCapa nivelCapa, Long idCapa);

	MarkersCharacterTab getCharacterTab(double lat, double lng);

	LandMessage getLandMessage(double lat, double lng);

	boolean validateInsideAMVA(double lat, double lng);
	
	CapaGeometries getMarkersCapaByRadio(Long idCapa, Double latitud, Double longitud, int radioAccion);

	String getGeometry(Long idMarker);

	boolean invertirCoordenadasCategoria(Long idCategoria);
	
}
