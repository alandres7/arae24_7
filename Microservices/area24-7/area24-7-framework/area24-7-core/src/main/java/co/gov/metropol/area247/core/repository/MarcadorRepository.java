package co.gov.metropol.area247.core.repository;

import java.util.List;

import co.gov.metropol.area247.core.domain.marker.dto.MarkerPoint;
import co.gov.metropol.area247.core.ordenamiento.dto.CategoryRelationship;
import co.gov.metropol.area247.core.repository.data.CrudRepository;
import co.gov.metropol.area247.jdbc.dao.ex.SQLException;

public interface MarcadorRepository extends CrudRepository<MarkerPoint> {

	public List<MarkerPoint> obtenerMarcadoresPorLatYLonYRadioYCapa(Double latitud, Double longitud, int radioAccion, Long idCapa, String nivelCapa)
			throws SQLException;
	
	public List<CategoryRelationship> getPolygonsByLatLng(double lat, double lng, List<Long> idsCategoria)throws SQLException;
	
	public List<MarkerPoint> obtenerMarcadoresPorIdUsuario(Long idUsuario) throws SQLException;
	
	public List<MarkerPoint> obtenerMarcadoresPorIdCapa(Long idCapa) throws SQLException;
	
	public List<MarkerPoint> obtenerMarcadorPorCapaYNivelCapa(String nivelCapa, Long idCapaCategoria) throws SQLException;
	
}
