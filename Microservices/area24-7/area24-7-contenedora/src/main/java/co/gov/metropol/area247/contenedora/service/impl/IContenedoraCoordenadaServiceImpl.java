package co.gov.metropol.area247.contenedora.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.gov.metropol.area247.contenedora.dao.IContenedoraCoordenadaRepository;
import co.gov.metropol.area247.contenedora.model.Coordenada;
import co.gov.metropol.area247.contenedora.service.IContenedoraCoordenadaService;

@Service
public class IContenedoraCoordenadaServiceImpl implements IContenedoraCoordenadaService {

	private static final Logger LOGGER = LoggerFactory.getLogger(IContenedoraMarcadorServiceImpl.class);

	@Autowired
	IContenedoraCoordenadaRepository coordenadaDao;

	@Override
	public Coordenada findByLatitudAndLongitud(float Latitud, float Longitud) {
		return coordenadaDao.findByLatitudAndLongitud(Latitud, Longitud);
	}

	@Override
	public boolean coordeanadaCrear(Coordenada coordenada) {
		try {
			coordenadaDao.save(coordenada);
			LOGGER.info("Se ha creado coordenada con id " + coordenada.getId());
			return true;
		} catch (Exception e) {
			LOGGER.error("No se ha podido crear la coordenada ;  " + e);
			return false;
		}
	}

	@Override
	public boolean coordenadaEliminar(Long idCoordenada) {
		try {
			coordenadaDao.delete(idCoordenada);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<Coordenada> obtenerMarcadorPoligonosPorRadioAccion(Long idMarcador, double latitud, double Longitud,
			int radio) {
		try {
			return coordenadaDao.obtenerMarcadorPoligonosPorRadioAccion(idMarcador, latitud, Longitud, radio);

		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public void actualizarPuntoCentrodePolygon(Coordenada coordenada) {
		try {
			coordenadaDao.coordenadaGeometryPolygonCentroide(coordenada.getId());
		} catch (Exception e) {
			LOGGER.error("Error actualizando centroide del poligono id: " + coordenada.getId() + e);
		}
	}

	@Override
	public List<Coordenada> obtenerPoligonosPorRadioAccionSubcategoria(Long idSubcategoria, double latitud,
			double Longitud, int radio) {
		try {

			return coordenadaDao.obtenerPoligonosPorRadioAccionSubcategoria(idSubcategoria, latitud, Longitud, radio);

		} catch (Exception e) {
			LOGGER.error(
					"Error obteniendo poligonos categoria por radio de accion id subcategoria: " + idSubcategoria + e);
		}
		return null;
	}

	@Override
	public List<Coordenada> obtenerPoligonosPorRadioAccionCategoria(Long idCategoria, double latitud, double Longitud,
			int radio) {
		try {
			return coordenadaDao.obtenerPoligonosPorRadioAccionCategoria(idCategoria, latitud, Longitud, radio);

		} catch (Exception e) {
			LOGGER.error("Error obteniendo poligonos categoria por radio de accion id categoria: " + idCategoria + e);
		}
		return null;
	}

	@Override
	public List<Coordenada> obtenerMarcadorPorRadio(double latitud, double longitud, int radio, Long idMarcador) {

		try {
			return coordenadaDao.obtenerMarcadorPorRadio(latitud, longitud, idMarcador, radio);

		} catch (Exception e) {
			LOGGER.error("Error obteniendo poligonos de marcador por radio de accion id marcador: " + idMarcador + e);
		}
		return null;
	}

	@Override
	public List<Coordenada> validacionReporteVigiaPorRadio(double latitud, double longitud, double radio,
			String aliasReporte, String nombre) {
		try {
			return coordenadaDao.validacionReporteVigiaPorRadio(latitud, longitud, radio, aliasReporte, nombre);

		} catch (Exception e) {
			LOGGER.error("Error obteniendo puntos para el reporte: " + aliasReporte + e);
			return null;
		}
	}

	@Override
	public List<Coordenada> obtenerMarcadorCapaPorRadio(double latitud, double longitud, int radio, Long idMarcador) {
		try {
			return coordenadaDao.obtenerMarcadorCapaPorRadio(latitud, longitud, idMarcador, radio);

		} catch (Exception e) {
			LOGGER.error("Error obteniendo puntos para el reporte: " + idMarcador + e);
			return null;
		}
	}

}
