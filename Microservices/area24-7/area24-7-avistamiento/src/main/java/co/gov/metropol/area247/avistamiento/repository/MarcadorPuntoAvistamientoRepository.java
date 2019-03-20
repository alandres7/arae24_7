package co.gov.metropol.area247.avistamiento.repository;

import java.util.List;

import co.gov.metropol.area247.core.domain.marker.dto.MarkerPoint;
import co.gov.metropol.area247.core.repository.data.CrudRepository;
import co.gov.metropol.area247.jdbc.dao.ex.SQLException;

public interface MarcadorPuntoAvistamientoRepository extends CrudRepository<MarkerPoint> {

	public List<MarkerPoint> obtenerMarcadorAvistamientoPorNombreOCapa(String nombre, double longitud, double latitud) throws SQLException;

}
