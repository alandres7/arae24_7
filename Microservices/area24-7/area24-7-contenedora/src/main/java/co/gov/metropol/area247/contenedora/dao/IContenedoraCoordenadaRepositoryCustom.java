package co.gov.metropol.area247.contenedora.dao;

import java.util.List;

import co.gov.metropol.area247.contenedora.model.Coordenada;
@Deprecated
public interface IContenedoraCoordenadaRepositoryCustom {
	List<Coordenada> obtenerMarcadorPoligonosPorRadioAccion(Long idMarcador, double latitud, double Longitud, int radio);
	List<Coordenada> obtenerPoligonosPorRadioAccionSubcategoria(Long idSubcategoria, double latitud, double Longitud, double radio);
	List<Coordenada> obtenerPoligonosPorRadioAccionCategoria(Long idCategoria, double latitud, double Longitud, double radio);
	List<Coordenada> obtenerPoligonosPorRadioAccionCapa(Long idCapa, double latitud, double Longitud, double radio);
	List<Coordenada> obtenerPuntosPorRadioAccionCategoria(Long idCategoria, double latitud, double Longitud,double radio);
	List<Coordenada> obtenerPuntosPorRadioAccionCapa(Long idCapa, double latitud, double Longitud, double radio);
	List<Coordenada> coordenadaGeometryPolygonIntercepto(Long idCategoria, double lat, double lng);

}
