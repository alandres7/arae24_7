package co.gov.metropol.area247.contenedora.service;

import java.util.List;

import co.gov.metropol.area247.contenedora.model.Coordenada;

public interface IContenedoraCoordenadaService {
	
	boolean coordeanadaCrear(Coordenada coordenada);
	Coordenada findByLatitudAndLongitud(float Latitud, float Longitud);
	boolean coordenadaEliminar(Long idCoordenada);
	List<Coordenada> obtenerMarcadorPoligonosPorRadioAccion(Long idMarcador, double latitud, double Longitud, int radio);
	List<Coordenada> obtenerPoligonosPorRadioAccionSubcategoria(Long idSubcategoria, double latitud, double Longitud, int radio);
	List<Coordenada> obtenerPoligonosPorRadioAccionCategoria(Long idCategoria, double latitud, double Longitud, int radio);
	void actualizarPuntoCentrodePolygon(Coordenada coordenada);
	List<Coordenada> obtenerMarcadorPorRadio(double latitud, double longitud, int radio, Long idMarcador);
	List<Coordenada> obtenerMarcadorCapaPorRadio(double latitud, double longitud, int radio, Long idMarcador);
	List<Coordenada> validacionReporteVigiaPorRadio(double latitud, double longitud, double radio, String aliasReporte, String nombre);
	
}
