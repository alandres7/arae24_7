package co.gov.metropol.area247.entorno.repository;

import co.gov.metropol.area247.core.repository.data.CrudRepository;
import co.gov.metropol.area247.entorno.domain.Agua;
import co.gov.metropol.area247.jdbc.dao.ex.SQLException;

public interface EstacionRepository  extends CrudRepository<Agua> {
	
	public String getDetailXMarker(Long idMarcador) throws SQLException;

	Long getDetailNearestMarker(Long idCapa, double lat, double lng);

}
